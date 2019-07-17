package com.diturrizaga.easypay.rabbitMQTest;

import android.app.Activity;
import android.util.Log;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RabbitMQApi {

   private static String AUTHENTICATION_QUEUE = "auth";
   private static String AUTHENTICATION_QUEUE_SUCCESSFUL = "auth_success";
   private static String TRANSFER_QUEUE = "transfer";
   private static String TAG = "SendAndReceive";
   //private static String HOST = "ec2-13-58-133-94.us-east-2.compute.amazonaws.com";
   private static String HOST = "ec2-18-221-252-55.us-east-2.compute.amazonaws.com";
   private static int PORT = 5672;
   private static String USER = "fisi";
   private static String PASS = "fisi";
   private static String DEFAULT_MESSAGE = "Mensaje no recibido";

   /**
    * rabbitMQ send and receive methods
    */

   public void sendMessage(final String messageToSend) {
      Thread send;
      send = new Thread(new Runnable() {
         @Override
         public void run() {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUsername(USER);
            factory.setPassword(PASS);
            factory.setHost(HOST);
            factory.setPort(PORT);
            Connection connection;
            Channel channel = null;
            try {
               connection = factory.newConnection();
               channel = connection.createChannel();
               channel.queueDeclare(AUTHENTICATION_QUEUE, false, false, false, null);
            } catch (IOException e) {
               e.printStackTrace();
            } catch (TimeoutException e) {
               e.printStackTrace();
            }
            //String message = "HOLA MUNDO PRUEBA 11:30 AM!";
            try {
               channel.basicPublish("", AUTHENTICATION_QUEUE, null, messageToSend.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
               e.printStackTrace();
            }
            Log.v(TAG,"----> [x] Sent  " + messageToSend );
         }
      });
      send.start();
   }

   public String receiveMessage() {
      final String[] messageReceived = {DEFAULT_MESSAGE};
      Thread receive;
      receive = new Thread(new Runnable() {
         @Override
         public void run() {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUsername(USER);
            factory.setPassword(PASS);
            factory.setHost(HOST);
            factory.setPort(PORT);
            Connection connection;
            Channel channel = null;
            try {
               connection = factory.newConnection();
               channel = connection.createChannel();
               channel.queueDeclare(AUTHENTICATION_QUEUE_SUCCESSFUL, false, false, false, null);
            } catch (IOException e) {
               e.printStackTrace();
            } catch (TimeoutException e) {
               e.printStackTrace();
            }
            //Log.v(TAG,"[*] Waiting for messages. To exit press CTRL+C");
            //System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
               //String message22 = new String(delivery.getBody(), "UTF-8");

               String messageToReceive = new String(delivery.getBody(), StandardCharsets.UTF_8);
               if (messageToReceive.equals(DEFAULT_MESSAGE)) {

               }
               messageReceived[0] = messageToReceive;
               Log.v(TAG,"----> [x] Received  " + messageToReceive );
            };

            try {
               channel.basicConsume(AUTHENTICATION_QUEUE_SUCCESSFUL, true, deliverCallback, consumerTag -> { });
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
      });
      receive.start();
      return messageReceived[0];
   }
}
