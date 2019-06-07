package com.diturrizaga.easypay.repository

import android.util.Log
import com.diturrizaga.easypay.OnGetItemsCallback
import com.diturrizaga.easypay.OnPostItemCallback
import com.diturrizaga.easypay.api.Api
import com.diturrizaga.easypay.api.Api.API_KEY
import com.diturrizaga.easypay.api.Api.APP_ID
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
      val transactions = Api.getRestProvider().getAccountTransactions(APP_ID,API_KEY,id,"transaction")
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

   fun createTransaction(transaction: TransactionResponse, callback : OnPostItemCallback<TransactionResponse>) {
      val transaction = Api.getRestProvider().createUserTransaction(transaction)
      Log.i("POST--->", transaction.request().url().toString())
      requestTransaction(transaction, callback)
   }

   fun createTransaction(transaction: TransactionResponse) {
      val transaction = Api.getRestProvider().createUserTransaction(transaction)
      Log.i("POST--->", transaction.request().url().toString())
      requestTransaction(transaction)
   }

   private fun requestTransaction(call:Call<TransactionResponse>) {
      call.enqueue(
         object : Callback<TransactionResponse> {
            override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
               Log.e(TAG, " -------> Unable to submit post to API.")
            }

            override fun onResponse(call: Call<TransactionResponse>, response: Response<TransactionResponse>) {
               if (response.isSuccessful) {
                  Log.i(TAG, "post submitted to API." + response.body().toString());
               }
            }
         }
      )
   }

   private fun requestTransaction(call:Call<TransactionResponse>, callback: OnPostItemCallback<TransactionResponse>) {
      call.enqueue(
         object : Callback<TransactionResponse> {
            override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
               callback.onError()
               Log.e(TAG,t.toString())
               Log.e(TAG, " -------> Unable to submit post to API.")
            }

            override fun onResponse(call: Call<TransactionResponse>, response: Response<TransactionResponse>) {
               if (response.isSuccessful) {
                  val transactionResponse = response.body()
                  if (transactionResponse != null) {
                     callback.onSuccess(transactionResponse)
                  } else {
                     callback.onError()
                     Log.i(TAG, "post submitted to API." + response.body().toString())
                  }
               } else {
                  callback.onError()
                  Log.i(TAG, "post submitted to API." + response.body().toString())
               }
            }
         }
      )
   }

   private fun requestTransactions(call: Call<AccountResponse>, callback: OnGetItemsCallback<TransactionResponse>){
      call.enqueue(
         object : Callback<AccountResponse> {
            override fun onFailure(call: Call<AccountResponse>, t: Throwable) {
               callback.onError()
               Log.e(TAG,t.toString())
            }

            override fun onResponse(call: Call<AccountResponse>, response: Response<AccountResponse>) {
               if (response.isSuccessful) {
                  val accountResponse = response.body()
                  if (accountResponse != null) {
                     callback.onSuccess(accountResponse.transactions!!)
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