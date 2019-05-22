package com.diturrizaga.easypay.model

import com.diturrizaga.easypay.model.response.UserResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BackendlessResponse : Serializable {
   @SerializedName("")
   @Expose
   val list: List<UserResponse>? = null
}