package com.example.mmudasirkhan.autocred.fuzzer.Navigation

data class Screen(val id: Int, val name: String, val action: String?) {

    fun equal(screen: Screen) = this.name == screen.name &&
            this.action == screen.action

    override fun toString() = "[ name -> ${this.name}, action -> ${this.action} ] \n"

}

