package com.diturrizaga.easypay.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.diturrizaga.easypay.R

class RecentListFragment : Fragment() {

   private var userId: String? = null

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


}
