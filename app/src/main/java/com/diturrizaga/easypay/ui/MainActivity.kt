package com.diturrizaga.easypay.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.diturrizaga.easypay.OnGetItemsCallback
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.model.response.UserResponse
import com.diturrizaga.easypay.repository.UserRepository

class MainActivity : AppCompatActivity() {

    private var userRepository = UserRepository.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        retrieveUsers()
    }

    private fun retrieveUsers() {
        userRepository.getUsers(object : OnGetItemsCallback<UserResponse>{
            override fun onSuccess(items: List<UserResponse>) {

            }

            override fun onError() {
                Log.v("ERROR", "tan mal pe :v")
            }

        })
    }
}
