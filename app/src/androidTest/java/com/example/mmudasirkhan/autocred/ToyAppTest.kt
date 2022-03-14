package com.example.mmudasirkhan.autocred

import android.content.Intent
import android.content.pm.PackageManager
import android.support.test.InstrumentationRegistry
import android.support.test.filters.LargeTest
import android.support.test.filters.SdkSuppress
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.*
import com.facebook.FacebookSdk.getApplicationContext
import org.junit.Before
import org.hamcrest.core.IsNull.notNullValue
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith

private const val BASIC_SAMPLE_PACKAGE = "de.fraunhofer.sit.adam.testfirebase"
private const val LAUNCH_TIMEOUT = 5000L
private const val STRING_TO_BE_TYPED = "UiAutomator"

@LargeTest
@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class ToyAppTest {

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
    fun firebaseSignIn() {
        //Espresso.onView(ViewMatchers.withId(R.id.btnLogin)).perform(ViewActions.click())
        mDevice.wait(Until.findObject(By.pkg("de.fraunhofer.sit.adam.testfirebase")), timeOut)

        //User Login
        val loginBtn: UiObject = mDevice.findObject(
            UiSelector()
                .className("android.widget.Button")
                .resourceId("de.fraunhofer.sit.adam.testfirebase:id/btnLogin")
        )
        loginBtn.waitForExists(timeOut)
        loginBtn.clickAndWaitForNewWindow()

        //Account Select
        if (mDevice.hasObject(By.res("android:id/content"))) {
            // else{
            val selectUser = mDevice.findObject(
                UiSelector().instance(1).className("android.widget.LinearLayout")
                    .packageName("com.google.android.gms"))
            selectUser.clickAndWaitForNewWindow()
        }
        //Enter Credential
        else {
            val userEmail: UiObject = mDevice.findObject(
                UiSelector().resourceId("identifierId")
                    .className("android.widget.EditText")
            )
            userEmail.click()
            userEmail.waitForExists(timeOut)
            userEmail.setText("actest@gmail.com")

            val nxtBtn: UiObject = mDevice.findObject(
                UiSelector().text("Next").resourceId("identifierNext")
                    .className("android.widget.Button")
            )
            nxtBtn.waitForExists(timeOut)
            nxtBtn.clickAndWaitForNewWindow()

            //Enter Password
            val userPswd: UiObject = mDevice.findObject(
                UiSelector().instance(0)
                    .className("android.widget.EditText")
            )
            userPswd.waitForExists(timeOut)
            userPswd.setText("android")

            val userlogin: UiObject = mDevice.findObject(
                UiSelector().text("Next").resourceId("passwordNext")
                    .className("android.widget.Button")
            )
            userlogin.waitForExists(timeOut)
            userlogin.clickAndWaitForNewWindow()
            Thread.sleep(1000)
        }
    }
}

