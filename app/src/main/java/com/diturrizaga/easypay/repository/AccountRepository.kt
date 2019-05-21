package com.diturrizaga.easypay.repository

import com.diturrizaga.easypay.api.Api
import com.diturrizaga.easypay.api.RestProvider

class AccountRepository : Repository {
   private var restProvider : RestProvider? = null

   constructor(restProvider: RestProvider){
      this.restProvider = restProvider
   }

   companion object {
      var repository: AccountRepository? = null
      fun getInstance(): AccountRepository {
         if (repository == null) {
            repository = AccountRepository(Api.getRestProvider())
         }
         return repository as AccountRepository
      }
   }

   
}