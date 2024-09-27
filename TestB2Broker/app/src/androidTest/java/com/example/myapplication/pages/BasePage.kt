package com.example.myapplication.pages

import androidx.test.espresso.ViewInteraction

// This interface defines common page interaction methods that can be used
// with the Ultron framework (https://open-tool.github.io/ultron/docs/).
interface BasePage {

    // Finds an element by its resource ID
    fun withId(resId: Int): ViewInteraction

    // Finds an element by its hint text
    fun withHint(hintText: String): ViewInteraction

    // Finds an element by its visible text
    fun withText(text: String): ViewInteraction

    // Clicks on the specified element
    fun ViewInteraction.click(): ViewInteraction

    // Replaces text in an input field
    fun ViewInteraction.replaceText(text: String): ViewInteraction

    // Checks if the element has the given text
    fun hasText(text: String): ViewInteraction

    // Verifies the element is displayed
    fun isDisplayed(): ViewInteraction

    // Checks if the element is enabled
    fun ViewInteraction.isEnabled(): ViewInteraction

    // Checks if the element is not enabled
    fun ViewInteraction.isNotEnabled(): ViewInteraction

    // Checks if the element is clickable
    fun ViewInteraction.isClickable(): ViewInteraction

    // Checks if the element is not clickable
    fun ViewInteraction.isNotClickable(): ViewInteraction

    // Verifies the element is not displayed
    fun ViewInteraction.isNotDisplayed(): ViewInteraction

    // Logs and performs a step for test actions
    fun step(description: String, action: () -> Unit)
}
