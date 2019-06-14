package com.diturrizaga.easypay.repository

import android.util.Log
import com.diturrizaga.easypay.OnGetItemsCallback
import com.diturrizaga.easypay.OnPostItemCallback
import com.diturrizaga.easypay.api.Api
import com.diturrizaga.easypay.api.Api.API_KEY
import com.diturrizaga.easypay.api.Api.APP_ID
import com.diturrizaga.easypay.api.RestProvider
import com.diturrizaga.easypay.model.response.Account
import com.diturrizaga.easypay.model.response.Transaction
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

   fun getTransactions(id: String, callback: OnGetItemsCallback<Transaction>) {
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

   fun createTransaction(transaction: Transaction, callback : OnPostItemCallback<Transaction>) {
      val currentTransaction = Api.getRestProvider().createUserTransaction(APP_ID, API_KEY,transaction)
      Log.i("POST--->", currentTransaction.request().url().toString())
      requestTransaction(currentTransaction, callback)
   }

   fun createTransaction(transaction: Transaction) {
      val currentTransaction = Api.getRestProvider().createUserTransaction(APP_ID, API_KEY,transaction)
      Log.i("POST--->", currentTransaction.request().url().toString())
      requestTransaction(currentTransaction)
   }

   private fun requestTransaction(call:Call<Transaction>) {
      call.enqueue(
         object : Callback<Transaction> {
            override fun onFailure(call: Call<Transaction>, t: Throwable) {
               Log.e(TAG, " -------> Unable to submit post to API.")
            }

            override fun onResponse(call: Call<Transaction>, response: Response<Transaction>) {
               if (response.isSuccessful) {
                  Log.i(TAG, "post submitted to API." + response.body().toString());
               }
            }
         }
      )
   }

   private fun requestTransaction(call:Call<Transaction>, callback: OnPostItemCallback<Transaction>) {
      call.enqueue(
         object : Callback<Transaction> {
            override fun onFailure(call: Call<Transaction>, t: Throwable) {
               callback.onError()
               Log.e(TAG,t.toString())
               Log.e(TAG, " -------> Unable to submit post to API.")
            }

            override fun onResponse(call: Call<Transaction>, response: Response<Transaction>) {
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

   private fun requestTransactions(call: Call<Account>, callback: OnGetItemsCallback<Transaction>){
      call.enqueue(
         object : Callback<Account> {
            override fun onFailure(call: Call<Account>, t: Throwable) {
               callback.onError()
               Log.e(TAG,t.toString())
            }

            override fun onResponse(call: Call<Account>, response: Response<Account>) {
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