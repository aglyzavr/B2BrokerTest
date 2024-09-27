package com.example.myapplication.tests

import androidx.test.rule.ActivityTestRule
import com.example.myapplication.pages.ChatPage
import com.example.myapplication.utils.MockEnvironmentUtils.MockEnvironment
import org.junit.Test

class ChatBasicUserActionTests : BaseTest() {

    private val activityTestRule = ActivityTestRule(MainActivity::class.java)

    init {
        ruleSequence.addLast(activityTestRule)
    }

    @Test
    fun testSendMessageSuccessfully() {
        ChatPage.enterMessage("Hello World")
            .clickSendButton()
            .verifyMessageSent("Hello World", ChatPage.getCurrentFormattedTimestamp())
    }

    @Test
    fun testMessageDisplayWithTimestamp() {
        val timestamp = ChatPage.getCurrentFormattedTimestamp()
        ChatPage.enterMessage("Test Message")
            .clickSendButton()
            .verifyMessageSent("Test Message", timestamp)
    }

    @Test
    fun testTextFieldHintVisibility() {
        ChatPage.verifyHintVisibility()
    }

    @Test
    fun testSendingEmptyMessage() {
        ChatPage.verifySendButtonDoesNothingWhenInputIsEmpty()
    }

    @Test
    fun testInputFieldClearsAfterSending() {
        ChatPage.enterMessage("Clear Field Test")
            .clickSendButton()
            .checkInputFieldIsEmpty()
    }

    @Test
    fun testUserSendsMessageSuccessfully() {
        // Arrange - Set up mock environment for successful send
        initializeMockEnvironment(MockEnvironment.SUCCESSFUL_SEND)

        // Act - User enters a message and sends it
        ChatPage.enterMessage("Positive Test Message")
            .clickSendButton()

        // Assert - Verify that the message is sent successfully
        ChatPage.verifyMessageSent("Positive Test Message", ChatPage.getCurrentFormattedTimestamp())
    }

    @Test
    fun testUserFailsToSendMessage() {
        // Arrange - Set up mock environment for a failed send
        initializeMockEnvironment(MockEnvironment.NETWORK_FAILURE)

        // Act - User enters a message and attempts to send it
        ChatPage.enterMessage("Negative Test Message")
            .clickSendButton()

        // Assert - Verify that an error message is displayed due to network failure
        ChatPage.verifyNetworkFailureMessage()
    }

    @Test
    fun testDefaultScreenStateAfterFreshInstall() {
        //TODO Test to verify the default state of the chat screen after a fresh install
        ChatPage.verifyDefaultScreenState()
    }

    @Test
    fun testScreenStateWithExistingChatHistory() {
        //TODO Test to verify the chat screen state when there is existing chat history
        ChatPage.verifyScreenStateWithChatHistory()
    }

    @Test
    fun testReceivedMessagesAreVisuallyDistinct() {
        //TODO Verify that received messages are visually distinct from sent messages
        ChatPage.sendMessage("User Sent Message")
            .verifyReceivedMessage("Received Message", distinctFromSent = true)
    }

    @Test
    fun testMessageWithEmojisAndHyperlinks() {
        //TODO Test to verify messages with special content such as emojis and hyperlinks are handled correctly
        ChatPage.enterMessage("Hello 😊 Visit https://example.com")
            .clickSendButton()
            .verifyMessageSent("Hello 😊 Visit https://example.com", chatScreen.getCurrentFormattedTimestamp())
            .verifyMessageContainsEmojiAndLink()
    }

    @Test
    fun testTimerBehaviorOnMessageSend() {
        ChatPage.enterMessage("Testing Timer")
            .clickSendButton()
            .verifyTimerDisplayed()
            .waitForTimerToEnd()
            .verifySendButtonEnabled()
    }
}
