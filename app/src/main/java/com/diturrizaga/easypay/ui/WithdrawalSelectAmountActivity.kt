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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.AppCompatTextView
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.diturrizaga.easypay.*
import com.diturrizaga.easypay.api.Api
import com.diturrizaga.easypay.model.response.Account
import com.diturrizaga.easypay.model.response.Transaction
import com.diturrizaga.easypay.repository.AccountRepository
import com.diturrizaga.easypay.repository.TransactionRepository
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
   private var transactionRepository = TransactionRepository.getInstance()
   private var userId : String? = null
   private var userAccounts : List<Account>? = null
   private var nameList = ArrayList<String>()
   private var currentAccount : Account? = null
   private var accountName : String? = null


   private var currentTransaction : Transaction? = null


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
            Log.v(TAG,"${currentAccount!!.account_name} selected")
            Toast.makeText(applicationContext,"${currentAccount!!.objectId} selected", Toast.LENGTH_LONG).show()
         }
      }
   }


   private fun getCurrentAccount(name : String) : Account{
      var account : Account? = null
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
         showAlertDialog()
         //startActivity(getWithdrawalGenerateQrActivity(this, currentAccount!!, currentTransaction!!))
      }
   }

   private fun postBackeless() {
      Backendless.initApp(this, Api.APP_ID, Api.API_KEY)
      populateTransaction()
      postTransaction()
   }

   private fun showAlertDialog() {
      // Initialize a new instance of
      val builder = AlertDialog.Builder(this)
      // Set the alert dialog title
      builder.setTitle("App background color")
      // Display a message on alert dialog
      builder.setMessage("Do you want to confirm this operation?")
      // Set a positive button and its click listener on alert dialog
      builder.setPositiveButton("Yes"){dialog, which ->
         // Do something when user press the positive button
         postBackendless()
         Toast.makeText(applicationContext,"Ok, we're sending your transaction",Toast.LENGTH_SHORT).show()
      }
      // Display a negative button on alert dialog
      builder.setNegativeButton("No"){dialog,which ->
         Toast.makeText(applicationContext,"You canceled this operation",Toast.LENGTH_SHORT).show()
      }
      // Display a neutral button on alert dialog
      builder.setNeutralButton("Cancel"){_,_ ->
         Toast.makeText(applicationContext,"You cancelled the dialog.",Toast.LENGTH_SHORT).show()
      }
      // Finally, make the alert dialog using builder
      val dialog: AlertDialog = builder.create()
      // Display the alert dialog on app interface
      dialog.show()
   }

   private fun postTransaction() {
      transactionRepository.createTransaction(
         currentTransaction!!,
         object  : OnPostItemCallback<Transaction> {
            @SuppressLint("LongLogTag")
            override fun onSuccess(item: Transaction) {
               if (currentTransaction != item) {
                  currentTransaction = item
                  Log.v(TAG, "Data posted")
               }
            }

            @SuppressLint("LongLogTag")
            override fun onError() {
               Log.v(TAG, "Couldn't bring data from URL")
            }
         }
      )
   }

   private fun postBackendless() {
      val account = currentAccount!!
      val transaction = currentTransaction!!
      val transactionCollection = ArrayList<Transaction>()
      transactionCollection.add(transaction)
      Backendless.Data.of(Account::class.java).addRelation(
         account,
         "transaction",
         transactionCollection,
         object: AsyncCallback<Int> {
            override fun handleFault(fault: BackendlessFault?) {
               Log.e( "EASYPAY", "server reported an error - " + fault!!.message )
               Toast.makeText(applicationContext,fault.message, Toast.LENGTH_LONG).show()
            }

            override fun handleResponse(response: Int?) {
               Toast.makeText(applicationContext,"Data ", Toast.LENGTH_LONG).show()
            }
         }
      )
   }



   @SuppressLint("LongLogTag")
   private fun setCurrentAccounts(names : List<Account>) {
      val item = names.iterator()
      while (item.hasNext()){
         val accountName = item.next().account_name
         nameList.add(accountName!!)
         Log.v(TAG,accountName)
      }
   }




   /**
    * By REST API
    * RETROFIT callback accounts
    */

   private fun getAccounts() {
      accountRepository.getAccounts(
         userId!!,
         object : OnGetItemsCallback<Account> {
            override fun onSuccess(items: List<Account>) {
               userAccounts = items
               setCurrentAccounts(userAccounts as MutableList<Account>)
               setSpinner()
            }

            @SuppressLint("LongLogTag")
            override fun onError() {
               Log.v(TAG, "Couldn't bring data from URL")
            }
         }
      )
   }

   private fun populateTransaction() {
      currentTransaction = Transaction()
      currentTransaction!!.created = null
      currentTransaction!!.from_account = currentAccount!!.account_name
      currentTransaction!!.activity_date = null
      currentTransaction!!.updated = null
      currentTransaction!!.status = "Done"
      currentTransaction!!.amount = 113.90
      currentTransaction!!.objectId = ""
      currentTransaction!!.to_account = "Current Balance-1502"
      currentTransaction!!.type = "transfer"
      currentTransaction!!.ownerId = null
      currentTransaction!!.___class = "transaction"
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

   /**
    * not working yet, we have to bring account.transactions data, because it's bringing a list of accounts
    */
   inner class SaveTransactionTask : AsyncTask<Account,Any, Account>() {
      override fun doInBackground(vararg accounts: Account?): Account {
         val account = accounts[0]

         val transactionItemList = account!!.transactions
         val savedTransactionsItems = ArrayList<Transaction>()

         for (transaction in transactionItemList!!) {
            val savedTransactionsItem = Backendless.Data.of(Transaction::class.java).save(transaction)
            savedTransactionsItems.add(savedTransactionsItem)
         }

         val savedAccount = Backendless.Data.of(Account::class.java).save(account)
         Backendless.Data.of(Account::class.java).addRelation(
            savedAccount,
            "account:account:n",
            savedTransactionsItems
         )
         return savedAccount
      }
   }
}