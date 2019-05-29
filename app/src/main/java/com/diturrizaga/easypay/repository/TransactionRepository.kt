package com.diturrizaga.easypay.repository

import android.util.Log
import com.diturrizaga.easypay.OnGetItemsCallback
import com.diturrizaga.easypay.api.Api
import com.diturrizaga.easypay.api.Api.API_KEY
import com.diturrizaga.easypay.api.Api.BL_KEY
import com.diturrizaga.easypay.api.RestProvider
import com.diturrizaga.easypay.model.response.AccountResponse
import com.diturrizaga.easypay.model.response.TransactionResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionRepository : Repository {
   private var restProvider : RestProvider? = null
   private val TAG = "TransactionRepository"

   constructor(restProvider: RestProvider){
      this.restProvider = restProvider
   }

   companion object {
      var repository: TransactionRepository? = null
      fun getInstance(): TransactionRepository {
         if (repository == null) {
            repository = TransactionRepository(Api.getRestProvider())
         }
         return repository as TransactionRepository
      }
   }

   fun getTransactions(id: String, callback: OnGetItemsCallback<TransactionResponse>) {
      val transactions = Api.getRestProvider().getAccountTransactions(BL_KEY,API_KEY,id,"transaction")
      Log.i("GET--->", transactions.request().url().toString())
      requestTransactions(transactions,callback)
   }

   fun updateTransaction() {
      val transaction = Api.getRestProvider().updateUserTransaction("transaction")
      Log.i("PUT--->", transaction.request().url().toString())
   }

   fun cancelTransaction() {
      val transaction = Api.getRestProvider().cancelUserTransaction("transaction")
      Log.i("DELETE--->", transaction.request().url().toString())
   }

   private fun requestTransactions(call: Call<AccountResponse>, callback: OnGetItemsCallback<TransactionResponse>){
      call.enqueue(
         object : Callback<AccountResponse> {
            override fun onFailure(call: Call<AccountResponse>, t: Throwable) {
               callback.onError()
               Log.v(TAG,t.toString())
            }

            override fun onResponse(call: Call<AccountResponse>, response: Response<AccountResponse>) {
               if (response.isSuccessful) {
                  val accountResponse = response.body()
                  if (accountResponse != null) {
                     callback.onSuccess(accountResponse.transaction!!)
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