package com.diturrizaga.easypay.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diturrizaga.easypay.OnGetItemsCallback
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.model.response.Account
import com.diturrizaga.easypay.model.response.Transaction
import com.diturrizaga.easypay.repository.TransactionRepository
import com.diturrizaga.easypay.ui.view.adapter.TransactionAdapter
import com.diturrizaga.easypay.ui.view.viewmodel.AccountListViewModel
import com.diturrizaga.easypay.util.UtilFormatter.Companion.amountToMoneyFormat


class AccountDetailActivity : AppCompatActivity() {

   private var accountName : TextView? = null
   private var accountBalance : TextView? = null
   private var accountType : TextView? = null
   private lateinit var transactionRecyclerView : RecyclerView
   private lateinit var layoutManager : RecyclerView.LayoutManager
   private var transactionRepository = TransactionRepository.getInstance()
   private val TAG = "AccountDetailActivity"
   private var account : Account? = null
   private var accountId: String? = null
   private var userId: String? = null
   private var viewModel : AccountListViewModel? = null


   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_account_detail)
      viewModel = ViewModelProviders.of(this).get(AccountListViewModel::class.java)
      initializeUI()
      setupRecycler()
      retrieveData()
      showDetails()
      showTransactions()
   }

   private fun retrieveData() {
      account = intent.extras!!.getSerializable("account") as Account
      userId = intent.extras!!.getString("userId")

      accountId = account!!.objectId

      viewModel!!.accountId = accountId
      viewModel!!.userId = userId
   }

   private fun showDetails() {
      accountName!!.text = account!!.account_name
      accountBalance!!.text = amountToMoneyFormat(account!!.balance!!)
      accountType!!.text = account!!.type
   }

   private fun showTransactions() {
      transactionRepository.getTransactions(
         accountId!!,
         object : OnGetItemsCallback<Transaction> {
            override fun onSuccess(items: List<Transaction>) {
               setAdapter(TransactionAdapter(items,applicationContext))
            }

            override fun onError() {
               Log.v(TAG, "Couldn't bring data from URL")
            }
         }
      )
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
}
