package com.diturrizaga.easypay.rabbitMQTest;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {
   ConnectionFactory factory = new ConnectionFactory();
   public void sendInfo(){
      factory.setHost("localhost");
   }

}
