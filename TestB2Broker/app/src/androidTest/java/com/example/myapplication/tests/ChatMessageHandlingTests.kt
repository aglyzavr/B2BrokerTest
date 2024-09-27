package com.example.myapplication.tests

import com.example.myapplication.pages.ChatPage
import com.example.myapplication.utils.MockEnvironmentUtils
import com.example.myapplication.utils.MockEnvironmentUtils.initializeMockEnvironment
import org.junit.Test

class ChatMessageHandlingTests : BaseTest() {

    private val activityTestRule = ActivityTestRule(MainActivity::class.java)

    init {
        ruleSequence.addLast(activityTestRule)
    }

    @Test
    fun testSendMessageWithNetworkFailure() {
        initializeMockEnvironment(MockEnvironmentUtils.MockEnvironment.NETWORK_FAILURE)
        ChatPage.enterMessage("Test message")
            .clickSendButton()
            .verifyNetworkFailureMessage()
    }

    @Test
    fun testSendMessageWithSlowNetwork() {
        initializeMockEnvironment(MockEnvironmentUtils.MockEnvironment.SLOW_NETWORK)
        ChatPage.enterMessage("Delayed message")
            .clickSendButton()
            .verifyMessageQueued()
    }

    @Test
    fun testOfflineMessageHandling() {
        ChatPage.enterMessage("Offline Test")
            .clickSendButton()
            .verifyOfflineBehavior()
    }
}
