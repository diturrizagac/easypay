package com.diturrizaga.easypay.ui.home

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
import com.diturrizaga.easypay.model.response.Account
import com.diturrizaga.easypay.repository.AccountRepository
import com.diturrizaga.easypay.ui.view.adapter.AccountAdapter
import com.diturrizaga.easypay.ui.AccountDetailActivity


class AccountListFragment : Fragment(){

   private lateinit var accountRecyclerView: RecyclerView
   private lateinit var layoutManager: RecyclerView.LayoutManager
   private var accountRepository = AccountRepository.getInstance()
   private val TAG = "AccountListFragment"
   private var userId: String? = null
   private var accountSelected : Account? = null
   private var accounts : List<Account>? = null
   private lateinit var accountRvAdapter : AccountAdapter

   private var adapterListener = object : View.OnClickListener{
      override fun onClick(view: View?) {
         val viewHolder = view!!.tag as RecyclerView.ViewHolder
         val position = viewHolder.adapterPosition
         accountSelected = accounts!![position]
         val intent = Intent(context, AccountDetailActivity::class.java)
         intent.putExtra("userId", userId)
         intent.putExtra("account",accountSelected)
         context!!.startActivity(intent)
      }
   }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      // Inflate the layout for this fragment
      val rootView = inflater.inflate(R.layout.fragment_account_list, container, false)
      setupRecycler(rootView)
      showAccounts()
      return rootView
   }

   private fun showAccounts() {
      accountRepository.getAccounts(
         userId!!,
         object : OnGetItemsCallback<Account> {
            override fun onSuccess(items: List<Account>) {
               accounts = items
               accountRvAdapter = AccountAdapter(items, context!!)
               setAdapter(accountRvAdapter)
               setListener()
            }

            override fun onError() {
               Log.v(TAG, "Couldn't bring data from URL")
            }
         }
      )
   }

   private fun setListener() {
      accountRvAdapter.setOnItemClickListener(adapterListener)
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
    * Initialize userId property of currentUserAux
    *
    */
   fun getIdFromHomeActivity(id: String) {
      userId = id
   }

}
