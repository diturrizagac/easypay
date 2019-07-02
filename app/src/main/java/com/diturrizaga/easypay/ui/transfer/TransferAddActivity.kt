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
import com.diturrizaga.easypay.repository.AccountRepository
import com.diturrizaga.easypay.repository.TransactionRepository
import com.diturrizaga.easypay.ui.Status
import com.google.android.material.button.MaterialButton

class TransferAddActivity : AppCompatActivity() {


   private val TAG = "TransferAddActivity"
   private val CLASS = "transaction"

   private var accountRepository = AccountRepository.getInstance()
   private var transactionRepository = TransactionRepository.getInstance()

   var transferAccountTitle: AppCompatTextView? = null
   var transferSpinner: AppCompatSpinner? = null
   var transferAmount: AppCompatEditText? = null
   var transferToAccount: AppCompatEditText? = null
   var continueMDButton: MaterialButton? = null
   var continueButton: Button? = null

   private var isSum: Boolean? = null

   private var payerUserId: String? = null
   private var payerUserAccounts: List<Account>? = null
   private var accountName: String? = null
   private var payerNameAccountsSpinner = ArrayList<String>()
   private var payerCurrentAccount: Account? = null

   private var creditorUserId: String ? = null
   private var creditorUserAccounts: List<Account>? = null
   private var creditorCurrentAccount: Account? = null

   private var currentTransaction: Transaction? = null
   private var creditorCurrentTransaction: Transaction? = null

   companion object {
      fun getTransferAddActivity(context: Context) = Intent(context, TransferAddActivity::class.java)
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_transfer_add)
      initializeUi()
      retrieveDataFromIntent()
      setListener()
      getAccountsFromRepository()
   }

   private fun initializeUi() {
      transferAccountTitle = findViewById(R.id.transfer_account)
      transferSpinner = findViewById(R.id.transfer_accounts_spinner)
      transferToAccount = findViewById(R.id.transfer_send_to)
      transferAmount = findViewById(R.id.transfer_amount)
      continueButton = findViewById(R.id.transfer_continue_button)
      //continueMDButton = findViewById(R.id.transfer_continue_button_md)
   }

   private fun retrieveDataFromIntent() {
      payerUserId = intent.extras!!.getString("userId")
   }

   private fun setListener() {
      continueButton!!.setOnClickListener {
         postTransactionOnBackendless()
         showAlertDialog()
      }
   }

   private fun getAccountsFromRepository() {
      accountRepository.getAccounts(
         payerUserId!!,
         object : OnGetItemsCallback<Account> {
            override fun onSuccess(items: List<Account>) {
               payerUserAccounts = items
               setCurrentAccounts(payerUserAccounts as MutableList<Account>)
               setSpinner()
            }

            @SuppressLint("LongLogTag")
            override fun onError() {
               Log.v(TAG, "Couldn't bring data from URL")
            }
         }
      )
   }

   private fun getAccountsFromBL() {

   }

   private fun postTransactionOnBackendless() {
      Backendless.initApp(this, Api.APP_ID, Api.API_KEY)
      //populateTransaction()
      populateCurrentTransaction()
      createTransaction()
      createCreditorTransaction()
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
         setRelationOnBackendless(payerCurrentAccount!!, currentTransaction!! )
         setRelationOnBackendless(creditorCurrentAccount!!,creditorCurrentTransaction!!)
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

   private fun createCreditorTransaction() {
      transactionRepository.createTransaction(
         creditorCurrentTransaction!!,
         object  : OnPostItemCallback<Transaction> {
            @SuppressLint("LongLogTag")
            override fun onSuccess(item: Transaction) {
               if (creditorCurrentTransaction != item) {
                  creditorCurrentTransaction = item
                  Log.v(TAG, "Data Creditor posted")
               }
            }

            @SuppressLint("LongLogTag")
            override fun onError() {
               Log.v(TAG, "Couldn't bring data from URL of creditor")
            }
         }
      )
   }

   private fun createTransaction() {
      transactionRepository.createTransaction(
         currentTransaction!!,
         object  : OnPostItemCallback<Transaction> {
            @SuppressLint("LongLogTag")
            override fun onSuccess(item: Transaction) {
               if (currentTransaction != item) {
                  currentTransaction = item
                  Log.v(TAG, "Data Payer posted")
               }
            }

            @SuppressLint("LongLogTag")
            override fun onError() {
               Log.v(TAG, "Couldn't bring data from URL of payer")
            }
         }
      )
   }

   @SuppressLint("ResourceType")
   private fun setSpinner() {
      val dataAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, payerNameAccountsSpinner)
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
            payerCurrentAccount = getCurrentAccount(accountName!!)
            Log.v(TAG,"${payerCurrentAccount!!.account_name} selected")
            Toast.makeText(applicationContext,"${payerCurrentAccount!!.objectId} selected", Toast.LENGTH_LONG).show()
         }
      }
   }

   private fun getCurrentAccount(name : String) : Account{
      var account : Account? = null
      val iterator = payerUserAccounts!!.iterator()
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
         payerNameAccountsSpinner.add(accountName!!)
         Log.v(TAG,accountName)
      }
   }

   private fun setRelationOnBackendless(account : Account, transaction : Transaction) {
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
               Toast.makeText(applicationContext,"Data  $response", Toast.LENGTH_LONG).show()
            }
         }
      )
   }

   private fun validateTransaction() {
      createTransaction()

   }



   private fun populateCurrentTransaction() {
      currentTransaction = Transaction()
      currentTransaction!!.created = null
      currentTransaction!!.from_account = payerCurrentAccount!!.account_name
      currentTransaction!!.activity_date = null
      currentTransaction!!.updated = null
      currentTransaction!!.status = Status.DONE.name
      currentTransaction!!.amount = transferAmount!!.text.toString().toDouble()
      currentTransaction!!.objectId = ""
      currentTransaction!!.to_account = transferToAccount!!.text.toString()
      currentTransaction!!.type = Type.TRANSFER.name
      currentTransaction!!.ownerId = null
      currentTransaction!!.___class = CLASS
      creditorCurrentTransaction = currentTransaction
   }
}