package com.diturrizaga.easypay

interface OnGetItemsCallbackVM<T>{
   fun onSuccess(items: List<T>)
   fun onError()
}