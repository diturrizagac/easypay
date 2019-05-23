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
import com.diturrizaga.easypay.OnGetItemsCallback
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.api.Api
import com.diturrizaga.easypay.model.response.UserResponse
import com.diturrizaga.easypay.repository.UserRepository

class LogInActivity : AppCompatActivity() {

   private var usernameEditText : EditText? = null
   private var passwordEditText : EditText? = null
   private var enterButton: Button? = null
   var getCurrentUserListener : OnGetCurrentUser? = null
   private var userRepository = UserRepository.getInstance()
   private var username : String? = null
   private var password : String? = null
   private var id : String? = null

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_log_in)
      Backendless.initApp(this, Api.BL_KEY, Api.API_KEY)
      setUpUI()
      setListener()
      //retrieveUsers()
   }

   private fun retrieveUsers() {
      userRepository.getUsers(object : OnGetItemsCallback<UserResponse> {
         override fun onSuccess(items: List<UserResponse>) {

         }

         override fun onError() {
            Log.v("ERROR", "tan mal pe :v")
         }

      })
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
               //getCurrentUserListener!!.getCurrentUser(id.toString())
               Toast.makeText(applicationContext,"User has been logged in", Toast.LENGTH_LONG).show()
               //intent = Intent(this@LogInActivity,HomeActivity::class.java)
               //startActivity(intent)
            }

         }
      )
   }

   fun setUpUI(){
      usernameEditText = findViewById(R.id.nicknameEt)
      passwordEditText = findViewById(R.id.passwordEt)
      enterButton = findViewById(R.id.enterButton)

   }

   fun setListener(){

      enterButton!!.setOnClickListener {
         setFields()
         setUpAccount()
         val intent = Intent( this,HomeActivity::class.java)
         intent.putExtra("id",id)
         this.startActivity(intent)
      }
   }

   fun setFields(){
      username = usernameEditText!!.text.toString()
      password = passwordEditText!!.text.toString()
   }

   fun getId(id : String){
      this.id = id
   }


   interface OnGetCurrentUser {
      fun getCurrentUser(id : String)
   }
}
