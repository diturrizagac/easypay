package com.diturrizaga.easypay.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.diturrizaga.easypay.model.response.AccountResponse

class AccountDetailViewModel : ViewModel() {
   var account : AccountResponse? = null
   var userId : String? = null
   var accountId : String? = null
   
}