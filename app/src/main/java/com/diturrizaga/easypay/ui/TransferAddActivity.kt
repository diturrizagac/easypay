package com.diturrizaga.easypay.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.model.response.Transaction
import com.diturrizaga.easypay.util.TransactionFilter.Companion.filterBy

class TransferAddActivity : AppCompatActivity() {

   var lista : List<Transaction>? = null
   var filtro : String? = null
   var filteredList : List<Transaction>? = null

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_transfer_add)
   }

   fun filter(){
      //filteredList = filterBy(lista!!,filtro!!)
   }
}


