package com.example.mmudasirkhan.autocred.fuzzer.login

import android.support.test.uiautomator.UiDevice

class AppAuthenticator : Authenticator {

    private lateinit var authenticator: Authenticator
    var sessionExists = false
    private set

    companion object {
        private const val GOOGLE = "google"
        private const val FACEBOOK = "TODO: Due to NFA disable facebook replace this `facebook`"//"facebook"
    }


    fun requiresLogin(resourceId: String): Boolean {
        if (!sessionExists) {
            if (resourceId.contains(GOOGLE, true)) {
                authenticator = GoogleAuthenticator()
                return true
            }
            else if (resourceId.contains(FACEBOOK, true)) {
                authenticator = FacebookAuthenticator()
                return true
            }
        }
        return false
    }

    override fun login(device: UiDevice, email: String, password: String, completion: (success: Boolean) -> Unit) {
        authenticator.login(device, email, password) {
            sessionExists = it
            completion(it)
        }
    }
}
