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
   private lateinit var userId : String
   private val accountListFragment = AccountListFragment()
   private val addTransactionFragment = AddTransactionFragment()
   private val recentListFragment = RecentListFragment()


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
         R.id.navigation_recent -> fragment = recentListFragment
      }
      return loadFragment(fragment,userId)
   }

   private fun loadFragment(fragment:Fragment,id:String) : Boolean {
      if (fragment != null) {
         supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
         if (fragment is AccountListFragment) {
            accountListFragment.getIdFromHomeActivity(id)
         } else {
            if (fragment is AddTransactionFragment) {
               addTransactionFragment.getIdFromHomeActivity(id)
            } else {
               recentListFragment.getIdFromHomeActivity(id)
            }
         }
         return true
      }
      return false
   }

   private fun loadFragment(fragment:Fragment):Boolean {
      if (fragment != null) {
         supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
         return true
      }
      return false
   }


   /**
    * Info retrieved from LoginActivity by Intent
    */
   private fun retrieveData(){
      userId = intent.extras!!.getString("userId") as String
      accountListFragment.getIdFromHomeActivity(userId)
      //sendIdToFragment(userId)
   }

   /**
    * Method to send Id of current user from HomeActivity to Fragment
    */
   @Deprecated("unnecesary method, it's been deprecated")
   private fun sendIdToFragment(id:String){
      accountListFragment.getIdFromHomeActivity(id)
      addTransactionFragment.getIdFromHomeActivity(id)
      recentListFragment.getIdFromHomeActivity(id)
   }

   override fun onSupportNavigateUp(): Boolean {
      finish()
      return true
   }

}