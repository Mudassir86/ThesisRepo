package com.example.mmudasirkhan.autocred.fuzzer.login

import android.support.test.uiautomator.*

class GoogleAuthenticator : Authenticator {

    companion object {
        private const val TIME_OUT = 5000L
        private const val EDIT_TEXT = "android.widget.EditText"
        private const val BUTTON = "android.widget.Button"
        private const val NEXT = "Next"
        private const val EMAIL_ID = "identifierId"
        private const val EMAIL_NEXT = "identifierNext"
        private const val PASSWORD_NEXT = "passwordNext"
        private const val ACCOUNT_SELECT_ID = "com.google.android.gms:id/account_picker_container"
        private const val CONSENT_NEXT = "signinconsentNext"
        private const val ACCOUNT_SELECTION_LAYOUT = "android.widget.LinearLayout"
        private const val ACCOUNT_SELECTION_PACKAGE_ID = "com.google.android.gms"

    }

    override fun login(device: UiDevice, email: String, password: String, completion: (success: Boolean) -> Unit) {

        device.wait(Until.findObject(By.clazz("android.webkit.WebView")
            .pkg("com.google.android.gms")), TIME_OUT)

        //Account Select
        if (device.hasObject(By.res(ACCOUNT_SELECT_ID))) {
            loginViaAccountSelection(device, email)
        }
        //Enter Credential
        else {
            loginViaCredentials(device, email, password)
        }
        completion(true)
    }

    private fun loginViaAccountSelection(device: UiDevice, email: String) {
        val selectUser = device.findObject(
            UiSelector().instance(1).className(ACCOUNT_SELECTION_LAYOUT)
                .packageName(ACCOUNT_SELECTION_PACKAGE_ID)
        )
        selectUser.clickAndWaitForNewWindow()
    }

    private fun loginViaCredentials(device: UiDevice, email: String, password: String) {
        val userEmail: UiObject = device.findObject(UiSelector().resourceId(EMAIL_ID)
            .className(EDIT_TEXT))
        userEmail.click()
        userEmail.waitForExists(TIME_OUT)
        userEmail.text = email

        val nxtBtn: UiObject = device.findObject(
            UiSelector().text(NEXT).resourceId(EMAIL_NEXT)
                .className(BUTTON))
        nxtBtn.waitForExists(TIME_OUT)
        nxtBtn.clickAndWaitForNewWindow()

        //Enter Password
        val userPswd: UiObject = device.findObject(
            UiSelector().instance(0)
                .className(EDIT_TEXT))
        userPswd.waitForExists(TIME_OUT)
        userPswd.text = password

        val userLogin: UiObject = device.findObject(
            UiSelector().text(NEXT).resourceId(PASSWORD_NEXT)
                .className(BUTTON)
        )
        userLogin.waitForExists(TIME_OUT)
        userLogin.clickAndWaitForNewWindow()
        Thread.sleep(5000)

        val consent: UiObject = device.findObject(
            UiSelector().resourceId(CONSENT_NEXT)
                .className(BUTTON))
        consent.clickAndWaitForNewWindow()
    }
}
