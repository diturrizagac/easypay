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
   private var recentTransferButton : CardView? = null
   private var recentPaymentButton : CardView? = null
   private var recentWithdrawalButton : CardView? = null

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      // Inflate the layout for this fragment
      val rootView = inflater.inflate(R.layout.fragment_recent_list, container, false)
      initializeUI(rootView)
      setListener()
      return rootView
   }

   fun initializeUI(view: View) {
      recentTransferButton = view.findViewById(R.id.transfer_recent)
      recentPaymentButton = view.findViewById(R.id.payment_recent)
      recentWithdrawalButton = view.findViewById(R.id.withdrawal_recent)
   }

   fun getIdFromHomeActivity(id: String) {
      userId = id
   }

   private fun setListener() {
      recentTransferButton!!.setOnClickListener {
         NavigationTo.goTo(TransferRecentActivity::class.java, context!!, userId!!)
      }

      recentPaymentButton!!.setOnClickListener {
         NavigationTo.goTo(PaymentRecentActivity::class.java, context!!, userId!!)
      }

      recentWithdrawalButton!!.setOnClickListener {
         NavigationTo.goTo(WithdrawalRecentActivity::class.java, context!!, userId!!)
      }
   }
}
