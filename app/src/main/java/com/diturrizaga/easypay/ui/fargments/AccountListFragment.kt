package com.diturrizaga.easypay.ui.fargments

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
import com.diturrizaga.easypay.ui.LogInActivity
import com.diturrizaga.easypay.ui.MainActivity
import com.diturrizaga.easypay.ui.adapter.AccountAdapter

class AccountListFragment : Fragment(), LogInActivity.OnGetCurrentUser {

   private lateinit var accountRecyclerView : RecyclerView
   private var accountRepository = AccountRepository.getInstance()
   private var layoutManager = LinearLayoutManager(context)
   private var id : String? = ""

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
      accountRepository.getAccounts(object : OnGetItemsCallback<AccountResponse> {
         override fun onSuccess(items: List<AccountResponse>) {
            setAdapter(AccountAdapter(items, context!!))
         }

         override fun onError() {
            Log.v("ERROR", "tan mal pe :v")
         }
      },id!!)
   }

   private fun setupRecycler(view : View) {
      accountRecyclerView = view.findViewById(R.id.account_list)
      accountRecyclerView.layoutManager = layoutManager
   }

   fun setAdapter(adapter : AccountAdapter) {
      accountRecyclerView.adapter = adapter
   }

   override fun getCurrentUser(id: String) {
      this.id = id
   }

}
