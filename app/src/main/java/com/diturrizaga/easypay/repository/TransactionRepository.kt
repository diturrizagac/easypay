package com.diturrizaga.easypay.repository

import android.util.Log
import com.diturrizaga.easypay.OnGetItemsCallback
import com.diturrizaga.easypay.OnPostItemCallback
import com.diturrizaga.easypay.OnPutItemCallback
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

   fun getAccountTransactions(id: String, callback: OnGetItemsCallback<Transaction>) {
      val transactions = Api.getRestProvider().getAccountTransactions(APP_ID,API_KEY,id,"transaction")
      Log.i("GET--->", transactions.request().url().toString())
      requestTransactions(transactions,callback)
   }

   fun getTransactions(callback: OnGetItemsCallback<Transaction>) {
      val transactions = Api.getRestProvider().getTransactions(APP_ID, API_KEY,100)
      Log.i("GET--->", transactions.request().url().toString())
      requestTransaction(transactions, callback)
   }

   fun updateUserAccount(id: String, account: Account, callback: OnPutItemCallback<Account>) {
      val account = Api.getRestProvider().updateUserAccount(APP_ID, API_KEY,id, account)
      Log.i("PUT--->", account.request().url().toString())
      requestUpdateAccount(account, callback)
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

   fun cancelTransaction() {
      val transaction = Api.getRestProvider().cancelUserTransaction("transaction")
      Log.i("DELETE--->", transaction.request().url().toString())
   }

   private fun requestUpdateAccount(call: Call<Account>, callback: OnPutItemCallback<Account>) {
      call.enqueue(
         object : Callback<Account> {
            override fun onFailure(call: Call<Account>, t: Throwable) {
               callback.onError()
               Log.e(TAG,t.toString())
               Log.e(TAG, " -------> Unable to submit post to API.")
            }

            override fun onResponse(call: Call<Account>, response: Response<Account>) {
               if (response.isSuccessful) {
                  val accountResponse = response.body()
                  if (accountResponse != null) {
                     callback.onSuccess(accountResponse)
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

   private fun requestTransaction(call: Call<List<Transaction>>, callback: OnGetItemsCallback<Transaction>) {
      call.enqueue(
         object : Callback<List<Transaction>> {
            override fun onFailure(call: Call<List<Transaction>>, t: Throwable) {
               callback.onError()
               Log.e(TAG,t.toString())
            }

            override fun onResponse(call: Call<List<Transaction>>, response: Response<List<Transaction>>) {
               if (response.isSuccessful) {
                  val listTransactionResponse = response.body()
                  if (listTransactionResponse != null) {
                     callback.onSuccess(listTransactionResponse)
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

}