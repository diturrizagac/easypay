package com.diturrizaga.easypay.repository

import android.util.Log
import com.diturrizaga.easypay.OnGetItemsCallback
import com.diturrizaga.easypay.api.Api
import com.diturrizaga.easypay.api.Api.API_KEY
import com.diturrizaga.easypay.api.Api.APP_ID
import com.diturrizaga.easypay.api.RestProvider
import com.diturrizaga.easypay.model.response.AccountResponse
import com.diturrizaga.easypay.model.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountRepository : Repository {
   private var restProvider : RestProvider? = null
   private val TAG = "AccountRepository"

   constructor(restProvider: RestProvider) {
      this.restProvider = restProvider
   }

   companion object {
      private var repository: AccountRepository? = null
      fun getInstance(): AccountRepository {
         if (repository == null) {
            repository = AccountRepository(Api.getRestProvider())
         }
         return repository as AccountRepository
      }
   }
   fun getAccounts(id: String, callback: OnGetItemsCallback<AccountResponse>){
      val users = Api.getRestProvider().getUserAccounts(APP_ID,API_KEY,id, "account")
      Log.i("GET--->", users.request().url().toString())
      //val accounts = Api.getRestProvider().getUserAccounts(APP_ID,API_KEY,"078D80EE-362A-8E2D-FF59-BF4620DA8B00", "account")
      requestAccounts(users,callback)
   }

   fun getAccount(callback: OnGetItemsCallback<AccountResponse>, id : String){
      val user = Api.getRestProvider().getUser(id)
   }

   fun updateAccount(callback: OnGetItemsCallback<AccountResponse>, id : String){
      val user = Api.getRestProvider().updateUser(id)
   }



   private fun requestAccounts(call: Call<UserResponse>, callback: OnGetItemsCallback<AccountResponse>){
      call.enqueue(
         object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
               callback.onError()
               Log.v(TAG,t.toString())
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
               if (response.isSuccessful) {
                  val userResponse = response.body()
                  if (userResponse != null) {
                     callback.onSuccess(userResponse.account!!)
                  } else {
                     callback.onError()
                  }
               } else {
                  callback.onError()
               }
            }
         }
      )
   }
}