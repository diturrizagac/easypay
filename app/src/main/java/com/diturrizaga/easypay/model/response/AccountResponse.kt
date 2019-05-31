package com.diturrizaga.easypay.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class AccountResponse : Serializable {
   @SerializedName("owner")
   @Expose
   var owner : String? = null
   @SerializedName("balance")
   @Expose
   var balance : Double? = null
   @SerializedName("account_name")
   @Expose
   var account_name : String? = null
   @SerializedName("created")
   @Expose
   var created : Long? = null
   @SerializedName("updated")
   @Expose
   var updated : Long? = null
   @SerializedName("status")
   @Expose
   var status : String? = null
   @SerializedName("objectId")
   @Expose
   var objectId : String? = null
   @SerializedName("token")
   @Expose
   var token : Int? = null
   @SerializedName("ownerId")
   @Expose
   var ownerId : String? = null
   @SerializedName("type")
   @Expose
   var type : String? = null
   @SerializedName("interested_earned")
   @Expose
   var interested_earned : String? = null
   @SerializedName("___class")
   @Expose
   var ___class : String? = null
   @SerializedName("transaction")
   @Expose
   var transaction : List<TransactionResponse>? = null
}