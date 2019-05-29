package com.diturrizaga.easypay.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.model.response.TransactionResponse
import com.diturrizaga.easypay.ui.viewholder.TransactionViewHolder

class TransactionAdapter(private val transactions: List<TransactionResponse>, private val context: Context) :
   RecyclerView.Adapter<TransactionViewHolder>() {
   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction,parent,false)
      return TransactionViewHolder(view)
   }

   override fun getItemCount(): Int {
      return transactions.size
   }

   override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
      val transaction = transactions[position]
      holder.bind(transaction)
   }
}