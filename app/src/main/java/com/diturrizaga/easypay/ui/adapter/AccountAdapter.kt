package com.diturrizaga.easypay.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.model.response.AccountResponse
import com.diturrizaga.easypay.ui.viewholder.AccountViewHolder


class AccountAdapter(private val accounts: List<AccountResponse>, private val mContext: Context) :RecyclerView.Adapter<AccountViewHolder>(){
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
}