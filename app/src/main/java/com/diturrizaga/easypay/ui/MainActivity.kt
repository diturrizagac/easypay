package com.diturrizaga.easypay.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.ui.launcher.LoginActivity
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private var signInButton : MaterialButton? = null
    private var signUpButton : MaterialButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        initializeUI()
        setListener()
    }

    private fun initializeUI(){
        signInButton = findViewById(R.id.button_sign_in)
        signUpButton = findViewById(R.id.button_sign_up)
    }

    private fun setListener(){
        signInButton!!.setOnClickListener {
            val intent = Intent( this, LoginActivity::class.java)
            this.startActivity(intent)
        }
    }

}


