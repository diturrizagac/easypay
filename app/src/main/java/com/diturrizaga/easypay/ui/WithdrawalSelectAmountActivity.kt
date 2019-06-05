package com.diturrizaga.easypay.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.AppCompatTextView
import com.diturrizaga.easypay.OnGetItemsCallback
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.model.response.AccountResponse
import com.diturrizaga.easypay.repository.AccountRepository
import com.diturrizaga.easypay.ui.WithdrawalScanQrActivity.Companion.getWithdrawalScanQrActivity
import com.google.android.material.button.MaterialButton

class WithdrawalSelectAmountActivity : AppCompatActivity() {

   private val TAG = "WithdrawalSelectAmountActivity"
   var ccaAccountTitle : AppCompatTextView? = null
   var ccaSpinner : AppCompatSpinner? = null
   var ccaAmount : AppCompatEditText? = null
   var continueMDButton : MaterialButton? = null
   var continueButton : Button? = null

   private var accountRepository = AccountRepository.getInstance()
   private var userId : String? = null
   private var currentAccounts : List<AccountResponse>? = null
   private var nameList = ArrayList<String>()

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
   }

   @SuppressLint("ResourceType")
   private fun setSpinner() {
      val dataAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,nameList)
      dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
      ccaSpinner!!.adapter = dataAdapter
   }

   private fun setListener(){
      continueButton!!.setOnClickListener {
         startActivity(getWithdrawalScanQrActivity(this))
      }
   }

   private fun getAccounts() {
      accountRepository.getAccounts(
         userId!!,
         object : OnGetItemsCallback<AccountResponse> {
            override fun onSuccess(items: List<AccountResponse>) {
               currentAccounts = items
               setCurrentAccounts(currentAccounts as MutableList<AccountResponse>)
               setSpinner()
            }

            @SuppressLint("LongLogTag")
            override fun onError() {
               Log.v(TAG, "Couldn't bring data from URL")
            }
         }
      )
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
}