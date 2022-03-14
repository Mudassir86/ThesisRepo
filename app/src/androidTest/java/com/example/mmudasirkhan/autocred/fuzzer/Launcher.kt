package com.example.mmudasirkhan.autocred.fuzzer

import android.content.Intent
import android.content.pm.PackageManager
import android.support.test.InstrumentationRegistry
import android.support.test.uiautomator.By
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.Until
import com.example.mmudasirkhan.autocred.fuzzer.Launcher.Constants.LAUNCH_TIMEOUT
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsNull


class Launcher(private val packageName: String) {

    object Constants {
        const val LAUNCH_TIMEOUT = 5000L
    }

    val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    fun launch() {
        // Initialize UiDevice instance
        // device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // Start from the home screen
        device.pressHome()

        // Wait for launcher
        val appPackage = getPackageName()
        assertThat(appPackage, IsNull.notNullValue())
        device.wait(Until.hasObject(By.pkg(appPackage).depth(0)), LAUNCH_TIMEOUT)


        // Launch the app
        val context = InstrumentationRegistry.getContext()
        val intent = context.packageManager
            .getLaunchIntentForPackage(packageName)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)    // Clear out any previous instances
        context.startActivity(intent)

        // Wait for the app to appear
        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), LAUNCH_TIMEOUT)
    }

    private fun getPackageName(): String {
        // Create launcher Intent
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)

        // Use PackageManager to get the launcher package name
        val pm = InstrumentationRegistry.getContext().packageManager
        val resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return resolveInfo.activityInfo.packageName
    }

}
