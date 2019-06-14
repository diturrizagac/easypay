package com.diturrizaga.easypay.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.diturrizaga.easypay.OnPostItemCallback
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.model.response.Account
import com.diturrizaga.easypay.model.response.Transaction
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

   private var currentAccount : Account? = null
   private var currentTransaction : Transaction? = null
   private var valueScanned : String? = null
   private var transactionRepository = TransactionRepository.getInstance()
   private var transaction : Transaction? = null


   companion object {
      private const val SCANNED_STRING: String = "scanned_string"
      fun getScannedActivity(callingClassContext: Context, encryptedString: String): Intent {
         return Intent(callingClassContext, WithdrawalScannedActivity::class.java)
            .putExtra(SCANNED_STRING, encryptedString)
      }

      fun getScannedActivity(callingClassContext: Context, encryptedString: String, account : Account): Intent {
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


   /**
    *
    * RETRIEVE SCANNED DATA FROM CAMERA
    */

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
      currentAccount = intent.extras!!.getSerializable("account") as Account
   }

   /**
    * CALLING SERVER TO SAVE DATA
    */

   private fun createTransaction() {
      transactionRepository.createTransaction(
         currentTransaction!!,
         object : OnPostItemCallback<Transaction> {
            override fun onSuccess(item: Transaction) {
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
      currentTransaction = Gson().fromJson(decryptedString, Transaction::class.java)


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
