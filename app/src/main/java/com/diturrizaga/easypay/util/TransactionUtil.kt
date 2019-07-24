package com.diturrizaga.easypay.util

import com.diturrizaga.easypay.model.response.Transaction

class TransactionUtil {
   companion object {
      fun filterBy(items : List<Transaction>, filterCriteria : String ) : List<Transaction>{
         val item = items.iterator()
         val filteredList = ArrayList<Transaction>()
         
         while(item.hasNext()){
            val transaction = item.next()
            if (transaction.type == filterCriteria) {
               filteredList.add(transaction)
            }
         }
         return filteredList
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