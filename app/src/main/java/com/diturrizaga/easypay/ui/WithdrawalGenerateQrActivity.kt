package com.diturrizaga.easypay.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import com.diturrizaga.easypay.R
import com.diturrizaga.easypay.model.response.Account
import com.diturrizaga.easypay.model.response.Transaction
import com.diturrizaga.easypay.qrUtil.EncryptionHelper
import com.diturrizaga.easypay.qrUtil.QRCodeHelper
import com.google.gson.Gson
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel

class WithdrawalGenerateQrActivity : AppCompatActivity() {

   var generateQrButton : Button? = null
   var nameEditText : AppCompatEditText? = null
   var ageEditText : AppCompatEditText? = null
   var qrImageView : ImageView? = null
   var account : Account? = null
   var transaction : Transaction? = null

   companion object {
      fun getWithdrawalGenerateQrActivity(context: Context) = Intent(context, WithdrawalGenerateQrActivity::class.java)
      fun getWithdrawalGenerateQrActivity(context: Context, account: Account): Intent =
         Intent(context, WithdrawalGenerateQrActivity::class.java).putExtra("account", account)

      fun getWithdrawalGenerateQrActivity(
         context: Context,
         account: Account,
         transaction: Transaction
      ): Intent =
         Intent(context, WithdrawalGenerateQrActivity::class.java)
            .putExtra("account", account)
            .putExtra("transaction", transaction)
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_withdrawal_generate_qr)
      initializeUI()
      retrieveData()
      setListener()

      //initializeTestUI()
      //setListenerTest()
   }

   private fun retrieveData() {
      account = intent.extras!!.getSerializable("account") as Account
      transaction = intent.extras!!.getSerializable("transaction") as Transaction
   }


   private fun initializeUI() {
      generateQrButton = findViewById(R.id.generateQrCodeButton)
      qrImageView = findViewById(R.id.qrCodeImageView)
   }

   private fun setListener() {
      generateQrButton!!.setOnClickListener {
         if (checkEditText()) {
            hideKeyboard()
            /**
             * fill transaction object
             */
            val transaction = Transaction()


            val serializeString = Gson().toJson(transaction)
            val encryptedString = EncryptionHelper.getInstance().encryptionString(serializeString).encryptMsg()
            setImageBitmap(encryptedString)
         }
      }
   }

   private fun setImageBitmap(encryptedString: String?) {
      val bitmap = QRCodeHelper.newInstance(this).setContent(encryptedString).setErrorCorrectionLevel(
         ErrorCorrectionLevel.Q).setMargin(2).qrcOde
      qrImageView!!.setImageBitmap(bitmap)
   }

   private fun hideKeyboard() {
      val view = this.currentFocus
      if (view != null) {
         val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
         imm.hideSoftInputFromWindow(view.windowToken, 0)
      }
   }

   private fun checkEditText(): Boolean {

      /**
       * implement checkEditTextHere
       */
      return true
   }


   private fun checkTestEditText(): Boolean {
      if (TextUtils.isEmpty(nameEditText!!.text.toString())) {
         Toast.makeText(this, "fullName field cannot be empty!", Toast.LENGTH_SHORT).show()
         return false
      } else if (TextUtils.isEmpty(ageEditText!!.text.toString())) {
         Toast.makeText(this, "age field cannot be empty!", Toast.LENGTH_SHORT).show()
         return false
      }
      return true
   }


   private fun initializeTestUI() {
      nameEditText = findViewById(R.id.fullNameEditText)
      ageEditText = findViewById(R.id.ageEditText)
   }

   private fun setListenerTest() {
      generateQrButton!!.setOnClickListener {
         if (checkEditText()) {
            hideKeyboard()
            val user = UserObject(
               fullName = nameEditText!!.text.toString(),
               age = Integer.parseInt(ageEditText!!.text.toString()))
            val serializeString = Gson().toJson(user)
            val encryptedString = EncryptionHelper.getInstance().encryptionString(serializeString).encryptMsg()
            setImageBitmap(encryptedString)
         }
      }
   }




}
