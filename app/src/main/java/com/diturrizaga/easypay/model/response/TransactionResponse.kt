package com.diturrizaga.easypay.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TransactionResponse :Serializable {

   @SerializedName("created")
   @Expose
   val created: Int? = null
   @SerializedName("from_account")
   @Expose
   val from_account: String? = null
   @SerializedName("activity_date")
   @Expose
   val activity_date: Int? = null
   @SerializedName("updated")
   @Expose
   val updated: Int? = null
   @SerializedName("status")
   @Expose
   val status: String? = null
   @SerializedName("amount")
   @Expose
   val amount: Double? = null
   @SerializedName("objectId")
   @Expose
   val objectId: String? = null
   @SerializedName("to_account")
   @Expose
   val to_account: String? = null
   @SerializedName("type")
   @Expose
   val type: String? = null
   @SerializedName("ownerId")
   @Expose
   val ownerId: String? = null
   @SerializedName("___class")
   @Expose
   val ___class: String? = null
}