package com.diturrizaga.easypay.ui.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.model.response.Transaction
import com.diturrizaga.easypay.repository.TransactionRepository
import com.diturrizaga.easypay.ui.view.adapter.TransactionAdapter

class PaymentRecentActivity : AppCompatActivity() {

   private lateinit var paymentRecyclerView : RecyclerView
   private lateinit var layoutManager: RecyclerView.LayoutManager
   private var transactionRepository = TransactionRepository.getInstance()
   private val TAG = "PaymentRecentActivity"
   private var userId: String? = null
   private var payments : List<Transaction>? = null
   private lateinit var paymentRvAdapter : TransactionAdapter

   private var adapterListener = object : View.OnClickListener {
      override fun onClick(v: View?) {

      }

   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_payment_recent)
   }

   private fun showPayments() {
      transactionRepository.getTransactions()
   }
}
