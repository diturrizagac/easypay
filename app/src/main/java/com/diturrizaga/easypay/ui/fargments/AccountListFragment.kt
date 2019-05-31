package com.diturrizaga.easypay.ui.fargments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diturrizaga.easypay.OnGetItemsCallback
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.model.response.AccountResponse
import com.diturrizaga.easypay.repository.AccountRepository
import com.diturrizaga.easypay.ui.AccountDetailActivity
import com.diturrizaga.easypay.ui.adapter.AccountAdapter
import java.lang.RuntimeException

class AccountListFragment : Fragment() {

   private lateinit var accountRecyclerView: RecyclerView
   private lateinit var layoutManager: RecyclerView.LayoutManager
   private var accountRepository = AccountRepository.getInstance()
   private val TAG = "AccountListFragment"
   private var userId: String? = null
   private var accounts : List<AccountResponse>? = null
   var userListener : OnCurrentUserListener? = null


   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      // Inflate the layout for this fragment
      val rootView = inflater.inflate(R.layout.fragment_account_list, container, false)
      setupRecycler(rootView)
      showAccounts()
      //setListener()
      return rootView
   }

   private fun setListener() {

   }

   private fun showAccounts() {
      accountRepository.getAccounts(
         userId!!,
         object : OnGetItemsCallback<AccountResponse> {
            override fun onSuccess(items: List<AccountResponse>) {
               accounts = items
               setAdapter(AccountAdapter(items, context!!))
               sendUserIdToDetails()
            }

            override fun onError() {
               Log.v(TAG, "Couldn't bring data from URL")
            }
         })
   }

   /**
    * Initializing recyclerView and layout manager
    */
   private fun setupRecycler(view: View) {
      layoutManager = LinearLayoutManager(context) as RecyclerView.LayoutManager
      accountRecyclerView = view.findViewById(R.id.account_list)
      accountRecyclerView.layoutManager = layoutManager
   }

   /**
    * Set recyclerView adapter with AccountAdapterClass to draw on recyclerView
    */
   fun setAdapter(adapter: AccountAdapter) {
      accountRecyclerView.adapter = adapter
   }

   /**
    * Initialize userId property of currentUser
    *
    */
   fun getIdFromActivity(id: String) {
      userId = id
   }

   fun sendUserIdToDetails() {
      if (userListener != null) {
         userListener!!.getCurrentUserId(userId!!)
      } else {
         throw RuntimeException("NULL LISTENER")
      }
   }

   interface OnCurrentUserListener {
      fun getCurrentUserId(id : String)
   }
}
