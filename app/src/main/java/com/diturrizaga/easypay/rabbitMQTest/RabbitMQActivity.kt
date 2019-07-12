package com.diturrizaga.easypay.rabbitMQTest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.diturrizaga.easypay.R

class RabbitMQActivity : AppCompatActivity() {

   lateinit var publishButton : Button
   lateinit var messageEditText : EditText
   lateinit var resultTextView : TextView
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_rabbit_mq)
      initializeUI()
   }

   fun initializeUI() {
      publishButton = findViewById(R.id.publishButton)
      messageEditText = findViewById(R.id.messageText)
      resultTextView = findViewById(R.id.resultTextView)
   }

   fun setListener() {

   }

}
