package com.diturrizaga.easypay.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.diturrizaga.easypay.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import com.google.android.material.snackbar.*
import kotlinx.android.synthetic.main.activity_withdrawal_scan.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

class WithdrawalScanQrActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

   private var qrCodeScanner : ZXingScannerView? = null
   private var barcodeBackImageView : ImageView? = null
   private var flashOnOff : ImageView? = null

   companion object {
      private const val HUAWEI = "huawei"
      private const val MY_CAMERA_REQUEST_CODE = 6515
      fun getWithdrawalScanQrActivity(context: Context) = Intent(context, WithdrawalScanQrActivity::class.java)
   }


   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      requestWindowFeature(Window.FEATURE_NO_TITLE)
      window.setFlags(
         WindowManager.LayoutParams.FLAG_FULLSCREEN,
         WindowManager.LayoutParams.FLAG_FULLSCREEN)
      setContentView(R.layout.activity_withdrawal_scan)
      initializeUI()
      setScannerProperties()
      barcodeBackImageView!!.setOnClickListener { onBackPressed() }
      flashOnOff!!.setOnClickListener {
         if (qrCodeScanner!!.flash) {
            qrCodeScanner!!.flash = false
            flashOnOff!!.background = ContextCompat.getDrawable(this, R.drawable.flash_off_vector_icon)
         } else {
            qrCodeScanner!!.flash = true
            flashOnOff!!.background = ContextCompat.getDrawable(this, R.drawable.flash_on_vector_icon)
         }
      }

   }

   private fun initializeUI() {
      barcodeBackImageView = findViewById(R.id.barcodeBackImageView)
      flashOnOff = findViewById(R.id.flashOnOffImageView)
      qrCodeScanner = findViewById(R.id.qrCodeScanner)
   }

   private fun setScannerProperties() {
      qrCodeScanner!!.setFormats(listOf(BarcodeFormat.QR_CODE))
      qrCodeScanner!!.setAutoFocus(true)
      qrCodeScanner!!.setLaserColor(R.color.colorAccent)
      qrCodeScanner!!.setMaskColor(R.color.colorAccent)
      //if (Build.MANUFACTURER.equals(HUAWEI, ignoreCase = true))
      //   qrCodeScanner!!.setAspectTolerance(0.5f)
   }

   override fun onResume() {
      super.onResume()
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
         if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),
               MY_CAMERA_REQUEST_CODE)
            return
         }
      }
      qrCodeScanner!!.startCamera()
      qrCodeScanner!!.setResultHandler(this)
   }

   override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults)
      if (requestCode == MY_CAMERA_REQUEST_CODE) {
         if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            openCamera()
         else if (grantResults[0] == PackageManager.PERMISSION_DENIED)
            showCameraSnackBar()
      }
   }

   private fun showCameraSnackBar() {
      if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
         val snackbar = Snackbar.make(scanQrCodeRootView, resources.getString(R.string.app_needs_your_camera_permission_in_order_to_scan_qr_code), Snackbar.LENGTH_LONG)
         val view1 = snackbar.view
         view1.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
         val textView = view1.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
         //val textView = view1.findViewById<TextView>(android.support.design.R.id.snackbar_text)
         textView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
         snackbar.show()
      }
   }

   private fun openCamera() {
      qrCodeScanner!!.startCamera()
      qrCodeScanner!!.setResultHandler(this)
   }

   override fun handleResult(p0: Result?) {
      if (p0 != null) {
         startActivity(WithdrawalScannedActivity.getScannedActivity(this, p0.text))
         resumeCamera()
      }
   }

   private fun resumeCamera() {
      Toast.LENGTH_LONG
      val handler = Handler()
      handler.postDelayed({ qrCodeScanner!!.resumeCameraPreview(this@WithdrawalScanQrActivity) }, 2000)
   }

}
