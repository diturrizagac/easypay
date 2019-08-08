package com.diturrizaga.easypay.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.diturrizaga.easypay.model.response.Transaction

object NavigationTo {
   fun goTo(activity: Class<*>, context: Context, id: String) {
      val intent = Intent(context,activity)
      intent.putExtra("userId", id)
      context.startActivity(intent)
   }

   fun goTo(activity: Class<*>, context: Context,currentUserId: String, transaction : Transaction, balance: Double) {
      val intent = Intent(context, activity)
      intent.putExtra("userId",currentUserId)
      intent.putExtra("transaction", transaction)
      /*intent.putExtra("from_account",transaction.from_account)
      intent.putExtra("to_account",transaction.to_account)
      intent.putExtra("amount",transaction.amount)*/
      intent.putExtra("balance",balance)
      context.startActivity(intent)
   }

   fun goTo(activity: Class<*>, context: Context) {
      val intent = Intent(context, activity)
      context.startActivity(intent)
   }
}