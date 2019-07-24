package com.diturrizaga.easypay.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.ui.payment.PaymentRecentActivity
import com.diturrizaga.easypay.ui.transfer.TransferRecentActivity
import com.diturrizaga.easypay.ui.withdrawal.WithdrawalRecentActivity
import com.diturrizaga.easypay.util.NavigationTo

class RecentListFragment : Fragment() {

   private var userId: String? = null
   private var addTransfer : CardView? = null
   private var addPayment : CardView? = null
   private var addWithdrawal : CardView? = null

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

   }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      // Inflate the layout for this fragment
      return inflater.inflate(R.layout.fragment_recent_list, container, false)
   }

   fun getIdFromHomeActivity(id: String) {
      userId = id
   }

   private fun setListener() {
      addTransfer!!.setOnClickListener {
         NavigationTo.goTo(TransferRecentActivity::class.java, context!!, userId!!)
      }

      addPayment!!.setOnClickListener {
         NavigationTo.goTo(PaymentRecentActivity::class.java, context!!, userId!!)
      }

      addWithdrawal!!.setOnClickListener {
         NavigationTo.goTo(WithdrawalRecentActivity::class.java, context!!, userId!!)
      }
   }


}
