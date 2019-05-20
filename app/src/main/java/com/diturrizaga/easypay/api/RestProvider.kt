package com.diturrizaga.easypay.api

import com.diturrizaga.easypay.model.response.AccountResponse
import retrofit2.Call
import retrofit2.http.GET

interface RestProvider {
   @GET("/data/user/")
   fun getUsers(): Call<AccountResponse>



}