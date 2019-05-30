package com.diturrizaga.easypay.ui.fargments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.ui.WithdrawalScanQrActivity
import com.diturrizaga.easypay.ui.WithdrawalSelectAmountActivity

class AddTransactionFragment : Fragment() {

   private var transactionCardlessCash : CardView? = null

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {

      val rootView = inflater.inflate(R.layout.fragment_add_transaction, container, false)
      initializeUI(rootView)
      setListener()
      // Inflate the layout for this fragment
      return rootView
   }

   private fun initializeUI(rootView : View) {
      transactionCardlessCash = rootView.findViewById(R.id.transaction_cardlesscash)
   }

   private fun setListener() {
      transactionCardlessCash!!.setOnClickListener {
         startActivity(WithdrawalSelectAmountActivity.getWithdrawalSelectAmountActivity(activity!!))
      }
   }
}
