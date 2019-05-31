package com.diturrizaga.easypay.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.model.response.AccountResponse
import com.diturrizaga.easypay.ui.AccountDetailActivity
import com.diturrizaga.easypay.ui.fargments.AccountListFragment
import util.UtilFormatter


class AccountAdapter(private val accounts: List<AccountResponse>, private val mContext: Context) :
   RecyclerView.Adapter<AccountAdapter.AccountViewHolder>(){



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

   inner class AccountViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView),
      AccountListFragment.OnCurrentUserListener {

      private var userId : String? = null
      private var accountListFragment = AccountListFragment()
      private var accountName = itemView.findViewById<TextView>(R.id.account_name)
      private var accountBalance = itemView.findViewById<TextView>(R.id.account_balance)
      private var accountType = itemView.findViewById<TextView>(R.id.account_type)
      private var imageArrow = itemView.findViewById<ImageView>(R.id.image_arrow)

      val myView = itemView.setOnClickListener {
         val position = adapterPosition
         accountListFragment.userListener = this
         if (position != RecyclerView.NO_POSITION) {
            val accountSelected = accounts[position]
            val intent = Intent(mContext, AccountDetailActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("account",accountSelected)
            mContext.startActivity(intent)
         }
      }

      fun bind(account: AccountResponse) {
         accountName.text = account.account_name
         accountBalance.text = UtilFormatter.amountToMoneyFormat(account.balance!!)
         accountType.text = account.type
      }

      override fun getCurrentUserId(id: String) {
         userId = id
      }
   }

}