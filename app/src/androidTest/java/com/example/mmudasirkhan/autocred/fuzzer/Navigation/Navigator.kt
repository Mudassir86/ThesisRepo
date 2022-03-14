package com.example.mmudasirkhan.autocred.fuzzer.Navigation

import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiObject2
import android.support.test.uiautomator.UiSelector
import android.util.Log
import com.example.mmudasirkhan.autocred.fuzzer.login.AppAuthenticator


class Navigator(
    private val device: UiDevice,
    private val packageName: String,
    private val email: String,
    private val password: String
) {
    private val responder = Responder(device)
    private val authenticator = AppAuthenticator()
    private val repository = Repository()
    private val traversList = mutableListOf<UiObject2>()
    private val rootScreen = Screen(0, responder.title(), null)

    fun start() {
        navigate(rootScreen)
    }

    private fun navigate(screen: Screen) {
        fillInput(screen)
        traverseTextViews(screen)
        traverseButtons(screen)
        traverseImageViews(screen)
        traverseLayouts(screen)
        traverseScrollView(screen)
        traverseImageButtons(screen)
    }

    private fun fillInput(screen: Screen) {
        next(responder.editTexts())?.let {
            it.click()
            val text = it.text
            it.text = text
            pressBack()
            nextAction(screen, text)
        }
    }

    private fun traverseTextViews(screen: Screen) {
        next(responder.textViews())?.let {
            click(screen, it)
        }
    }

    private fun traverseImageButtons(screen: Screen) {
        next(responder.imageButtons())?.let {
            click(screen, it)
        }
    }

    private fun traverseImageViews(screen: Screen) {
        next(responder.imageViews())?.let {
            click(screen, it)
        }
    }

    private fun traverseButtons(screen: Screen) {
        next(responder.buttons())?.let {
            click(screen, it)
        }
    }

    private fun traverseLayouts(screen: Screen) {
        next(responder.layouts())?.let {
            click(screen, it)
        }
    }

    private fun traverseScrollView(screen: Screen) {
        val scrollable = responder.scrollable()
        if (scrollable.exists()) {
            for (i in 0 until scrollable.childCount - 1) {
                scrollable.getChild(UiSelector().clickable(true).instance(i)).let { obj ->
                    val action = if (obj.text != null) obj.text else "scrollable"
                    obj.clickAndWaitForNewWindow()
                    nextAction(screen, action)
                }
            }
        }
    }

    private fun click(screen: Screen, view: UiObject2) {
        if (view.isClickable || view.isEnabled || view.isFocusable) {
            val text = if (view.text != null) view.text else view.resourceName
            view.click()
            sleep()
            if (text != null && authenticator.requiresLogin(text)) {
                login(screen, text)
            } else {
                nextAction(screen, text)
            }
        }
    }


    private fun nextAction(screen: Screen, action: String?) {
        val nextScreen = Screen(screen.id + 1, responder.title(), action)
        Log.d("repository - >>>>> ", repository.toString())

        if (!repository.exists(nextScreen)) {
            repository.add(nextScreen)
            navigate(nextScreen)
        } else {
            if (screen.id > 0) {
                traversList.clear()
                pressBack()
                val lastScreen = Screen(screen.id - 1, responder.title(), action)
                repository.add(lastScreen)
            }
        }
    }

    private fun login(screen: Screen, action: String?) {
        authenticator.login(device, email, password) {
            nextAction(screen, action)
        }
    }

    private fun next(list: MutableList<UiObject2>?): UiObject2? {
        if (device.currentPackageName == packageName) {
            list?.let {
                for (obj in it) {
                    if (!traversList.contains(obj)) {
                        traversList.add(obj)
                        return obj
                    }
                }
            }
        }

        return null
    }


    private fun sleep() = Thread.sleep(3000)

    private fun pressBack() {
        responder.pressBack()
        sleep()
    }

}
