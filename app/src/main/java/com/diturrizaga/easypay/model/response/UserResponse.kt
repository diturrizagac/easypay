package com.diturrizaga.easypay.model.response

import com.diturrizaga.easypay.model.Account
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserResponse {
   @SerializedName("created")
   @Expose
   val created: Long? = null
   @SerializedName("id")
   @Expose
   val id: Int? = null
   @SerializedName("nickname")
   @Expose
   val nickname: String? = null
   @SerializedName("updated")
   @Expose
   val updated: Long? = null
   @SerializedName("objectId")
   @Expose
   val objectId: String? = null
   @SerializedName("last_name")
   @Expose
   val last_name: String? = null
   @SerializedName("first_name")
   @Expose
   val first_name: String? = null
   @SerializedName("ownerId")
   @Expose
   val ownerId: String? = null
   @SerializedName("___class")
   @Expose
   val ___class: String? = null
   @SerializedName("account")
   @Expose
   val account: List<AccountResponse>? = null
}