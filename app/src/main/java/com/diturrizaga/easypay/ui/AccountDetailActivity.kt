package com.diturrizaga.easypay.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diturrizaga.easypay.OnGetItemsCallback
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.model.response.TransactionResponse
import com.diturrizaga.easypay.repository.TransactionRepository
import com.diturrizaga.easypay.ui.adapter.TransactionAdapter

class AccountDetailActivity : AppCompatActivity() {

   private var accountName : TextView? = null
   private var accountBalance : TextView? = null
   private var accountType : TextView? = null
   private lateinit var transactionRecyclerView : RecyclerView
   private lateinit var layoutManager : RecyclerView.LayoutManager
   private var transactionRepository = TransactionRepository.getInstance()
   private val TAG = "AccountDetailActivity"
   private var accountId: String? = null

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_account_detail)
      initializeUI()
      setupRecycler()
      showTransactions()
      showDetails()
   }

   private fun showDetails() {

   }

   private fun showTransactions() {
      transactionRepository.getTransactions(
         accountId!!,
         object : OnGetItemsCallback<TransactionResponse> {
            override fun onSuccess(items: List<TransactionResponse>) {
               setAdapter(TransactionAdapter(items,applicationContext))
            }

            override fun onError() {
               Log.v(TAG, "Couldn't bring data from URL")
            }

      })
   }

   private fun initializeUI() {
      accountName = findViewById(R.id.details_account_name)
      accountBalance = findViewById(R.id.details_account_balance)
      accountType = findViewById(R.id.details_account_type)
      transactionRecyclerView = findViewById(R.id.transaction_list)
   }

   private fun setupRecycler(){
      layoutManager = LinearLayoutManager(this)
      transactionRecyclerView.layoutManager = layoutManager
   }

   fun setAdapter(adapter : TransactionAdapter) {
      transactionRecyclerView.adapter = adapter
   }

   fun getIdFromFragment(id : String) {
      accountId = id
   }


}
