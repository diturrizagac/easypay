package com.diturrizaga.easypay.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.api.Api.API_KEY
import com.diturrizaga.easypay.api.Api.APP_ID
import com.diturrizaga.easypay.ui.viewmodel.LogInViewModel

class LogInActivity : AppCompatActivity() {

   private var usernameEditText : EditText? = null
   private var passwordEditText : EditText? = null
   private var enterButton: Button? = null
   private var username : String? = null
   private var password : String? = null
   private var userId : String? = null

   var viewModel : LogInViewModel? = null
   private val TAG = "LogInActivity"

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_log_in)
      Backendless.initApp(this, APP_ID, API_KEY)
      //viewModel = ViewModelProviders.of(this).get(LogInViewModel::class.java)
      initializeUI()
      setListener()
   }

   private fun setUpAccount() {
      Backendless.UserService.login(
         username,
         password,
         object : AsyncCallback<BackendlessUser> {
            override fun handleFault(fault: BackendlessFault?) {
               Toast.makeText(applicationContext,fault!!.message, Toast.LENGTH_LONG).show()
            }

            override fun handleResponse(response: BackendlessUser?) {
               //val currentUser = response!!.properties
               val userId = response!!.properties.getValue("objectId").toString()
               getId(userId)
               Toast.makeText(applicationContext,"User has been logged in", Toast.LENGTH_LONG).show()
               val intent = Intent(this@LogInActivity,HomeActivity::class.java)
               intent.putExtra("userId",userId)
               this@LogInActivity.startActivity(intent)
            }
         }
      )
   }


   private fun initializeUI(){
      usernameEditText = findViewById(R.id.login_nickname_et)
      passwordEditText = findViewById(R.id.login_password_et)
      enterButton = findViewById(R.id.login_continue_button)
   }

   private fun setListener(){
      enterButton!!.setOnClickListener {
         setFields()
         setUpAccount()
      }
   }

   private fun setFields(){
      username = usernameEditText!!.text.toString()
      password = passwordEditText!!.text.toString()
   }

   fun getId(id : String){
      userId = id
   }
}
