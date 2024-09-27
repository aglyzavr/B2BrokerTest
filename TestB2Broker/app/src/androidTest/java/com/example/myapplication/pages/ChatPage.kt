package com.example.myapplication.pages

import com.example.myapplication.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// I made ChatPage abstract because it uses methods from the BasePage
// interface that aren’t fully implemented, preventing errors.
// In a real project, I would use object for page classes in the Page Object pattern
// because it ensures a singleton instance, making the pages reusable.
abstract class ChatPage : BasePage {

    private val messageInput = withHint("Enter your message")
    private val sendButton = withId(R.id.send_button)
    private val timerView = withId(R.id.timer_view)
    private val messageStatus = withId(R.id.message_status)
    private val errorMessage = "No internet connection. Please try again."
    private val messagesRecyclerView = withRecyclerView(withId(R.id.messages_recycler_view))

    fun enterMessage(message: String): ChatPage = apply {
        step("Enter message '$message'") {
            messageInput.replaceText(message)
        }
    }

    fun checkInputFieldIsEmpty(): ChatPage = apply {
        step("Check that input field is empty") {
            messageInput.hasText("")
        }
    }

    fun verifyHintVisibility(): ChatPage = apply {
        step("Verify hint visibility in the input field") {
            messageInput.isDisplayed().hasText("Enter your message")
        }
    }

    fun clickSendButton(): ChatPage = apply {
        step("Click on send button to send message") {
            sendButton.click()
        }
    }

    fun verifyNetworkFailureMessage() = apply {
        step("Verify network failure message is displayed") {
            withText(errorMessage).isDisplayed()
        }
    }

    fun verifyTimerDisplayed(): ChatPage = apply {
        step("Verify timer is displayed on the screen") {
            timerView.isDisplayed()
        }
    }

    fun verifyOfflineBehavior() {
        verifyNetworkFailureMessage()
        verifyMessageQueued()
    }

    fun verifyMessageSent(message: String, timestamp: String) = apply {
        step("Verify that message '$message' with timestamp '$timestamp' is displayed") {
            getMessageItem(message).apply {
                isDisplayed()
                hasText(containsString(message))
                hasText(containsString(timestamp))
            }
        }
    }

    fun waitForTimerToEnd(timeoutMs: Long = 10000) = apply {
        step("Wait for timer to end within $timeoutMs milliseconds") {
            timerView.withTimeout(timeoutMs).isNotDisplayed()
        }
    }

    fun verifySendButtonDoesNothingWhenInputIsEmpty(): ChatPage = apply {
        step("Verify that send button does nothing when message input is empty") {
            messageInput.hasText("")
            val initialMessageCount = getMessageCount()
            sendButton.click()
            val newMessageCount = getMessageCount()
            assertThat(newMessageCount).isEqualTo(initialMessageCount)
            errorSnackbar.isNotDisplayed()
        }
    }

    fun verifySendButtonEnabled(): ChatPage = apply {
        step("Verify that send button is enabled") {
            sendButton.isEnabled()
        }
    }

    fun verifySendButtonDisabled(): ChatPage = apply {
        step("Verify that send button is not enabled") {
            sendButton.isNotEnabled()
        }
    }

    fun verifyMessageQueued() = apply {
        step("Verify message is queued with status 'Pending'") {
            messageStatus.hasText("Pending").isDisplayed()
        }
    }

    fun getCurrentFormattedTimestamp(): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return dateFormat.format(Date())
    }

    fun scrollToPosition(position: Int) = apply {
        step("Scroll to position $position in the RecyclerView") {
            messagesRecyclerView.scrollToPosition(position)
        }
    }

    fun assertIsDisplayedAtPosition(position: Int, message: String) = apply {
        step("Verify message \"$message\" is displayed at position $position") {
            messagesRecyclerView.getItemAtPosition(position).hasText(message).isDisplayed()
        }
    }

    fun isButtonBlockedByTimer() = apply {
        step("Check if send button is blocked by timer") {
            if (!isSendButtonBlockedByTimer()) {
                throw AssertionError("Expected the send button to be blocked by the timer," +
                        " but it was not.")
            }
        }
    }

    fun waitButtonUnblockedByTimerBlocked(timeoutMs: Long = 10000) = apply {
        step("Wait until the send button is unblocked by timer") {
            var currentTime = 0L
            val interval = 500L

            while (currentTime < timeoutMs) {
                if (!isSendButtonBlockedByTimer()) break
                Thread.sleep(interval)
                currentTime += interval
            }

            try {
                assertSendButtonIsClickable()
            } catch (e: AssertionError) {
                throw AssertionError(
                    "Expected the send button to be unblocked and clickable, " +
                            "but it remained blocked after $timeoutMs ms.",
                    e
                )
            }
        }
    }

    private fun isSendButtonBlockedByTimer(): Boolean {
        return try {
            sendButton.hasText(containsString(Regex("Отправить\\(\\d+\\)"))).isNotClickable()
            true
        } catch (e: AssertionError) {
            false
        }
    }

    private fun assertSendButtonIsClickable() {
        sendButton.hasText("Отправить")
        sendButton.isClickable()
    }
}
