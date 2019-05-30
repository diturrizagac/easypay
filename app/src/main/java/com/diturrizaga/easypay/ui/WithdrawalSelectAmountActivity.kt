package com.diturrizaga.easypay.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.AppCompatTextView
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.ui.WithdrawalScanQrActivity.Companion.getWithdrawalScanQrActivity
import com.google.android.material.button.MaterialButton

class WithdrawalSelectAmountActivity : AppCompatActivity() {

   var ccaAccountTitle : AppCompatTextView? = null
   var ccaSpinner : AppCompatSpinner? = null
   var ccaAmount : AppCompatEditText? = null
   var continueMDButton : MaterialButton? = null
   var continueButton : Button? = null

   companion object{
      fun getWithdrawalSelectAmountActivity(context: Context) = Intent(context, WithdrawalSelectAmountActivity::class.java)
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_select_amount)
      initializeUI()
      setListener()
   }

   private fun initializeUI() {
      ccaAccountTitle = findViewById(R.id.transaction_cca_account)
      ccaSpinner = findViewById(R.id.accounts_spinner)
      ccaAmount = findViewById(R.id.transaction_cca_amount)
      continueButton = findViewById(R.id.scan_button)
   }

   private fun setListener(){
      continueButton!!.setOnClickListener {
         startActivity(getWithdrawalScanQrActivity(this))
      }
   }
}
