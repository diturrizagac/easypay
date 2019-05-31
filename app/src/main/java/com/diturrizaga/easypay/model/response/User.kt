package com.diturrizaga.easypay.model.response


class User {
   var objectId: String? = null
   var nickname: String? = null
   var last_name: String? = null
   var first_name: String? = null
   var email: String? = null
   var account: List<AccountResponse>? = null
}