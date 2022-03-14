package com.example.mmudasirkhan.autocred.fuzzer.Navigation

class Repository {

    var screens = mutableListOf<Screen>()
    private set

    fun add(screen: Screen) = screens.add(screen)

    fun exists(queryScreen: Screen): Boolean {
        for (screen in screens) {
            if (screen.equal(queryScreen)) {
                return  true
            }
        }

        return false
    }

    override fun toString(): String {
        var s = ""
        for (screen in screens) {
            s += screen.toString()
        }

        return  s
    }

}
