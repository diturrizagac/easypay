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
import com.diturrizaga.easypay.api.Api.API_KEY
import com.diturrizaga.easypay.api.Api.BL_KEY

class LogInActivity : AppCompatActivity() {

   private var usernameEditText : EditText? = null
   private var passwordEditText : EditText? = null
   private var enterButton: Button? = null
   private var username : String? = null
   private var password : String? = null
   private var userId : String? = null
   private val TAG = "LogInActivity"

   var logInActivityListener : LogInActivityListener? = null

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_log_in)
      Backendless.initApp(this, BL_KEY, API_KEY)
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
               val id = response!!.properties.getValue("objectId").toString()
               //Log.v(TAG,response.properties.)
               getId(id)
               Toast.makeText(applicationContext,"User has been logged in", Toast.LENGTH_LONG).show()
               val intent = Intent(this@LogInActivity,HomeActivity::class.java)
               intent.putExtra("id",id)
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
      userId = id
      //logInActivityListener!!.sendUser(userId!!)
   }

   interface LogInActivityListener{
      fun sendUser(user_id : String)
   }
}
