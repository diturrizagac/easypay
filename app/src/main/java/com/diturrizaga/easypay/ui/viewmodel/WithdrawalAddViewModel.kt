package com.diturrizaga.easypay.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diturrizaga.easypay.OnGetItemsCallback
import com.diturrizaga.easypay.model.response.UserResponse
import com.diturrizaga.easypay.repository.UserRepository

class WithdrawalAddViewModel : ViewModel() {

   private var currentUser : UserResponse? = null

}