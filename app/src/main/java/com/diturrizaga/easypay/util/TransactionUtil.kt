package com.diturrizaga.easypay.util

import com.diturrizaga.easypay.model.response.Account
import com.diturrizaga.easypay.model.response.Transaction

class TransactionUtil {
   companion object {
      fun filterTransactionsByUser(items : List<Transaction>, filterCriteria : String ) : List<Transaction>{
         val item = items.iterator()
         val filteredList = ArrayList<Transaction>()
         
         while(item.hasNext()){
            val transaction = item.next()
            if (transaction.ownerId.equals(filterCriteria,true)) {
            //if (transaction.type == filterCriteria) {
               filteredList.add(transaction)
            }
         }
         return filteredList
      }

      fun filterTransactionsByType(items : List<Transaction>, filterCriteria : String ) : List<Transaction>{
         val item = items.iterator()
         val filteredList = ArrayList<Transaction>()

         while(item.hasNext()){
            val transaction = item.next()
            if (transaction.type.equals(filterCriteria,true)) {
               //if (transaction.type == filterCriteria) {
               filteredList.add(transaction)
            }
         }
         return filteredList
      }

      fun filterAccounts(items : List<Account>, filterCriteria : String, type : String ) : List<Account>{
         val item = items.iterator()
         val filteredList = ArrayList<Account>()

         while(item.hasNext()){
            val account = item.next()
            if (type.equals(filterCriteria,true)) {
               filteredList.add(account)
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