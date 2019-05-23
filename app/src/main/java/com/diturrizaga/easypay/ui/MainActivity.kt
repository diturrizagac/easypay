package com.diturrizaga.easypay.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.diturrizaga.easypay.OnGetItemsCallback
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.api.Api.API_KEY
import com.diturrizaga.easypay.api.Api.BL_KEY
import com.diturrizaga.easypay.model.response.UserResponse
import com.diturrizaga.easypay.repository.UserRepository
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {


    var signInButton : MaterialButton? = null
    var signUpButton : MaterialButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpUI()
        setListener()
    }

    fun setUpUI(){
        signInButton = findViewById(R.id.button_sign_in)
        signUpButton = findViewById(R.id.button_sign_up)

    }

    fun setListener(){
        signInButton!!.setOnClickListener {
            val intent = Intent( this,LogInActivity::class.java)
            this.startActivity(intent)
        }
    }

}


