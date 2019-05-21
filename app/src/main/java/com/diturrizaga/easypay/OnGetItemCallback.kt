package com.diturrizaga.easypay

interface OnGetItemCallback<T> {
   fun onSuccess(items: List<T>)
   fun onError()
}