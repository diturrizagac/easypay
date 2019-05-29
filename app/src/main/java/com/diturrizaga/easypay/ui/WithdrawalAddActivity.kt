package com.diturrizaga.easypay.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.diturrizaga.easypay.R

class WithdrawalAddActivity : AppCompatActivity() {

   var generateQrButton : Button? = null
   var scanQrButton : Button? = null

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_withdrawal_add)
      initializeUI()
      generateQrButton!!.setOnClickListener {
         startActivity(WithdrawalGenerateQrActivity.getGenerateQrCodeActivity(this))
      }
      scanQrButton!!.setOnClickListener {
         startActivity(WithdrawalScanQrActivity.getScanQrCodeActivity(this))
      }
   }

   private fun initializeUI() {
      generateQrButton = findViewById(R.id.generateButton)
      scanQrButton = findViewById(R.id.scanButton)
   }
}
