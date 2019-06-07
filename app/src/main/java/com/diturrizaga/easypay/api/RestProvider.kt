package com.diturrizaga.easypay.api

import com.diturrizaga.easypay.model.response.AccountResponse
import com.diturrizaga.easypay.model.response.TransactionResponse
import com.diturrizaga.easypay.model.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface RestProvider {


   /**
    * USER, ACCOUNT AND TRANSACTIONS CRUD
    */

   @GET("{bl_key}/{api_key}/data/Users")
   fun getUsers(
      @Path("bl_key") bl_key : String,
      @Path("api_key") api_key : String
   ): Call<List<UserResponse>>

   /**
    * USER
    */

   @GET("data/user/{id_user}")
   fun getUser(
      @Query("objectId") objectId: String
   ) : Call<List<UserResponse>>

   @PUT("data/user/{id_user}")
   fun updateUser(
      @Query("objectId") objectId: String
   ) : Call<List<UserResponse>>

   /**
    * ACCOUNT
     */
   //@GET("{bl_key}/{api_key}/data/user/{id_user}")
   @GET("{bl_key}/{api_key}/data/Users/{id_user}")
   fun getUserAccounts(
      @Path("bl_key") bl_key : String,
      @Path("api_key") api_key : String,
      @Path("id_user") id_user : String,
      @Query("loadRelations") loadRelations: String
      //account
   ) : Call<UserResponse>

   @PUT("data/user/{id_user}")
   fun updateUserAccounts(
      @Query("loadRelations") loadRelations: String
      //account
   ) : Call<AccountResponse>

   @POST("data/user/{id_user}")
   fun createUserAccounts(
      @Query("loadRelations") loadRelations: String
      //account
   ) : Call<AccountResponse>

   /**
    * TRANSACTIONS
     */

   @GET("{bl_key}/{api_key}/data/account/{id_account}")
   fun getAccountTransactions(
      @Path("bl_key") bl_key : String,
      @Path("api_key") api_key : String,
      @Path("id_account") id_user : String,
      @Query("loadRelations") loadRelations: String
      //account.transaction
   ) : Call<AccountResponse>

   @PUT("data/user/{id_user}")
   fun updateUserTransaction(
      @Query("loadRelations") loadRelations: String
      //account.transaction
   ) : Call<TransactionResponse>

   //@POST("data/user/{id_user}")
   @POST("/data/transaction")
   fun createUserTransaction(
      @Body transaction : TransactionResponse
   ) : Call<TransactionResponse>

   @DELETE("")
   fun cancelUserTransaction(
      @Query("loadRelations   ") loadRelations : String
   ) : Call<TransactionResponse>
}