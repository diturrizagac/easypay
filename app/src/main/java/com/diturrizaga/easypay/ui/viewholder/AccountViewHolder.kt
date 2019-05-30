package com.diturrizaga.easypay.ui.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.model.response.AccountResponse
import util.UtilFormatter.Companion.amountToMoneyFormat

class AccountViewHolder(itemvView : View) : RecyclerView.ViewHolder(itemvView) {
   private var accountName = itemView.findViewById<TextView>(R.id.account_name)
   private var accountBalance = itemView.findViewById<TextView>(R.id.account_balance)
   private var accountType = itemView.findViewById<TextView>(R.id.account_type)
   private var imageArrow = itemView.findViewById<ImageView>(R.id.image_arrow)

   /**
    * TODO: IMPLEMENT SET ON CLICK LISTENER
    */

   fun bind(account: AccountResponse) {
      accountName.text = account.account_name
      accountBalance.text = amountToMoneyFormat(account.balance!!)
      accountType.text = account.type
   }

}