package com.diturrizaga.easypay.ui.fargments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.diturrizaga.easypay.R

class AccountListFragment : Fragment() {

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      // Inflate the layout for this fragment
      return inflater.inflate(R.layout.fragment_account_list, container, false)
   }
}
