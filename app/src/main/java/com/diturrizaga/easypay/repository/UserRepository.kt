package com.diturrizaga.easypay.repository

import android.util.Log
import com.diturrizaga.easypay.OnGetItemsCallback
import com.diturrizaga.easypay.api.Api
import com.diturrizaga.easypay.api.Api.getRestProvider
import com.diturrizaga.easypay.api.RestProvider
import com.diturrizaga.easypay.model.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository : Repository {
   private var restProvider: RestProvider? = null
   private val TAG = "UserRepository"

   constructor(restProvider: RestProvider) {
      this.restProvider = restProvider
   }

   companion object {
      var repository: UserRepository? = null
      @JvmStatic
      fun getInstance(): UserRepository {
         if (repository == null) {
            repository = UserRepository(getRestProvider())
         }
         return repository as UserRepository
      }
   }

   fun getUser(callback: OnGetItemsCallback<UserResponse>) {

   }

   fun getUsers(callback: OnGetItemsCallback<UserResponse>) {
      val users = Api.getRestProvider().getUsers(Api.BL_KEY, Api.API_KEY)
      requestUsers(users, callback)

   }

   private fun requestUsers(call: Call<List<UserResponse>>, callback: OnGetItemsCallback<UserResponse>) {
      call.enqueue(
         object : Callback<List<UserResponse>> {
            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
               Log.v("ERROR $TAG", t.toString())
            }

            override fun onResponse(call: Call<List<UserResponse>>, response: Response<List<UserResponse>>) {
               if (response.isSuccessful) {
                  val userResponse = response.body()
                  if (userResponse != null) {
                     callback.onSuccess(userResponse)
                  }
               }
            }

         }
      )
   }
}