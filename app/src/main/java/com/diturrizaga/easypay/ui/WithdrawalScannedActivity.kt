package com.diturrizaga.easypay.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.model.response.AccountResponse
import com.diturrizaga.easypay.model.response.Transaction
import com.diturrizaga.easypay.qrUtil.EncryptionHelper
import com.google.gson.Gson
import java.lang.RuntimeException

class WithdrawalScannedActivity : AppCompatActivity() {

   private var scannedFullNameTextView : TextView? = null
   private var scannedAgeTextView : TextView? = null


   private var  scannedAccountName : TextView? = null
   private var  scannedAmountName : TextView? = null
   private var  scannedBalanceName : TextView? = null

   private var currentAccount : AccountResponse? = null


   companion object {
      private const val SCANNED_STRING: String = "scanned_string"
      fun getScannedActivity(callingClassContext: Context, encryptedString: String): Intent {
         return Intent(callingClassContext, WithdrawalScannedActivity::class.java)
            .putExtra(SCANNED_STRING, encryptedString)
      }
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_withdrawal_scanned)
      initializeUI()
      if (intent.getSerializableExtra(SCANNED_STRING) == null)
         throw RuntimeException("No encrypted String found in intent")
      val decryptedString = EncryptionHelper.getInstance().getDecryptionString(intent.getStringExtra(SCANNED_STRING))
      val transaction = Gson().fromJson(decryptedString, Transaction::class.java)

      scannedAccountName!!.text = transaction.from_account
      scannedAmountName!!.text = transaction.amount.toString()
      //scannedBalanceName!!.text = transaction.



      val userObject = Gson().fromJson(decryptedString, UserObject::class.java)
      scannedFullNameTextView!!.text = userObject.fullName
      scannedAgeTextView!!.text = userObject.age.toString()
   }

   private fun initializeUI() {

      scannedAccountName = findViewById(R.id.scanned_account_name)
      scannedAmountName = findViewById(R.id.scanned_account_amount)
      scannedBalanceName = findViewById(R.id.scanned_account_balance)

      //scannedFullNameTextView = findViewById(R.id.scannedFullNameTextView)
      //scannedAgeTextView = findViewById(R.id.scannedAgeTextView)
   }
}
