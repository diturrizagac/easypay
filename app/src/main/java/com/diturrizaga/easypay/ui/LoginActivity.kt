package com.diturrizaga.easypay.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.api.Api.API_KEY
import com.diturrizaga.easypay.api.Api.APP_ID
import com.diturrizaga.easypay.model.response.User
import com.diturrizaga.easypay.model.response.UserResponse
import com.diturrizaga.easypay.ui.viewmodel.LogInViewModel

class LoginActivity : AppCompatActivity() {

   private var usernameEditText : EditText? = null
   private var passwordEditText : EditText? = null
   private var enterButton: Button? = null
   private var username : String? = null
   private var password : String? = null
   private var userId : String? = null
   private val TAG = "LoginActivity"

   private var viewModel : LogInViewModel? = null


   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_login)
      Backendless.initApp(this, APP_ID, API_KEY)
      viewModel = ViewModelProviders.of(this).get(LogInViewModel::class.java)
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
               //val userId = response.properties.getValue("objectId").toString()
               //getId(userId)
               parseToUser(response!!)
               getId(response.properties.getValue("objectId").toString())
               Toast.makeText(applicationContext,"User has been logged in", Toast.LENGTH_LONG).show()
               val intent = Intent(this@LoginActivity,HomeActivity::class.java)
               intent.putExtra("userId",userId)
               this@LoginActivity.startActivity(intent)
            }
         }
      )
   }


   private fun parseToUser(backendlessUser : BackendlessUser) {
      val mUser = User()
      mUser.objectId = backendlessUser.properties!!.getValue("objectId").toString()
      mUser.last_name = backendlessUser.properties!!.getValue("last_name").toString()
      mUser.nickname = backendlessUser.properties!!.getValue("nickname").toString()
      mUser.first_name = backendlessUser.properties!!.getValue("first_name").toString()
      mUser.email = backendlessUser.properties!!.getValue("email").toString()
      mUser.objectId = backendlessUser.properties!!.getValue("objectId").toString()
      viewModel!!.currentUser = mUser
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
