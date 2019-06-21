package com.diturrizaga.easypay.ui.withdrawal

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.diturrizaga.easypay.ui.withdrawal.WithdrawalScanQrActivity.Companion.getWithdrawalScanQrActivity
import com.google.android.material.button.MaterialButton
import java.text.DecimalFormat
import java.text.NumberFormat
import kotlin.collections.ArrayList

class WithdrawalSelectAmountActivity : AppCompatActivity() {

   private val TAG = "WithdrawalSelectAmountActivity"
   private val CLASS = "transaction"
   var ccaAccountTitle : AppCompatTextView? = null
   var ccaSpinner : AppCompatSpinner? = null
   var ccaAmount : AppCompatEditText? = null
   var ccaToAccount : AppCompatEditText? = null

   var continueMDButton : MaterialButton? = null
   var continueButton : Button? = null
   var generateButton : Button? = null

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
      /*ccaAmount!!.addTextChangedListener(object : TextWatcher{
         var current : String = ""
         override fun afterTextChanged(s: Editable?) {
         }

         override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
         }

         override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if(s.toString() != current){
               ccaAmount!!.removeTextChangedListener(this)
               val cleanString = s.toString().replace("[$,.]", "")
               val parsed : Double = cleanString.toDouble()
               val formatted = NumberFormat.getCurrencyInstance().format(parsed/100)
               current = formatted
               current = NumberFormat.getCurrencyInstance().format(parsed/100)
               ccaAmount!!.setText(formatted)
               ccaAmount!!.setSelection(formatted.length)
               ccaAmount!!.addTextChangedListener(this)
            }
         }
      })*/
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

   override fun onSupportNavigateUp(): Boolean {
      finish()
      return true
   }

   private fun setCurrentTransaction() {
      currentTransaction = Transaction()
      currentTransaction!!.created = null
      currentTransaction!!.from_account = currentAccount!!.account_name
      currentTransaction!!.activity_date = null
      currentTransaction!!.updated = null
      currentTransaction!!.status = Status.DONE.name
      currentTransaction!!.amount = ccaAmount!!.text.toString().toDouble()
      currentTransaction!!.objectId = ""
      currentTransaction!!.to_account = ccaToAccount!!.text.toString()
      currentTransaction!!.type = Type.WITHDRAWAL.name
      currentTransaction!!.ownerId = null
      currentTransaction!!.___class = CLASS
   }
}