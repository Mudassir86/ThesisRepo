package com.example.mmudasirkhan.autocred.fuzzer.login

import android.support.test.uiautomator.*
import java.lang.Exception

class FacebookAuthenticator : Authenticator {

    companion object {
        private const val TIME_OUT = 5000L
        private const val PACKAGE = "com.facebook.katana"
        private const val LOGIN_IDENTIFIER = "Phone or Email"
        private const val PASSWORD_IDENTIFIER = "Password"
        private const val LOGIN_BUTTON_IDENTIFIER = "Login click"
    }

    override fun login(device: UiDevice, email: String, password: String, completion: (success: Boolean) -> Unit) {
        try {
            device.wait(Until.findObject(By.pkg(PACKAGE)), TIME_OUT)

            val emailField: UiObject = device.findObject(UiSelector().text(LOGIN_IDENTIFIER))
            emailField.waitForExists(TIME_OUT)
            emailField.text = email

            val passwordField: UiObject = device.findObject(UiSelector().text(PASSWORD_IDENTIFIER))
            passwordField.waitForExists(TIME_OUT)
            passwordField.text = password

            val loginButton: UiObject = device.findObject(UiSelector().description(LOGIN_BUTTON_IDENTIFIER))
            loginButton.waitForExists(TIME_OUT)
            loginButton.clickAndWaitForNewWindow()

            completion(true)
        }
        catch (exception: Exception) {
            completion(false)
        }

    }

}
