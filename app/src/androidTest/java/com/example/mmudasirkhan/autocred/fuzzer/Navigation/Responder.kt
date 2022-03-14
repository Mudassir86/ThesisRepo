package com.example.mmudasirkhan.autocred.fuzzer.Navigation

import android.support.test.uiautomator.*

class Responder(private val device: UiDevice) {

    companion object {
        private const val IMAGE_BUTTON = "android.widget.ImageButton"
        private const val IMAGE_VIEW = "android.widget.ImageView"
        private const val BUTTON = "android.widget.Button"
        private const val EDIT_TEXT = "android.widget.EditText"
        private const val TEXT_VIEW = "android.widget.TextView"
        private const val RELATIVE_LAYOUT = "android.widget.RelativeLayout"
        private const val LINEAR_LAYOUT = "android.widget.LinearLayout"
    }

    fun imageButtons(): MutableList<UiObject2>? = device.findObjects(By.clazz(IMAGE_BUTTON))

    fun imageViews(): MutableList<UiObject2>? = device.findObjects(By.clazz(IMAGE_VIEW))

    fun editTexts(): MutableList<UiObject2>? = device.findObjects(By.clazz(EDIT_TEXT))

    fun textViews(): MutableList<UiObject2>? = device.findObjects(By.clazz(TEXT_VIEW))

    fun buttons(): MutableList<UiObject2>? = device.findObjects(By.clazz(BUTTON))

    fun scrollable() = UiScrollable(UiSelector().scrollable(true))

    fun layouts(): MutableList<UiObject2> {
        val list = mutableListOf<UiObject2>()
        device.findObjects(By.clazz(RELATIVE_LAYOUT))?.let {
            list.addAll(it)
        }
        device.findObjects(By.clazz(LINEAR_LAYOUT))?.let {
            list.addAll(it)
        }

        return list
    }

    fun pressBack() = device.pressBack()

    fun title(): String {
        var title = "UNKNOWN"
        if (textViews() != null && textViews()!!.count() > 0) {
            if (textViews()!!.first().text != null) {
                title = textViews()!!.first().text
            }

        }
        return title
    }


}
