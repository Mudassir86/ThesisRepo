package com.example.mmudasirkhan.autocred.fuzzer.login

import android.support.test.uiautomator.UiDevice

interface Authenticator {

    fun login(device: UiDevice, email: String, password: String, completion: (success: Boolean) -> Unit)
}
