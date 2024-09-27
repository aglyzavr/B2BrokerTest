package com.example.myapplication.tests

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rulesequence.RuleSequence
import com.setupteardown.SetUpRule
import com.example.myapplication.utils.MockEnvironmentUtils
import com.example.myapplication.utils.MockEnvironmentUtils.MockEnvironment
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseTest {

    val setupRule = SetUpRule("Setup rule for initializing tests")
        .add(name = "Initialize Mock Environment") {
            MockEnvironmentUtils.initializeMockEnvironment(MockEnvironment.DEFAULT)
        }

    @get:Rule
    open val ruleSequence = RuleSequence(setupRule)

    companion object {
        @BeforeClass
        @JvmStatic
        fun config() {
            Config.applyRecommended()
        }
    }
}
