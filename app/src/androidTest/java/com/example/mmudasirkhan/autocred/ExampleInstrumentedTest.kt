package com.example.mmudasirkhan.autocred

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.*
import org.junit.runner.RunWith
import android.support.test.uiautomator.UiSelector
import android.support.test.uiautomator.UiObject
import org.junit.*


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)
    val timeOut: Long = 1000 * 60
    val mDevice: UiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @Before
        fun setup() {
            Intents.init()
    }
    @After
        fun teardown() {
            Intents.release()
    }


    @Test
    fun visibleButton() {
        onView(withId(R.id.updateButton)).check(matches(isDisplayed()))
        onView(withId(R.id.login_button)).check(matches(isDisplayed()))
        onView(withId(R.id.googleButton)).check(matches(isDisplayed()))
    }

    @Test
    fun updateLabel() {
        onView(withId(R.id.textInput)).perform(typeText("foo"), closeSoftKeyboard())
        // click update button
        onView(withId(R.id.updateButton)).perform(click())
        onView(withId(R.id.labelView)).check(matches(withText("foo")))
    }

    @Test
    fun googleSignIn() {
        onView(withId(R.id.googleButton)).perform(click())
        Thread.sleep(1000)
        mDevice.wait(Until.findObject(By.clazz("android.webkit.WebView")
            .pkg("com.google.android.gms")), timeOut)

        //Account Select
        if (mDevice.hasObject(By.res("com.google.android.gms:id/account_picker"))) {
            val selectUser = mDevice.findObject(
                UiSelector().instance(1).className("android.widget.LinearLayout")
                    .packageName("com.google.android.gms"))
            selectUser.clickAndWaitForNewWindow()
        }

        //Enter Credential
        else {
            val userEmail: UiObject = mDevice.findObject(UiSelector().resourceId("identifierId")
                    .className("android.widget.EditText"))
            userEmail.click()
            userEmail.waitForExists(timeOut)
            userEmail.setText("actest@gmail.com")

            val nxtBtn: UiObject = mDevice.findObject(
                UiSelector().text("Next").resourceId("identifierNext")
                    .className("android.widget.Button"))
            nxtBtn.waitForExists(timeOut)
            nxtBtn.clickAndWaitForNewWindow()

            //Enter Password
            val userPswd: UiObject = mDevice.findObject(
                UiSelector().instance(0)
                    .className("android.widget.EditText"))
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
    fun fbSignIn() {

        onView(withId(R.id.login_button)).perform(click())
        Thread.sleep(1000)

        mDevice.wait(Until.findObject(By.pkg("com.facebook.katana")),timeOut)

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
