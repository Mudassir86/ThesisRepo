package com.example.mmudasirkhan.autocred

import android.support.test.filters.LargeTest
import android.support.test.filters.SdkSuppress
import android.support.test.runner.AndroidJUnit4
import com.example.mmudasirkhan.autocred.fuzzer.Fuzzer
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class FuzzerTests {

    private val drivers = listOf(
        //Fuzzer("com.example.mmudasirkhan.autocred", "", "")
        //Fuzzer("com.android.vending",  "", "")
        //Fuzzer("com.android.contacts","","")
        //Fuzzer("com.accuweather.android","","")
        //Fuzzer("com.wordwebsoftware.android.wordweb","","")
        //Fuzzer("com.google.android.dialer","","")
        //Fuzzer("com.evernote","","")
        //Fuzzer("de.zalando.mobile","","")
        //Fuzzer("org.videolan.vlc","","")
        Fuzzer("com.android.calculator2","","")
    )

    @Test
    fun testYo() {
        for (driver in drivers)
            driver.start()
    }

}
