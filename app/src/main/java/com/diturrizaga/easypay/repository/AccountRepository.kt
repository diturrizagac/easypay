package com.diturrizaga.easypay.repository

import com.diturrizaga.easypay.OnGetItemCallback
import com.diturrizaga.easypay.api.Api
import com.diturrizaga.easypay.api.RestProvider
import com.diturrizaga.easypay.model.response.UserResponse

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
   fun getUsers(callback: OnGetItemCallback<UserResponse>){
      val allUsers = Api.getRestProvider().getUsers()
   }

   fun getUser(callback: OnGetItemCallback<UserResponse>, id : String){
      val user = Api.getRestProvider().getUser(id)
   }

   fun updateUser(callback: OnGetItemCallback<UserResponse>,id : String){
      val user = Api.getRestProvider().putUser(id)
   }

}