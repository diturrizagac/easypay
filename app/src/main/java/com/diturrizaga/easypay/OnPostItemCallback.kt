package com.diturrizaga.easypay

interface OnPostItemCallback<T> {
   fun onSuccess(item: T)
   fun onError()
}