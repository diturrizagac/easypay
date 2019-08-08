package com.diturrizaga.easypay.ui.payment

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
import com.diturrizaga.easypay.util.TransactionUtil.Companion.filterBy
import com.diturrizaga.easypay.util.TransactionUtil.Companion.filterTransactions

class PaymentRecentActivity : AppCompatActivity() {

   private lateinit var paymentRecyclerView : RecyclerView
   private lateinit var paymentRvAdapter : TransactionAdapter
   private lateinit var layoutManager: RecyclerView.LayoutManager
   private var transactionRepository = TransactionRepository.getInstance()
   private val TAG = "PaymentRecentActivity"
   private var userId: String? = null
   private var payments : List<Transaction>? = null


   private var adapterListener = object : View.OnClickListener {
      override fun onClick(v: View?) {

      }

   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_payment_recent)
      initializeUI()
      showPayments()
   }

   private fun initializeUI() {
      layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager
      paymentRecyclerView = findViewById(R.id.payment_list)
      paymentRecyclerView.layoutManager = layoutManager
   }

   private fun showPayments() {
      transactionRepository.getTransactions(
         object : OnGetItemsCallback<Transaction> {
            override fun onSuccess(items: List<Transaction>) {
               payments = filterTransactions(items, Type.PAYMENT.name)
               paymentRvAdapter = TransactionAdapter(payments!!, this@PaymentRecentActivity)
               setAdapter(paymentRvAdapter)
               setListener()
            }

            override fun onError() {
               Log.v(TAG, "Couldn't bring data from URL")
            }
         }
      )
   }

   private fun setListener() {
      paymentRvAdapter.setOnItemClickListener(adapterListener)
   }

   private fun setAdapter(adapter: TransactionAdapter) {
      paymentRecyclerView.adapter = adapter
   }
}
