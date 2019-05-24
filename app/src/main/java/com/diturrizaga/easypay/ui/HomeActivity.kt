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

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, LogInActivity.LogInActivityListener{

   private val TAG = "HomeActivity"
   private lateinit var id : String
   var listener : OnIdSendListener? = null


   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_home)

      retrieveData()
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





   private fun retrieveData(){
      id = intent.extras!!.getSerializable("id") as String
      if (listener != null) {
         listener!!.sendId(id)
         Log.v(TAG, "listener prueba")
      } else {
         Log.v("LISTENER", "  NO ETNTRO AL LISTENER")
      }

   }

   override fun sendUser(user_id: String) {
      id = user_id
   }


   interface OnIdSendListener {
      fun sendId(id:String)
   }

}
