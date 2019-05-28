package com.diturrizaga.easypay.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.ui.fargments.AccountListFragment
import com.diturrizaga.easypay.ui.fargments.AddTransactionFragment
import com.diturrizaga.easypay.ui.fargments.RecentListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener{

   private val TAG = "HomeActivity"
   private lateinit var id : String
   private val accountListFragment = AccountListFragment()
   private val addTransactionFragment = AddTransactionFragment()
   private val recentListFragment = RecentListFragment()
   private val FRAGTMENT_TAG = "used"

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_home)
      loadFragment(accountListFragment)
      retrieveData()
      val navigationView : BottomNavigationView = findViewById(R.id.bottom_navigation)
      navigationView.setOnNavigationItemSelectedListener(this)
   }

   override fun onNavigationItemSelected(item: MenuItem): Boolean {
      lateinit var fragment: Fragment
      when (item.itemId) {
         R.id.navigation_home -> fragment = accountListFragment
         R.id.navigation_add -> fragment = addTransactionFragment
         R.id.navigation_recent -> fragment =  recentListFragment
      }
      return loadFragment(fragment)
   }

   private fun loadFragment(fragment:Fragment):Boolean {
      if (fragment != null) {

         supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment,FRAGTMENT_TAG)
            .commit()
         return true
      }
      return false
   }

   private fun retrieveData(){
      id = intent.extras!!.getSerializable("id") as String
      sendIdToFragment(id)
   }

   private fun sendIdToFragment(id:String){
      accountListFragment.getIdFromActivity(id)
   }

}