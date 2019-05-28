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
import com.diturrizaga.easypay.qrUtil.EncryptionHelper
import com.diturrizaga.easypay.qrUtil.QRCodeHelper
import com.google.gson.Gson
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel

class WithdrawalGenerateQrActivity : AppCompatActivity() {

   var button : Button? = null
   var nameEditText : AppCompatEditText? = null
   var ageEditText : AppCompatEditText? = null
   var qrImageView : ImageView? = null

   companion object {
      fun getGenerateQrCodeActivity(context : Context) = Intent(context,WithdrawalGenerateQrActivity::class.java)
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_withdrawal_generate_qr)
      initializeUI()
      setListener()
   }

   private fun initializeUI() {
      button = findViewById(R.id.generateQrCodeButton)
      nameEditText = findViewById(R.id.fullNameEditText)
      ageEditText = findViewById(R.id.ageEditText)
      qrImageView = findViewById(R.id.qrCodeImageView)

   }

   private fun setListener() {
      button!!.setOnClickListener {
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
      if (TextUtils.isEmpty(nameEditText!!.text.toString())) {
         Toast.makeText(this, "fullName field cannot be empty!", Toast.LENGTH_SHORT).show()
         return false
      } else if (TextUtils.isEmpty(ageEditText!!.text.toString())) {
         Toast.makeText(this, "age field cannot be empty!", Toast.LENGTH_SHORT).show()
         return false
      }
      return true
   }
}
