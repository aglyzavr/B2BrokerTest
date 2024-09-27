package com.example.myapplication.tests

import com.example.myapplication.pages.ChatPage
import org.junit.Test

class ChatTimerTests : BaseTest() {

    private val activityTestRule = ActivityTestRule(MainActivity::class.java)

    init {
        ruleSequence.addLast(activityTestRule)
    }

    @Test
    fun testTimerStartsAfterSendingMessage() {
        ChatPage.enterMessage("Countdown Test")
            .clickSendButton()
            .verifyTimerDisplayed()
            .verifySendButtonDisabled()
    }

    @Test
    fun testSendButtonReenablesAfterCountdown() {
        ChatPage.enterMessage("Reenable Test")
            .clickSendButton()
            .waitForTimerToEnd()
            .verifySendButtonEnabled()
    }
}
