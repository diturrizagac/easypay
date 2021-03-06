package com.diturrizaga.easypay.ui.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.model.response.Transaction
import com.diturrizaga.easypay.util.UtilFormatter.Companion.amountToMoneyFormat
import com.diturrizaga.easypay.util.UtilFormatter.Companion.dateFormatter


class TransactionAdapter(private val transactions: List<Transaction>, private val context: Context) :
   RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

   var mOnItemClickListener : View.OnClickListener? = null

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

   fun setOnItemClickListener(itemClickListener: View.OnClickListener) {
      mOnItemClickListener = itemClickListener
   }

   inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      private var transactionCreditor = itemView.findViewById<TextView>(R.id.transaction_creditor)
      private var transactionDate = itemView.findViewById<TextView>(R.id.transaction_date)
      private var transactionAmount = itemView.findViewById<TextView>(R.id.transaction_amount)
      private var transactionStatus = itemView.findViewById<TextView>(R.id.transaction_status)

      init {
         itemView.tag = this
         itemView.setOnClickListener(mOnItemClickListener)
      }

      fun bind(transaction : Transaction) {
         transactionCreditor.text = transaction.to_account
         transactionDate.text = dateFormatter(transaction.created!!)
         transactionAmount.text = amountToMoneyFormat(transaction.amount!!)
         transactionStatus.text = transaction.status
      }
   }
}