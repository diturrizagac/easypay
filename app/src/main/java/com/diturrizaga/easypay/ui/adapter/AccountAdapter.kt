package com.diturrizaga.easypay.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.model.response.Account
import com.diturrizaga.easypay.util.UtilFormatter


class AccountAdapter(private val accounts: List<Account>, private val mContext: Context) :
   RecyclerView.Adapter<AccountAdapter.AccountViewHolder>(){

   var mOnItemClickListener : View.OnClickListener? = null

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.item_account,parent,false)
      return AccountViewHolder(view)
   }

   override fun getItemCount(): Int {
      return accounts.size
   }

   override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
      val account = accounts[position]
      holder.bind(account)
   }

   fun setOnItemClickListener(itemClickListener: View.OnClickListener) {
      mOnItemClickListener = itemClickListener
   }

   inner class AccountViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

      private var accountName = itemView.findViewById<TextView>(R.id.account_name)
      private var accountBalance = itemView.findViewById<TextView>(R.id.account_balance)
      private var accountType = itemView.findViewById<TextView>(R.id.account_type)
      private var imageArrow = itemView.findViewById<ImageView>(R.id.image_arrow)

      init {
         itemView.tag = this
         itemView.setOnClickListener(mOnItemClickListener)
      }

      fun bind(account: Account) {
         accountName.text = account.account_name
         accountBalance.text = UtilFormatter.amountToMoneyFormat(account.balance!!)
         accountType.text = account.type
      }
   }
}