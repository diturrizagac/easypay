package com.diturrizaga.easypay.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.diturrizaga.easypay.R

class WithdrawalAddActivity : AppCompatActivity() {

   var generateButton : Button? = null
   var scanButton : Button? = null

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_withdrawal_add)
      initializeUI()
      generateButton!!.setOnClickListener {
         startActivity(WithdrawalGenerateQrActivity.getGenerateQrCodeActivity(this))
      }
      scanButton!!.setOnClickListener {
         startActivity(WithdrawalScanQrActivity.getScanQrCodeActivity(this))
      }
   }

   fun initializeUI() {
      generateButton = findViewById(R.id.generateButton)
      scanButton = findViewById(R.id.scanButton)
   }
}
