package com.diturrizaga.easypay.util

import com.diturrizaga.easypay.model.response.TransactionResponse

class TransactionFilter {
   companion object {
      fun filterBy(items : List<TransactionResponse>, filter : String ) : List<TransactionResponse>{
         val item = items.iterator()
         val filtered = ArrayList<TransactionResponse>()
         
         while(item.hasNext()){
            val property = item.next()
            if (property.type == filter) {
               filtered.add(property)
            }
         }
         return filtered
      }
   }
}