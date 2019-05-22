package com.diturrizaga.easypay

interface OnGetItemsCallback<T> {
   fun onSuccess(items: List<T>)
   fun onError()
}