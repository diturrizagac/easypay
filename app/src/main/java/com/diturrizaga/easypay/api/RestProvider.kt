package com.diturrizaga.easypay.api

import com.diturrizaga.easypay.model.response.Account
import com.diturrizaga.easypay.model.response.Transaction
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

   @GET("data/User/{id_user}")
   fun getUser(
      @Query("objectId") objectId: String
   ) : Call<List<UserResponse>>

   @PUT("data/User/{id_user}")
   fun updateUser(
      @Query("objectId") objectId: String
   ) : Call<List<UserResponse>>

   /**
    * ACCOUNT
     */

   @GET("{bl_key}/{api_key}/data/Account")
   fun getAllAccounts(
      @Path("bl_key") bl_key : String,
      @Path("api_key") api_key : String
   ) : Call<List<Account>>

   //@GET("{bl_key}/{api_key}/data/user/{id_user}")
   @GET("{bl_key}/{api_key}/data/Users/{id_user}")
   fun getUserAccounts(
      @Path("bl_key") bl_key : String,
      @Path("api_key") api_key : String,
      @Path("id_user") id_user : String,
      @Query("loadRelations") loadRelations: String
      //account
   ) : Call<UserResponse>

   @GET("{bl_key}/{api_key}/Account/{objectId}")
   fun getAccount(
      @Path("bl_key") bl_key : String,
      @Path("api_key") api_key : String,
      @Path("objectId") objectId: String
   ) : Call<Account>

   @PUT("data/User/{id_user}")
   fun updateUserAccounts(
      @Query("loadRelations") loadRelations: String
      //account
   ) : Call<Account>

   @POST("data/User/{id_user}")
   fun createUserAccounts(
      @Query("loadRelations") loadRelations: String
      //account
   ) : Call<Account>

   /**
    * TRANSACTIONS
     */

   @GET("{bl_key}/{api_key}/data/Account/{id_account}")
   fun getAccountTransactions(
      @Path("bl_key") bl_key : String,
      @Path("api_key") api_key : String,
      @Path("id_account") id_user : String,
      @Query("loadRelations") loadRelations: String
      //account.transaction
   ) : Call<Account>

   @GET("{bl_key}/{api_key}/data/Transaction")
   fun getTransactions(
      @Path("bl_key") bl_key : String,
      @Path("api_key") api_key : String,
      @Query("pageSize") size: Int
   ) : Call<List<Transaction>>

   @PUT("data/User/{id_user}")
   fun updateUserTransaction(
      @Query("loadRelations") loadRelations: String
      //account.transaction
   ) : Call<Transaction>

   //@POST("data/user/{id_user}")
   @POST("{bl_key}/{api_key}/data/Transaction")
   fun createUserTransaction(
      @Path("bl_key") bl_key : String,
      @Path("api_key") api_key : String,
      @Body transaction : Transaction
   ) : Call<Transaction>

   @DELETE("")
   fun cancelUserTransaction(
      @Query("loadRelations   ") loadRelations : String
   ) : Call<Transaction>
}