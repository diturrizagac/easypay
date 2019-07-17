package com.diturrizaga.easypay.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.ui.home.HomeActivity
import com.diturrizaga.easypay.util.NavigationTo.goTo

class SuccessfulOperationActivity : AppCompatActivity() {

   private var fromAccount : String? = null
   private var toAccount : String? = null
   private var amount : Double? = null
   private var balance : Double? = null
   private var userId : String? = null

   private var fromAccountTextView : TextView? = null
   private var toAccountTextView : TextView? = null
   private var amountTextView : TextView? = null
   private var balanceTextView : TextView? = null
   private var goHomeButton : Button? = null


   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_successful_operation)
      initializeUI()
      retrieveDataFromIntent()
      setData()
      setListener()
   }

   private fun initializeUI() {
      fromAccountTextView = findViewById(R.id.scanned_from_account_name)
      toAccountTextView = findViewById(R.id.scanned_to_account_name)
      amountTextView = findViewById(R.id.scanned_account_amount)
      balanceTextView = findViewById(R.id.scanned_account_balance)
      goHomeButton = findViewById(R.id.goHomeButton)
   }

   private fun setData() {
      fromAccountTextView!!.text = fromAccount
      toAccountTextView!!.text = toAccount
      amountTextView!!.text = amount.toString()
      balanceTextView!!.text = balance.toString()
   }

   private fun retrieveDataFromIntent() {
      userId = intent.extras!!.getString("userId")
      fromAccount = intent.extras!!.getString("from_account")
      toAccount = intent.extras!!.getString("to_account")
      amount = intent.extras!!.getDouble("amount")
      balance = intent.extras!!.getDouble("balance")
   }

   private fun setListener() {
      goHomeButton!!.setOnClickListener {
         goTo(HomeActivity::class.java, this, userId!!)
      }
   }
}
