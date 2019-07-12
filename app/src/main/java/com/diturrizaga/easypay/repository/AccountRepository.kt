package com.diturrizaga.easypay.repository

import android.util.Log
import com.diturrizaga.easypay.OnGetItemsCallback
import com.diturrizaga.easypay.api.Api
import com.diturrizaga.easypay.api.Api.API_KEY
import com.diturrizaga.easypay.api.Api.APP_ID
import com.diturrizaga.easypay.api.RestProvider
import com.diturrizaga.easypay.model.response.Account
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
   fun getAccounts(userId: String, callback: OnGetItemsCallback<Account>){
      val accounts = Api.getRestProvider().getUserAccounts(APP_ID,API_KEY,userId, "accounts")
      Log.i(TAG, "GET---> ${accounts.request().url()}")
      //val accounts = Api.getRestProvider().getUserAccounts(APP_ID,API_KEY,"078D80EE-362A-8E2D-FF59-BF4620DA8B00", "account")
      requestAccounts(accounts,callback)
   }

   fun getAllAccounts(callback: OnGetItemsCallback<Account>) {
      val accounts = Api.getRestProvider().getAllAccounts(APP_ID, API_KEY)
      Log.i(TAG,"GET---> ${accounts.request().url()}")
      requestAllAccounts(accounts,callback)
   }

   private fun requestAccounts(call: Call<UserResponse>, callback: OnGetItemsCallback<Account>) {
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
                     callback.onSuccess(userResponse.accounts!!)
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

   private fun requestAllAccounts(call: Call<List<Account>>, callback: OnGetItemsCallback<Account>) {
      call.enqueue(
         object : Callback<List<Account>> {
            override fun onFailure(call: Call<List<Account>>, t: Throwable) {
               callback.onError()
               Log.v(TAG,t.toString())
            }

            override fun onResponse(call: Call<List<Account>>, response: Response<List<Account>>) {
               if (response.isSuccessful) {
                  val accountsResponse = response.body()
                  if (accountsResponse != null) {
                     callback.onSuccess(accountsResponse)
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


   fun getAccount(callback: OnGetItemsCallback<Account>, id : String){
      val user = Api.getRestProvider().getUser(id)
   }

   fun updateAccount(callback: OnGetItemsCallback<Account>, id : String){
      val user = Api.getRestProvider().updateUser(id)
   }
}