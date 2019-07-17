package com.diturrizaga.easypay.util

import com.diturrizaga.easypay.model.response.Transaction

class TransactionUtil {
   companion object {
      fun filterBy(items : List<Transaction>, filter : String ) : List<Transaction>{
         val item = items.iterator()
         val filtered = ArrayList<Transaction>()
         
         while(item.hasNext()){
            val property = item.next()
            if (property.type == filter) {
               filtered.add(property)
            }
         }
         return filtered
      }

      fun isSum(fromAccount : String): Boolean {
         return true
      }

      fun updateAdittionAmount(total: Double, amount: Double): Double {
         return total + amount
      }

      fun updateSubtractionAmount(total: Double, amount: Double): Double {
         return total - amount
      }
   }
}