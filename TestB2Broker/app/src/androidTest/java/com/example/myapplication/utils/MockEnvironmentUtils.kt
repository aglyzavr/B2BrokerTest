package com.example.myapplication.utils

import org.mockito.Mockito
import org.mockito.Mockito.`when`

object MockEnvironmentUtils {

    enum class MockEnvironment {
        DEFAULT,
        NETWORK_FAILURE,
        SLOW_NETWORK,
        SUCCESSFUL_SEND
    }

    fun initializeMockEnvironment(env: MockEnvironment) {
        val mockNetworkService = Mockito.mock(NetworkService::class.java)

        when (env) {
            MockEnvironment.NETWORK_FAILURE -> {
                `when`(mockNetworkService.sendMessage()).thenReturn("Error: Network Failure")
                `when`(mockNetworkService.isNetworkAvailable()).thenReturn(false)
            }
            MockEnvironment.SLOW_NETWORK -> {
                `when`(mockNetworkService.sendMessage()).thenReturn("Pending")
                `when`(mockNetworkService.isNetworkAvailable()).thenReturn(true)
                `when`(mockNetworkService.getLatency()).thenReturn(5000) // Simulate delay
            }
            MockEnvironment.SUCCESSFUL_SEND -> {
                `when`(mockNetworkService.sendMessage()).thenReturn("OK")
                `when`(mockNetworkService.isNetworkAvailable()).thenReturn(true)
            }
            MockEnvironment.DEFAULT -> {
                `when`(mockNetworkService.sendMessage()).thenReturn("OK")
                `when`(mockNetworkService.isNetworkAvailable()).thenReturn(true)
            }
        }
    }

}
