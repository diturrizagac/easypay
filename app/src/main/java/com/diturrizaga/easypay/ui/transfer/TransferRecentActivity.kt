package com.diturrizaga.easypay.ui.transfer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diturrizaga.easypay.OnGetItemsCallback
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.Type
import com.diturrizaga.easypay.model.response.Transaction
import com.diturrizaga.easypay.repository.TransactionRepository
import com.diturrizaga.easypay.ui.view.adapter.TransactionAdapter
import com.diturrizaga.easypay.util.TransactionUtil.Companion.filterTransactionsByType
import com.diturrizaga.easypay.util.TransactionUtil.Companion.filterTransactionsByUser

class TransferRecentActivity : AppCompatActivity() {

   private lateinit var transferRecyclerView: RecyclerView
   private lateinit var transferRvAdapter: TransactionAdapter
   private lateinit var layoutManager: RecyclerView.LayoutManager
   private var transactionRepository = TransactionRepository.getInstance()
   private val TAG = "TransferRecentActivity"
   private var userId: String? = null
   private var transfers: List<Transaction>? = null

   private var adapterListener = object : View.OnClickListener {
      override fun onClick(v: View?) {

      }
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_transfer_recent)
      initializeUI()
      retrieveData()
      showTransfers()
   }

   private fun initializeUI() {
      layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager
      transferRecyclerView = findViewById(R.id.transfer_list)
      transferRecyclerView.layoutManager = layoutManager
   }

   private fun retrieveData() {
      userId = intent.extras!!.getString("userId") as String
   }

   private fun showTransfers() {
      transactionRepository.getTransactions(
         object : OnGetItemsCallback<Transaction> {
            override fun onSuccess(items: List<Transaction>) {
               val allTransfers = filterTransactionsByType(items, Type.TRANSFER.name)
               transfers = filterTransactionsByUser(allTransfers, userId!!)
               transferRvAdapter = TransactionAdapter(transfers!!, this@TransferRecentActivity)
               setAdapter(transferRvAdapter)
               setListener()
            }

            override fun onError() {
               Log.v(TAG, "Couldn't bring data from URL")
            }

         }
      )
   }

   private fun setListener() {
      transferRvAdapter.setOnItemClickListener(adapterListener)
   }

   private fun setAdapter(adapter: TransactionAdapter) {
      transferRecyclerView.adapter = adapter
   }
}
