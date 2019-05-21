package com.diturrizaga.easypay.repository

import com.diturrizaga.easypay.api.Api.getRestProvider
import com.diturrizaga.easypay.api.RestProvider

class UserRepository : Repository{
   private var restProvider : RestProvider? = null

   constructor(restProvider: RestProvider){
      this.restProvider = restProvider
   }

   companion object {
      var repository: UserRepository? = null
      fun getInstance(): UserRepository {
         if (repository == null) {
            repository = UserRepository(getRestProvider())
         }
         return repository as UserRepository
      }
   }
}