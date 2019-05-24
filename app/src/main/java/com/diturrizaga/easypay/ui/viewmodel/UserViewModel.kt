package com.diturrizaga.easypay.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diturrizaga.easypay.OnGetItemsCallback
import com.diturrizaga.easypay.model.response.UserResponse
import com.diturrizaga.easypay.repository.UserRepository

class UserViewModel : ViewModel() {
   private var userList: MutableLiveData<List<UserResponse>>? = null
   private var repository = UserRepository.getInstance()

   fun getUsers(): LiveData<List<UserResponse>>{
      if (userList == null) {
         userList = MutableLiveData()
         loadUsers()
      }
      return userList as MutableLiveData<List<UserResponse>>
   }

   fun loadUsers() {
      repository.getUsers(object : OnGetItemsCallback<UserResponse>{
         override fun onSuccess(items: List<UserResponse>) {

         }

         override fun onError() {
            Log.v("ERROR", "tan mal pe :v")
         }

      })
   }
}