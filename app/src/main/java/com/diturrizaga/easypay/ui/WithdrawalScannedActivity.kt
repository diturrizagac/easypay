package com.diturrizaga.easypay.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.diturrizaga.easypay.OnPostItemCallback
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.model.response.AccountResponse
import com.diturrizaga.easypay.model.response.Transaction
import com.diturrizaga.easypay.model.response.TransactionResponse
import com.diturrizaga.easypay.qrUtil.EncryptionHelper
import com.diturrizaga.easypay.repository.TransactionRepository
import com.google.gson.Gson
import java.lang.RuntimeException

class WithdrawalScannedActivity : AppCompatActivity() {

   private var TAG = "WithdrawalScannedActivity"
   private var scannedFullNameTextView : TextView? = null
   private var scannedAgeTextView : TextView? = null


   private var  scannedAccountName : TextView? = null
   private var  scannedAmountName : TextView? = null
   private var  scannedBalanceName : TextView? = null

   private var currentAccount : AccountResponse? = null
   private var currentTransaction : TransactionResponse? = null
   private var valueScanned : String? = null
   private var transactionRepository = TransactionRepository.getInstance()
   private var transaction : TransactionResponse? = null


   companion object {
      private const val SCANNED_STRING: String = "scanned_string"
      fun getScannedActivity(callingClassContext: Context, encryptedString: String): Intent {
         return Intent(callingClassContext, WithdrawalScannedActivity::class.java)
            .putExtra(SCANNED_STRING, encryptedString)
      }

      fun getScannedActivity(callingClassContext: Context, encryptedString: String, account : AccountResponse): Intent {
         return Intent(callingClassContext, WithdrawalScannedActivity::class.java)
            .putExtra(SCANNED_STRING, encryptedString)
            .putExtra("account", account)
      }
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_withdrawal_scanned)
      initializeUI()
      retrieveData()
      setData()
   }

   private fun retrieveData() {
      if (intent.getSerializableExtra(SCANNED_STRING) == null)
         throw RuntimeException("No encrypted String found in intent")

      /**
       * recently implemented
       */
      valueScanned = intent.getSerializableExtra(SCANNED_STRING) as String
      if (valueScanned == null) {
         throw RuntimeException("No encrypted String found in intent")
      }
      currentAccount = intent.extras!!.getSerializable("account") as AccountResponse
   }

   private fun createTransaction() {
      transactionRepository.createTransaction(
         currentTransaction!!,
         object : OnPostItemCallback<TransactionResponse> {
            override fun onSuccess(item: TransactionResponse) {
               transaction = item
               Log.v(TAG, transaction!!.from_account)
            }

            override fun onError() {
               Log.v(TAG, "Couldn't bring data from URL")
            }

         }
      )
   }

   private fun setData() {
      //val decryptedString = EncryptionHelper.getInstance().getDecryptionString(intent.getStringExtra(SCANNED_STRING))
      val decryptedString = EncryptionHelper.getInstance().getDecryptionString(valueScanned)
      currentTransaction = Gson().fromJson(decryptedString, TransactionResponse::class.java)


      scannedAccountName!!.text = currentTransaction!!.from_account
      scannedAmountName!!.text = currentTransaction!!.amount.toString()
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
