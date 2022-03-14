package com.example.mmudasirkhan.autocred

import android.content.Intent
import android.content.pm.PackageManager
import android.support.test.InstrumentationRegistry
import android.support.test.filters.LargeTest
import android.support.test.filters.SdkSuppress
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.*
import com.facebook.FacebookSdk
import org.hamcrest.core.IsNull
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

private const val BASIC_SAMPLE_PACKAGE = "com.duolingo"
private const val LAUNCH_TIMEOUT = 5000L
private const val STRING_TO_BE_TYPED = "UiAutomator"

@LargeTest
@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class DuolingoTest {

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
        Assert.assertThat<String>(launcherPackage, IsNull.notNullValue())
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT)

        // Launch the app
        val context = FacebookSdk.getApplicationContext()
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
        val pm = FacebookSdk.getApplicationContext().packageManager
        val resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return resolveInfo.activityInfo.packageName
    }

    @Test
        fun googleSignin (){

        mDevice.wait(Until.findObject(By.pkg("com.duolingo")), timeOut)

        //Already have an account
        val account: UiObject = mDevice.findObject(
            UiSelector()
                .className("android.widget.TextView")
                .resourceId("com.duolingo:id/introFlowLoginButton")
        )
        account.waitForExists(timeOut)
        account.clickAndWaitForNewWindow()

        val googleBtn: UiObject = mDevice.findObject(
            UiSelector()
                .resourceId("com.duolingo:id/google_login_button")
                .className("android.widget.Button"))
        googleBtn.clickAndWaitForNewWindow()
        Thread.sleep(1000)

        //Account Select
        if (mDevice.hasObject(By.res("android:id/content"))) {
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
            userEmail.clickAndWaitForNewWindow()
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

    @Test
    fun duoFbSignIn() {

        val account: UiObject = mDevice.findObject(
            UiSelector()
                .className("android.widget.TextView")
                .resourceId("com.duolingo:id/introFlowLoginButton")
        )
        account.waitForExists(timeOut)
        account.clickAndWaitForNewWindow()

        val fbBtn: UiObject = mDevice.findObject(
            UiSelector()
                .className("android.widget.Button")
                .resourceId("com.duolingo:id/fb_login_button")
        )
        fbBtn.waitForExists(timeOut)
        fbBtn.clickAndWaitForNewWindow()

        mDevice.wait(Until.findObject(By.pkg("com.facebook.katana")),timeOut)

        /*if (mDevice.hasObject(By.clazz("android.widget.FrameLayout").pkg("com.facebook.katana"))){

            val selectUser = mDevice.findObject(
                UiSelector().instance(1).text("CONTINUE AS")
                    .resourceId("com.facebook.katana:id/(name removed)")
                    .className("android.widget.Button")
                    .packageName("com.facebook.katana"))
            selectUser.waitForExists(timeOut)
            selectUser.clickAndWaitForNewWindow()
        }

        else {*/
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
       // }
    }
}
