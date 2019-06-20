package com.diturrizaga.easypay.ui

import com.diturrizaga.easypay.R
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog

import android.os.Bundle
import android.content.Intent
import android.text.TextUtils
import android.annotation.SuppressLint
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*

import java.util.concurrent.TimeUnit
import com.shuhart.stepview.StepView
import com.chaos.view.PinView


class AuthenticationActivity : AppCompatActivity() {

   /*private var currentStep = 0
   lateinit var layout1 : LinearLayout
   lateinit var layout2 : LinearLayout
   lateinit var layout3 : LinearLayout
   lateinit var stepView : StepView
   lateinit var dialog_verifying : AlertDialog
   lateinit var profile_dialog : AlertDialog

   private val uniqueIdentifier: String? = null
   private val UNIQUE_ID = "UNIQUE_ID"
   private val ONE_HOUR_MILLI = (60 * 60 * 1000).toLong()

   private val TAG = "FirebasePhoneNumAuth"

   private var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
   private var firebaseAuth: FirebaseAuth? = null

   private val phoneNumber: String? = null
   private var sendCodeButton: Button? = null
   private var verifyCodeButton: Button? = null
   private val signOutButton: Button? = null
   private var button3: Button? = null

   private var phoneNum: EditText? = null
   private var verifyCodeET: PinView? = null
   private var phonenumberText: TextView? = null

   private var mVerificationId: String? = null
   private var mResendToken: PhoneAuthProvider.ForceResendingToken? = null


   private var mAuth: FirebaseAuth? = null*/

   @SuppressLint("SetTextI18n")
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_authentication)

     /* mAuth = FirebaseAuth.getInstance()

      layout1 = findViewById(R.id.layout1) as LinearLayout
      layout2 = findViewById(R.id.layout2) as LinearLayout
      layout3 = findViewById(R.id.layout3) as LinearLayout
      sendCodeButton = findViewById(R.id.submit1) as Button
      verifyCodeButton = findViewById(R.id.submit2) as Button
      button3 = findViewById(R.id.submit3) as Button
      firebaseAuth = FirebaseAuth.getInstance()
      phoneNum = findViewById(R.id.phonenumber) as EditText
      verifyCodeET = findViewById(R.id.pinView) as PinView
      phonenumberText = findViewById(R.id.phonenumberText) as TextView


      stepView = findViewById(R.id.step_view)
      stepView.setStepsNumber(3)
      stepView.go(0, true)
      layout1.visibility = View.VISIBLE

      sendCodeButton!!.setOnClickListener(object : View.OnClickListener() {
         fun onClick(view: View) {

            val phoneNumber = phoneNum!!.text.toString()
            phonenumberText!!.text = phoneNumber

            if (TextUtils.isEmpty(phoneNumber)) {
               phoneNum!!.error = "Enter a Phone Number"
               phoneNum!!.requestFocus()
            } else if (phoneNumber.length < 10) {
               phoneNum!!.error = "Please enter a valid phone"
               phoneNum!!.requestFocus()
            } else {

               if (currentStep < stepView.stepCount - 1) {
                  currentStep++
                  stepView.go(currentStep, true)
               } else {
                  stepView.done(true)
               }
               layout1.visibility = View.GONE
               layout2.setVisibility(View.VISIBLE)
               PhoneAuthProvider.getInstance().verifyPhoneNumber(
                  phoneNumber, // Phone number to verify
                  60, // Timeout duration
                  TimeUnit.SECONDS, // Unit of timeout
                  this@AuthenticationActivity, // Activity (for callback binding)
                  mCallbacks
               )        // OnVerificationStateChangedCallbacks
            }
         }
      })

      mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
         fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
            val inflater = layoutInflater
            val alertLayout = inflater.inflate(R.layout.processing_dialog, null)
            val show = AlertDialog.Builder(this@AuthenticationActivity)

            show.setView(alertLayout)
            show.setCancelable(false)
            dialog_verifying = show.create()
            dialog_verifying.show()
            signInWithPhoneAuthCredential(phoneAuthCredential)
         }

         fun onVerificationFailed(e: FirebaseException) {

         }

         fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
         ) {

            // Save verification ID and resending token so we can use them later
            mVerificationId = verificationId
            mResendToken = token

            // ...
         }
      }

      verifyCodeButton!!.setOnClickListener(object : View.OnClickListener() {
         fun onClick(view: View) {

            val verificationCode = verifyCodeET!!.text!!.toString()
            if (verificationCode.isEmpty()) {
               Toast.makeText(this@AuthenticationActivity, "Enter verification code", Toast.LENGTH_SHORT).show()
            } else {

               val inflater = layoutInflater
               val alertLayout = inflater.inflate(R.layout.processing_dialog, null)
               val show = AlertDialog.Builder(this@AuthenticationActivity)

               show.setView(alertLayout)
               show.setCancelable(false)
               dialog_verifying = show.create()
               dialog_verifying.show()

               val credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode)
               signInWithPhoneAuthCredential(credential)

            }
         }
      })

      button3!!.setOnClickListener(object : View.OnClickListener() {
         fun onClick(view: View) {

            if (currentStep < stepView.stepCount - 1) {
               currentStep++
               stepView.go(currentStep, true)
            } else {
               stepView.done(true)
            }
            val inflater = layoutInflater
            val alertLayout = inflater.inflate(R.layout.profile_create_dialog, null)
            val show = AlertDialog.Builder(this@AuthenticationActivity)
            show.setView(alertLayout)
            show.setCancelable(false)
            profile_dialog = show.create()
            profile_dialog.show()
            val handler = Handler()
            handler.postDelayed(Runnable {
               profile_dialog.dismiss()
               startActivity(Intent(this@AuthenticationActivity, ProfileActivity::class.java))
               finish()
            }, 3000)
         }
      })

   }

   private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
      mAuth!!.signInWithCredential(credential)
         .addOnCompleteListener(this, object : OnCompleteListener<AuthResult>() {
            fun onComplete(task: Task<AuthResult>) {
               if (task.isSuccessful()) {
                  // Sign in success, update UI with the signed-in user's information
                  Log.d(TAG, "signInWithCredential:success")
                  dialog_verifying.dismiss()
                  if (currentStep < stepView.stepCount - 1) {
                     currentStep++
                     stepView.go(currentStep, true)
                  } else {
                     stepView.done(true)
                  }
                  layout1.visibility = View.GONE
                  layout2.setVisibility(View.GONE)
                  layout3.setVisibility(View.VISIBLE)
                  // ...
               } else {

                  dialog_verifying.dismiss()
                  Toast.makeText(this@AuthenticationActivity, "Something wrong", Toast.LENGTH_SHORT).show()
                  Log.w(TAG, "signInWithCredential:failure", task.getException())
                  if (task.getException() is FirebaseAuthInvalidCredentialsException) {

                  }
               }
            }
         })*/
   }
}
