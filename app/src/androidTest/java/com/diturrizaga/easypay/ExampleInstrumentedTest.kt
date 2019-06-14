package com.diturrizaga.easypay

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.hamcrest.CoreMatchers.notNullValue

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

   private lateinit var mDevice : UiDevice

   companion object {
      private const val LAUNCH_TIMEOUT = 7000
      private const val STRING_TO_BE_TYPED = "Testing UI Automator"
      private const val UI_PACKAGE = "com.diturrizaga.easy.ui"
   }

   @Before
   fun startMainActivity() {

      // Initialize UiDevice instance

      mDevice = UiDevice.getInstance(getInstrumentation())

      // Start from the home screen
      mDevice.pressHome()

      // Wait for launcher
      val launcherPackage = getLauncherPackageName()
      assertThat(launcherPackage, notNullValue())
      mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT.toLong())

      // Launch the blueprint app
      val context : Context = getApplicationContext()
      val intent : Intent = context.packageManager.getLaunchIntentForPackage(UI_PACKAGE)!!
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)    // Clear out any previous instances
      context.startActivity(intent)

      // Wait for the app to appear
      mDevice.wait(Until.hasObject(By.pkg(UI_PACKAGE).depth(0)), LAUNCH_TIMEOUT.toLong())
   }


   @Test
   fun useAppContext() {
      // Context of the app under test.
      val appContext : Context = getApplicationContext()
      assertEquals("com.diturrizaga.easypay", appContext.packageName)
   }


   private fun getLauncherPackageName() : String {
      //Create launcher Intent
      val intent = Intent(Intent.ACTION_MAIN)
      intent.addCategory(Intent.CATEGORY_HOME)

      // Use PackageManager to get the launcher package name
      val packageManager = getApplicationContext<Context>().packageManager
      val resolveInfo = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
      return resolveInfo.activityInfo.packageName
   }
}
