package com.diturrizaga.easypay

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until


import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class LoginTest {

   companion object {
      private const val LAUNCH_TIMEOUT = 5000L
      private const val UI_PACKAGE = "com.diturrizaga.easypay"
      private const val EMAIL = "diturrizagac@gmail.com"
      private const val PASS = "diego"
   }

   private var device : UiDevice? = null
   private val launcherPackageName: String
      get() {
         val intent = Intent(Intent.ACTION_MAIN)
         intent.addCategory(Intent.CATEGORY_HOME)
         val pm = getApplicationContext<Context>().packageManager
         val resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
         return resolveInfo.activityInfo.packageName
      }

   @Before
   fun startActivityFromHomeScreen() {
      val launcherPackage : String = launcherPackageName


      device = UiDevice.getInstance(getInstrumentation())
      device!!.pressHome()

      assertThat(launcherPackage, notNullValue())
      device!!.wait(
         Until.hasObject(By.pkg(launcherPackage).depth(0)),
         LAUNCH_TIMEOUT
      )
      val context : Context = getApplicationContext<Context>()
      val intent : Intent? = context.packageManager
         .getLaunchIntentForPackage(UI_PACKAGE)
      intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
      context.startActivity(intent)

      device!!.wait(
         Until.hasObject(By.pkg(UI_PACKAGE).depth(0)),
         LAUNCH_TIMEOUT
      )
   }


   @Test
   fun checkPreconditions() {
      assertThat<UiDevice>(device, notNullValue())
   }

   @Test
   fun validateLogin() {
      device!!.findObject(By.res(UI_PACKAGE,"email_et"))
         .text = EMAIL
      device!!.findObject(By.res(UI_PACKAGE, "password_et"))
         .text = PASS
      device!!.findObject(By.res(UI_PACKAGE,"button_sign_in"))
         .click()
   }

   @Deprecated("use startActivityFromHomeScreen method ")
   fun startActivityFromScreen() {
      device = UiDevice.getInstance(getInstrumentation())
      device!!.pressHome()

      //val launcherPackage = device!!.launcherPackageName
      val launcherPackage = launcherPackageName
      assertThat(launcherPackage,notNullValue())
      device!!.wait(
         Until.hasObject(By.pkg(launcherPackage).depth(0)),
         LAUNCH_TIMEOUT
      )

      val context  = getApplicationContext<Context>()
      val intent = context.packageManager
         .getLaunchIntentForPackage(UI_PACKAGE)

      intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)    // Clear out any previous instances
      context.startActivity(intent)

      device!!.wait(
         Until.hasObject(By.pkg(UI_PACKAGE).depth(0)),
         LAUNCH_TIMEOUT
      )
   }
}