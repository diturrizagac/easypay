package com.diturrizaga.easypay.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserResponse {
   @SerializedName("created")
   @Expose
   var created: Long? = null
   @SerializedName("id")
   @Expose
   var id: Int? = null
   @SerializedName("nickname")
   @Expose
   var nickname: String? = null
   @SerializedName("updated")
   @Expose
   var updated: Long? = null
   @SerializedName("objectId")
   @Expose
   var objectId: String? = null
   @SerializedName("last_name")
   @Expose
   var last_name: String? = null
   @SerializedName("first_name")
   @Expose
   var first_name: String? = null
   @SerializedName("ownerId")
   @Expose
   var ownerId: String? = null
   @SerializedName("___class")
   @Expose
   var ___class: String? = null
   @SerializedName("account")
   @Expose
   var account: List<AccountResponse>? = null
}