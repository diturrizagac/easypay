package com.diturrizaga.easypay.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.diturrizaga.easypay.R

class WithdrawalAddActivity : AppCompatActivity() {

   var generateQrButton : Button? = null
   var scanQrButton : Button? = null

   companion object{
      fun getWithdrawalAddActivity(context: Context) = Intent(context, WithdrawalAddActivity::class.java)
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_withdrawal_add)
      initializeUI()
      setListeners()
   }

   private fun setListeners() {
      generateQrButton!!.setOnClickListener {
         startActivity(WithdrawalGenerateQrActivity.getWithdrawalGenerateQrActivity(this))
      }
      scanQrButton!!.setOnClickListener {
         startActivity(WithdrawalScanQrActivity.getWithdrawalScanQrActivity(this))
      }
   }

   private fun initializeUI() {
      generateQrButton = findViewById(R.id.generateButton)
      scanQrButton = findViewById(R.id.scanButton)
   }
}
