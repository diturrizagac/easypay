package com.diturrizaga.easypay.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
   private const val BASE_URL = "https://api.backendless.com/"
   const val APP_ID = "5D1A38AE-4A0A-EEDD-FFF6-92324609B900"
   const val API_KEY = "6BF9B897-D297-92E7-FF3C-381FEFEEC600"


   private fun getRetrofit() : Retrofit {
      return Retrofit.Builder()
         .baseUrl(BASE_URL)
         .addConverterFactory(GsonConverterFactory.create())
         .build()
   }

   fun getRestProvider() : RestProvider {
      return getRetrofit().create<RestProvider>(RestProvider::class.java)
   }
}