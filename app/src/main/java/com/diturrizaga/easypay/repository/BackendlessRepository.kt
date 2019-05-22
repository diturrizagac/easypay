package com.diturrizaga.easypay.repository

import com.diturrizaga.easypay.api.Api
import com.diturrizaga.easypay.api.RestProvider

class BackendlessRepository : Repository {
   private var restProvider : RestProvider? = null
   private val TAG = "BackendlessRepository"

   constructor(restProvider: RestProvider) {
      this.restProvider = restProvider
   }

   companion object {
      private var repository : BackendlessRepository? = null
      fun getInstance(): BackendlessRepository {
         if (repository == null) {
            repository = BackendlessRepository(Api.getRestProvider())
         }
         return repository as BackendlessRepository
      }
   }


}