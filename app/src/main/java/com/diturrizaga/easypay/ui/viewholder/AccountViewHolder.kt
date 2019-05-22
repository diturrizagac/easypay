package com.diturrizaga.easypay.ui.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.model.response.AccountResponse

class AccountViewHolder(itemvView : View) : RecyclerView.ViewHolder(itemvView) {
   private var account_name = itemView.findViewById<TextView>(R.id.account_name)
   private var account_balance = itemView.findViewById<TextView>(R.id.account_balance)
   private var account_type = itemView.findViewById<TextView>(R.id.account_type)
   private var image_arrow = itemView.findViewById<ImageView>(R.id.image_arrow)

   /**
    * TODO: IMPLEMENT SET ON CLICK LISTENER
    */

   fun bind(account: AccountResponse) {
      account_name.text = account.account_name
      account_balance.text = account.balance.toString()
      account_type.text = account.type
   }

}