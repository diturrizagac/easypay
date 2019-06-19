package com.diturrizaga.easypay.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.model.response.Transaction
import com.diturrizaga.easypay.util.UtilFormatter


class TransactionAdapter(private val transactions: List<Transaction>, private val context: Context) :
   RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {
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

   inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      private var transactionCreditor = itemView.findViewById<TextView>(R.id.transaction_creditor)
      private var transactionDate = itemView.findViewById<TextView>(R.id.transaction_date)
      private var transactionAmount = itemView.findViewById<TextView>(R.id.transaction_amount)
      private var transactionStatus = itemView.findViewById<TextView>(R.id.transaction_status)

      fun bind(transaction : Transaction) {
         transactionCreditor.text = transaction.to_account
         transactionDate.text = transaction.activity_date.toString()
         transactionAmount.text = UtilFormatter.amountToMoneyFormat(transaction.amount!!)
         transactionStatus.text = transaction.status
      }
   }
}