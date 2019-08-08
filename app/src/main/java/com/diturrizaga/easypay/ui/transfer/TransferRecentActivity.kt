package com.diturrizaga.easypay.ui.transfer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.diturrizaga.easypay.util.TransactionUtil.Companion.filterBy
import com.diturrizaga.easypay.util.TransactionUtil.Companion.filterTransactions

class TransferRecentActivity : AppCompatActivity() {

   private lateinit var transferRecyclerView : RecyclerView
   private lateinit var transferRvAdapter : TransactionAdapter
   private lateinit var layoutManager: RecyclerView.LayoutManager
   private var transactionRepository = TransactionRepository.getInstance()
   private val TAG = "TransferRecentActivity"
   private var userId: String? = null
   private var transfers : List<Transaction>? = null

   private var adapterListener = object : View.OnClickListener {
      override fun onClick(v: View?) {

      }

   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_transfer_recent)
   }

   private fun initializeUI() {
      layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager
      transferRecyclerView = findViewById(R.id.transfer_list)
      transferRecyclerView.layoutManager = layoutManager
   }

   private fun showTransfers() {
      transactionRepository.getTransactions(
         object : OnGetItemsCallback<Transaction> {
            override fun onSuccess(items: List<Transaction>) {
               transfers = filterTransactions(items, Type.TRANSFER.name)

            }

            override fun onError() {
               TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

         }
      )
   }
}
