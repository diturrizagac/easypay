package com.diturrizaga.easypay.ui.transfer

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import com.diturrizaga.easypay.model.response.TransactionAux
import com.diturrizaga.easypay.repository.AccountRepository
import com.diturrizaga.easypay.repository.TransactionRepository
import com.google.android.material.button.MaterialButton

class TransferAddActivity : AppCompatActivity() {

   private val TAG = "TransferAddActivity"
   private val CLASS = "transaction"
   var transferAccountTitle: AppCompatTextView? = null
   var transferSpinner: AppCompatSpinner? = null
   var transferAmount: AppCompatEditText? = null
   var transferToAccount: AppCompatEditText? = null

   var continueMDButton: MaterialButton? = null
   var continueButton: Button? = null

   private var accountRepository = AccountRepository.getInstance()
   private var transactionRepository = TransactionRepository.getInstance()
   private var userId: String? = null
   private var userAccounts: List<Account>? = null
   private var accountName: String? = null
   private var nameList = ArrayList<String>()

   private var currentAccount: Account? = null
   private var currentTransaction: Transaction? = null

   var lista: List<TransactionAux>? = null
   var filtro: String? = null
   var trasnferList: List<TransactionAux>? = null

   companion object {
      fun getTransferAddActivity(context: Context) = Intent(context, TransferAddActivity::class.java)
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_transfer_add)
      initializeUI()
      retrieveData()
      setListener()
      getAccounts()
   }

   private fun initializeUI() {
      transferAccountTitle = findViewById(R.id.transfer_account)
      transferSpinner = findViewById(R.id.transfer_accounts_spinner)
      transferToAccount = findViewById(R.id.transfer_send_to)
      transferAmount = findViewById(R.id.transfer_amount)
      continueButton = findViewById(R.id.transfer_continue_button)
      //continueMDButton = findViewById(R.id.transfer_continue_button_md)
   }

   private fun retrieveData() {
      userId = intent.extras!!.getString("userId")
   }

   private fun setListener() {
      continueButton!!.setOnClickListener {
         saveOnBackendless()
         showAlertDialog()
      }
   }

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

   private fun saveOnBackendless() {
      Backendless.initApp(this, Api.APP_ID, Api.API_KEY)
      //populateTransaction()
      setCurrentTransaction()
      postTransaction()
   }

   private fun showAlertDialog() {
      // Initialize a new instance of
      val builder = AlertDialog.Builder(this)
      // Set the alert dialog title
      builder.setTitle("Confirm your transaction")
      // Display a message on alert dialog
      builder.setMessage("Do you want to confirm this transaction?")
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

   @SuppressLint("ResourceType")
   private fun setSpinner() {
      val dataAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nameList)
      dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
      transferSpinner!!.adapter = dataAdapter
      setSpinnerListener()
   }

   private fun setSpinnerListener() {
      transferSpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

   @SuppressLint("LongLogTag")
   private fun setCurrentAccounts(names : List<Account>) {
      val item = names.iterator()
      while (item.hasNext()){
         val accountName = item.next().account_name
         nameList.add(accountName!!)
         Log.v(TAG,accountName)
      }
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

   private fun setCurrentTransaction() {
      currentTransaction = Transaction()
      currentTransaction!!.created = null
      currentTransaction!!.from_account = currentAccount!!.account_name
      currentTransaction!!.activity_date = null
      currentTransaction!!.updated = null
      currentTransaction!!.status = Status.DONE.name
      currentTransaction!!.amount = transferAmount!!.text.toString().toDouble()
      currentTransaction!!.objectId = ""
      currentTransaction!!.to_account = transferToAccount!!.text.toString()
      currentTransaction!!.type = Type.WITHDRAWAL.name
      currentTransaction!!.ownerId = null
      currentTransaction!!.___class = CLASS
   }
}