package com.diturrizaga.easypay.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Transaction :Serializable {

   @SerializedName("created")
   @Expose
   var created: Long? = null
   @SerializedName("from_account")
   @Expose
   var from_account: String? = null
   @SerializedName("activity_date")
   @Expose
   var activity_date: Long? = null
   @SerializedName("updated")
   @Expose
   var updated: Long? = null
   @SerializedName("status")
   @Expose
   var status: String? = null
   @SerializedName("amount")
   @Expose
   var amount: Double? = null
   @SerializedName("objectId")
   @Expose
   var objectId: String? = null
   @SerializedName("to_account")
   @Expose
   var to_account: String? = null
   @SerializedName("type")
   @Expose
   var type: String? = null
   @SerializedName("ownerId")
   @Expose
   var ownerId: String? = null
   @SerializedName("___class")
   @Expose
   var ___class: String? = null
}