package com.diturrizaga.easypay.ui.withdrawal

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diturrizaga.easypay.OnGetItemsCallback
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.Type
import com.diturrizaga.easypay.model.response.Transaction
import com.diturrizaga.easypay.repository.TransactionRepository
import com.diturrizaga.easypay.ui.view.adapter.TransactionAdapter
import com.diturrizaga.easypay.util.TransactionUtil

class WithdrawalRecentActivity : AppCompatActivity() {

   private lateinit var withdrawalRecyclerView: RecyclerView
   private lateinit var withdrawalRvAdapter: TransactionAdapter
   private lateinit var layoutManager: RecyclerView.LayoutManager
   private var transactionRepository = TransactionRepository.getInstance()
   private val TAG = "WithdrawalRecentActivity"
   private var userId: String? = null
   private var withdrawals: List<Transaction>? = null

   private var adapterListener = object : View.OnClickListener {
      override fun onClick(v: View?) {

      }

   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_withdrawal_recent)
      initializeUI()
      retrieveData()
      showWithdrawals()
   }

   private fun initializeUI() {
      layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager
      withdrawalRecyclerView = findViewById(R.id.withdrawal_list)
      withdrawalRecyclerView.layoutManager = layoutManager
   }

   private fun retrieveData() {
      userId = intent.extras!!.getString("userId") as String
   }

   private fun showWithdrawals() {
      transactionRepository.getTransactions(
         object : OnGetItemsCallback<Transaction> {
            override fun onSuccess(items: List<Transaction>) {
               val allWithdrawals = TransactionUtil.filterTransactionsByType(items, Type.WITHDRAWAL.name)
               withdrawals = TransactionUtil.filterTransactionsByUser(allWithdrawals, userId!!)
               withdrawalRvAdapter = TransactionAdapter(withdrawals!!, this@WithdrawalRecentActivity)
               setAdapter(withdrawalRvAdapter)
               setListener()
            }

            @SuppressLint("LongLogTag")
            override fun onError() {
               Log.v(TAG, "Couldn't bring data from URL")
            }

         }
      )
   }

   private fun setListener() {
      withdrawalRvAdapter.setOnItemClickListener(adapterListener)
   }

   private fun setAdapter(adapter: TransactionAdapter) {
      withdrawalRecyclerView.adapter = adapter
   }
}
