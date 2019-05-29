package com.diturrizaga.easypay.ui.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.model.response.TransactionResponse
import util.UtilFormatter.Companion.amountToMoneyFormat

class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
   private var transactionCreditor = itemView.findViewById<TextView>(R.id.transaction_creditor)
   private var transactionDate = itemView.findViewById<TextView>(R.id.transaction_date)
   private var transactionAmount = itemView.findViewById<TextView>(R.id.transaction_amount)
   private var transactionStatus = itemView.findViewById<TextView>(R.id.transaction_status)

   fun bind(transaction : TransactionResponse) {
      transactionCreditor.text = transaction.to_account
      transactionDate.text = transaction.activity_date.toString()
      transactionAmount.text = amountToMoneyFormat(transaction.amount!!)
      transactionStatus.text = transaction.status
   }
}