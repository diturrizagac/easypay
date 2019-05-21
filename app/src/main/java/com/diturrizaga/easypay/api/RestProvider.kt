package com.diturrizaga.easypay.api

import com.diturrizaga.easypay.model.response.AccountResponse
import com.diturrizaga.easypay.model.response.TransactionResponse
import com.diturrizaga.easypay.model.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface RestProvider {

   /**
    * USER, ACCOUNT AND TRANSACTIONS CRUD
    */

   /**
    * USER
    */
   @GET("data/user")
   fun getUsers(): Call<AccountResponse>

   @GET("data/user/{id_user}")
   fun getUser(
      @Query("objectId") objectId: String
   ) : Call<UserResponse>

   @PUT("data/user/{id_user}")
   fun putUser(
      @Query("objectId") objectId: String
   ) : Call<UserResponse>

   /**
    * ACCOUNT
     */
   @GET("data/user/{id_user}")
   fun getUserAccounts(
      @Query("loadRelations") loadRelations: String
      //account
   ) : Call<AccountResponse>



   @GET("data/user/{id_user}")
   fun getUserTransactions(
      @Query("loadRelations") loadRelations: String
      //account.transaction
   ) : Call<TransactionResponse>

   /**
   * UPDATE TRANSACTIONS
   * */




   @PUT("data/user/{id_user}")
   fun putUserAccounts(
      @Query("loadRelations") loadRelations: String
      //account
   ) : Call<AccountResponse>

   @PUT("data/user/{id_user}")
   fun putUserTransactions(
      @Query("loadRelations") loadRelations: String
      //account.transaction
   ) : Call<TransactionResponse>


   /**
    * CREATE TRANSACTIONS
    */
   @POST("data/user/{id_user}")
   fun postUserAccounts(
      @Query("loadRelations") loadRelations: String
      //account
   ) : Call<AccountResponse>

   @POST("data/user/{id_user}")
   fun postUserTransactions(
      @Query("loadRelations") loadRelations: String
      //account.transaction
   ) : Call<TransactionResponse>
}