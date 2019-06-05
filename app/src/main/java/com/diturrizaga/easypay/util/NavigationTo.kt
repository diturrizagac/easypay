package com.diturrizaga.easypay.util

import android.content.Context
import android.content.Intent

object NavigationTo {
   fun goTo(activity: Class<*>, context: Context, id: String) {
      val intent = Intent(context,activity)
      intent.putExtra("userId", id)
      context.startActivity(intent)
   }
}