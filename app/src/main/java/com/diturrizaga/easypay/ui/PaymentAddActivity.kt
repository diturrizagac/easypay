package com.diturrizaga.easypay.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.diturrizaga.easypay.R

class PaymentAddActivity : AppCompatActivity() {

   var userId : String? = null

   companion object{
      fun getPaymentAddActivity(context: Context) = Intent(context, PaymentAddActivity::class.java)
      fun getPaymentAddActivity(context: Context, id:String): Intent {
         return Intent(context, PaymentAddActivity::class.java)
      }
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_payment_add)
   }
}
