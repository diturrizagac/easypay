package com.diturrizaga.easypay.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.ui.payment.PaymentAddActivity
import com.diturrizaga.easypay.ui.transfer.TransferAddActivity
import com.diturrizaga.easypay.ui.withdrawal.WithdrawalSelectAmountActivity
import com.diturrizaga.easypay.util.NavigationTo.goTo

class AddTransactionFragment : Fragment() {

   private var userId: String? = null
   private var transactionTransfer : CardView? = null
   private var transactionPayment : CardView? = null
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
      transactionTransfer = rootView.findViewById(R.id.transaction_add_transfer)
      transactionPayment = rootView.findViewById(R.id.transaction_add_payment)
      transactionCardlessCash = rootView.findViewById(R.id.transaction_add_cardlesscash)
   }

   private fun setListener() {
      transactionCardlessCash!!.setOnClickListener {
         goTo(WithdrawalSelectAmountActivity::class.java,context!!,userId!!)
      }

      transactionTransfer!!.setOnClickListener {
         goTo(TransferAddActivity::class.java,context!!,userId!!)
      }

      transactionPayment!!.setOnClickListener {
         goTo(PaymentAddActivity::class.java,context!!,userId!!)
      }
   }

   fun getIdFromHomeActivity(id: String) {
      userId = id
   }


   @Deprecated("Use goTo method from NavigationTo object in util package")
   private fun goTo(activity: Class<*>) {
      val intent = Intent(context,activity)
      intent.putExtra("userId", userId)
      context!!.startActivity(intent)
   }

}
