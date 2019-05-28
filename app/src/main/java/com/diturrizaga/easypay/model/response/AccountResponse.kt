package com.diturrizaga.easypay.model.response

import com.diturrizaga.easypay.model.Transaction
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class AccountResponse : Serializable {
   @SerializedName("owner")
   @Expose
   val owner : String? = null
   @SerializedName("balance")
   @Expose
   val balance : Double? = null
   @SerializedName("account_name")
   @Expose
   val account_name : String? = null
   @SerializedName("created")
   @Expose
   val created : Long? = null
   @SerializedName("updated")
   @Expose
   val updated : Long? = null
   @SerializedName("status")
   @Expose
   val status : String? = null
   @SerializedName("objectId")
   @Expose
   val objectId : String? = null
   @SerializedName("token")
   @Expose
   val token : Int? = null
   @SerializedName("ownerId")
   @Expose
   val ownerId : String? = null
   @SerializedName("type")
   @Expose
   val type : String? = null
   @SerializedName("interested_earned")
   @Expose
   val interested_earned : String? = null
   @SerializedName("___class")
   @Expose
   val ___class : String? = null
   @SerializedName("transaction")
   @Expose
   val transaction : List<TransactionResponse>? = null
}