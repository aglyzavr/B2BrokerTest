package com.example.myapplication.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.myapplication.MainActivity
import com.example.myapplication.pages.ChatPage
import org.junit.Rule
import org.junit.Test

class ChatPerformanceTests : BaseTest() {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testScrollThroughMockedMessages() {
        ChatPage.messagesRecyclerView.apply {
            scrollToPosition(39)
            assertIsDisplayedAtPosition(39, "Message 39")
        }
    }
}
