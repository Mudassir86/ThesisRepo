package com.example.mmudasirkhan.autocred

import android.content.Intent
import android.content.pm.PackageManager
import android.support.test.InstrumentationRegistry
import android.support.test.filters.LargeTest
import android.support.test.filters.SdkSuppress
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.*
import com.facebook.FacebookSdk.getApplicationContext
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

private const val BASIC_SAMPLE_PACKAGE = "com.airbnb.android"
private const val LAUNCH_TIMEOUT = 5000L
private const val STRING_TO_BE_TYPED = "UiAutomator"

@LargeTest
@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class AirbnbTest {


    private lateinit var mDevice: UiDevice
    val timeOut: Long = 1000 * 60

    @Before
    fun startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // Start from the home screen
        mDevice.pressHome()

        // Wait for launcher
        val launcherPackage = getLauncherPackageName()
        assertThat<String>(launcherPackage, notNullValue())
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT)

        // Launch the app
        val context = getApplicationContext()
        val intent = context.getPackageManager()
            .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)    // Clear out any previous instances
        context.startActivity(intent)

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)), LAUNCH_TIMEOUT)
    }


    private fun getLauncherPackageName(): String {
        // Create launcher Intent
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)

        // Use PackageManager to get the launcher package name
        val pm = getApplicationContext().packageManager
        val resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return resolveInfo.activityInfo.packageName
    }

    @Test
    fun SigninFB() {

        //Espresso.onView(ViewMatchers.withText("Continue with Facebook")).perform(ViewActions.click())
        //Thread.sleep(1000)

        mDevice.wait(Until.findObject(By.pkg("com.airbnb.android")), timeOut)
        val FbBtn: UiObject = mDevice.findObject(UiSelector().resourceId("com.airbnb.android:id/primary_sign_in_option_button"))
        FbBtn.clickAndWaitForNewWindow()

        val userEmail: UiObject = mDevice.findObject(UiSelector().text("Phone or Email"))
        userEmail.waitForExists(timeOut)
        userEmail.setText("autocred_glggbol_test@tfbnw.net@tfbnw.net")

        val userPswd: UiObject = mDevice.findObject(UiSelector().text("Password"))
        userPswd.waitForExists(timeOut)
        userPswd.setText("AndroidTest")

        val loginBtn: UiObject = mDevice.findObject(UiSelector().description("Login click"))
        loginBtn.waitForExists(timeOut)
        loginBtn.clickAndWaitForNewWindow()
        Thread.sleep(1000)

    }

}
