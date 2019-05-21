package com.diturrizaga.easypay.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.ui.fargments.AccountListFragment
import com.diturrizaga.easypay.ui.fargments.AddTransactionFragment
import com.diturrizaga.easypay.ui.fargments.RecentListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {


   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_home)
      loadFragment(AccountListFragment())
      val navigationView : BottomNavigationView = findViewById(R.id.bottom_navigation)
      navigationView.setOnNavigationItemSelectedListener(this)
      //initializeUI()
   }

   override fun onNavigationItemSelected(item: MenuItem): Boolean {
      lateinit var fragment: Fragment
      when (item.itemId) {
         R.id.navigation_home -> fragment = AccountListFragment()
         R.id.navigation_add -> fragment = AddTransactionFragment()
         R.id.navigation_recent -> fragment =  RecentListFragment()
      }
      return loadFragment(fragment)
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


   /*private fun initializeUI() {
      main_viewpager.adapter = HomePagerAdapter(supportFragmentManager)
      main_viewpager.offscreenPageLimit = 3
   }*/
}
