package com.diturrizaga.easypay.ui.view.viewmodel

import androidx.lifecycle.ViewModel
import com.diturrizaga.easypay.model.response.Account

class AccountDetailViewModel : ViewModel() {
   var account : Account? = null
   var userId : String? = null
   var accountId : String? = null
   
}