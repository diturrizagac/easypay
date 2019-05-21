package com.diturrizaga.easypay.repository

import com.diturrizaga.easypay.api.Api
import com.diturrizaga.easypay.api.RestProvider

class TransactionRepository : Repository {
   private var restProvider : RestProvider? = null

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

   fun getUserAccounts(callback)

}