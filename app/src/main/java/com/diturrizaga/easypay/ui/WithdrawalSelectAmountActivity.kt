package com.diturrizaga.easypay.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.AppCompatTextView
import com.backendless.Backendless
import com.diturrizaga.easypay.OnGetItemsCallback
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.Status
import com.diturrizaga.easypay.Type
import com.diturrizaga.easypay.api.Api
import com.diturrizaga.easypay.model.response.AccountResponse
import com.diturrizaga.easypay.model.response.TransactionResponse
import com.diturrizaga.easypay.repository.AccountRepository
import com.diturrizaga.easypay.ui.WithdrawalGenerateQrActivity.Companion.getWithdrawalGenerateQrActivity
import com.diturrizaga.easypay.ui.WithdrawalScanQrActivity.Companion.getWithdrawalScanQrActivity
import com.google.android.material.button.MaterialButton
import kotlin.collections.ArrayList



class WithdrawalSelectAmountActivity : AppCompatActivity() {

   private val TAG = "WithdrawalSelectAmountActivity"
   private val CLASS = "transaction"
   var ccaAccountTitle : AppCompatTextView? = null
   var ccaSpinner : AppCompatSpinner? = null
   var continueMDButton : MaterialButton? = null
   var continueButton : Button? = null
   var generateButton : Button? = null

   var ccaAmount : AppCompatEditText? = null
   var ccaToAccount : AppCompatEditText? = null


   private var accountRepository = AccountRepository.getInstance()
   private var userId : String? = null
   private var userAccounts : List<AccountResponse>? = null
   private var nameList = ArrayList<String>()
   private var currentAccount : AccountResponse? = null
   private var accountName : String? = null


   private var currentTransaction : TransactionResponse? = null


   companion object{
      fun getWithdrawalSelectAmountActivity(context: Context) = Intent(context, WithdrawalSelectAmountActivity::class.java)
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_select_amount)
      initializeUI()
      retrieveData()
      setListener()
      getAccounts()

   }

   private fun retrieveData() {
      userId = intent.extras!!.getString("userId")
   }

   private fun initializeUI() {
      ccaAccountTitle = findViewById(R.id.transaction_cca_account)
      ccaSpinner = findViewById(R.id.accounts_spinner)
      ccaAmount = findViewById(R.id.transaction_cca_amount)
      continueButton = findViewById(R.id.scan_button)
      generateButton = findViewById(R.id.generate_button)
   }

   @SuppressLint("ResourceType")
   private fun setSpinner() {
      val dataAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,nameList)
      dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
      ccaSpinner!!.adapter = dataAdapter
      setSpinnerListener()
   }



   private fun setSpinnerListener() {
      ccaSpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
         override fun onNothingSelected(parent: AdapterView<*>?) {
            val name = parent!!.getItemAtPosition(0)
         }

         @SuppressLint("LongLogTag")
         override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            accountName = parent!!.selectedItem as String
            //Toast.makeText(applicationContext,"$accountName selected", Toast.LENGTH_LONG).show()
            currentAccount = getCurrentAccount(accountName!!)
            Toast.makeText(applicationContext,"${currentAccount!!.objectId} selected", Toast.LENGTH_LONG).show()
         }
      }
   }


   private fun getCurrentAccount(name : String) : AccountResponse{
      var account : AccountResponse? = null
      val iterator = userAccounts!!.iterator()
      while (iterator.hasNext()) {
         account = iterator.next()
         if (account.account_name == name){
            return account
         } else {
            account = null
         }
      }
      return account ?: throw NullPointerException("Expression 'account' must not be null")
   }

   private fun setListener(){
      continueButton!!.setOnClickListener {
         startActivity(getWithdrawalScanQrActivity(this,currentAccount!!))
      }

      generateButton!!.setOnClickListener {
         postBackeless()
         //startActivity(getWithdrawalGenerateQrActivity(this, currentAccount!!, currentTransaction!!))
      }
   }

   private fun postBackeless() {
      Backendless.initApp(this, Api.APP_ID, Api.API_KEY)
      SaveTransactionTask().execute(currentAccount)
   }


   /**
    * not working yet, we have to bring account.transactions data, because it's bringing a list of accounts
    */
   inner class SaveTransactionTask : AsyncTask<AccountResponse,Any, AccountResponse>() {
      override fun doInBackground(vararg accounts: AccountResponse?): AccountResponse {
         val account = accounts[0]

         val transactionItemList = account!!.transactions
         val savedTransactionsItems = ArrayList<TransactionResponse>()

         for (transaction in transactionItemList!!) {
            val savedTransactionsItem = Backendless.Data.of(TransactionResponse::class.java).save(transaction)
            savedTransactionsItems.add(savedTransactionsItem)
         }

         val savedAccount = Backendless.Data.of(AccountResponse::class.java).save(account)
         Backendless.Data.of(AccountResponse::class.java).addRelation(
            savedAccount,
            "account:account:n",
            savedTransactionsItems
         )
         return savedAccount
      }

   }

   private fun setCurrentTransaction() {
      currentTransaction!!.created = null
      currentTransaction!!.from_account = currentAccount!!.account_name
      currentTransaction!!.activity_date = null
      currentTransaction!!.updated = null
      currentTransaction!!.status = Status.DONE.name
      currentTransaction!!.amount = ccaAmount!!.text.toString().toDouble()
      currentTransaction!!.objectId = ""
      currentTransaction!!.to_account = ccaToAccount!!.text.toString()
      currentTransaction!!.type = Type.PAYMENT.name
      currentTransaction!!.ownerId = null
      currentTransaction!!.___class = CLASS
   }


   @SuppressLint("LongLogTag")
   private fun setCurrentAccounts(names : List<AccountResponse>) {
      val item = names.iterator()
      while (item.hasNext()){
         val accountName = item.next().account_name
         nameList.add(accountName!!)
         Log.v(TAG,accountName)
      }
   }


   /**
    * RETROFIT callback accounts
    */

   private fun getAccounts() {
      accountRepository.getAccounts(
         userId!!,
         object : OnGetItemsCallback<AccountResponse> {
            override fun onSuccess(items: List<AccountResponse>) {
               userAccounts = items
               setCurrentAccounts(userAccounts as MutableList<AccountResponse>)
               setSpinner()
            }

            @SuppressLint("LongLogTag")
            override fun onError() {
               Log.v(TAG, "Couldn't bring data from URL")
            }
         }
      )
   }
}