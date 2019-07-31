package com.diturrizaga.easypay

interface OnPutItemCallback<T> {
   fun onSuccess(item: T)
   fun onError()
}