package com.github.api

import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Test

class APITests {
    private val apiClient = GithubClient

    /**
     * Jnit5 assertions: (Use it according to the needs)
     * assertNotNull = When we want to declare or assert that the response wouldn't be null.
     * assertEquals = Asserts that expected and actual are equal.
     * assertFalse(boolean condition) = Asserts that the supplied condition is not true.
     * assertAll = Declares that all supplied executables do not throw exceptions.
     */

    @Test
    fun `Check Search User API`() {
        runBlocking {
            val searchUserResponse = apiClient.service.searchUsers("Manishthapa")
            assertNotNull(searchUserResponse.body()?.items)
        }
    }

}