package com.example.mmudasirkhan.autocred.fuzzer

import com.example.mmudasirkhan.autocred.fuzzer.Navigation.Navigator

class Fuzzer(
    packageName: String,
    email: String,
    password: String
) {

    private val launcher = Launcher(packageName)
    private val navigator = Navigator(launcher.device, packageName, email, password)

    fun start() {
        launcher.launch()
        navigator.start()
    }


}
