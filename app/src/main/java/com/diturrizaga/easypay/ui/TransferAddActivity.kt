package com.diturrizaga.easypay.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.model.response.Transaction
import com.diturrizaga.easypay.repository.TransactionRepository

class TransferAddActivity : AppCompatActivity() {

   private var transactionRepository : TransactionRepository? = null

   var lista : List<Transaction>? = null
   var filtro : String? = null
   var filteredList : List<Transaction>? = null

   companion object{
      fun getTransferAddActivity(context: Context) = Intent(context, TransferAddActivity::class.java)
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_transfer_add)
   }



   fun filter(){
      //filteredList = filterBy(lista!!,filtro!!)
   }
}


