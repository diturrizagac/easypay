package com.diturrizaga.easypay.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.api.Api

class LogInActivity : AppCompatActivity() {

   private var usernameEditText : EditText? = null
   private var passwordEditText : EditText? = null
   private var enterButton: Button? = null
   private var username : String? = null
   private var password : String? = null
   private var userId : String? = null

   var logInActivityListener : LogInActivityListener? = null

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_log_in)
      Backendless.initApp(this, Api.BL_KEY, Api.API_KEY)
      initializeUI()
      setListener()
      //retrieveUsers()
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
               val id = response!!.properties.getValue("objectId")
               getId(id.toString())
               Toast.makeText(applicationContext,"User has been logged in", Toast.LENGTH_LONG).show()
               val intent = Intent(this@LogInActivity,HomeActivity::class.java)
               intent.putExtra("id",id.toString())
               this@LogInActivity.startActivity(intent)
            }

         }
      )
   }


   private fun initializeUI(){
      usernameEditText = findViewById(R.id.nicknameEt)
      passwordEditText = findViewById(R.id.passwordEt)
      enterButton = findViewById(R.id.enterButton)
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
      this.userId = id
      if (logInActivityListener != null){
         logInActivityListener!!.sendUser(userId!!)
      }
   }

   interface LogInActivityListener{
      fun sendUser(user_id : String)
   }
}
