package com.example.dailyplanet.api

import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for the Constants object.
 * Tests that API constants are properly defined.
 */
class ConstantsTest {

    @Test
    fun `API_KEY should not be empty`() {
        // Assert
        assertNotNull(Constants.API_KEY)
        assertTrue(Constants.API_KEY.isNotEmpty())
    }

    @Test
    fun `BASE_URL should be valid`() {
        // Assert
        assertNotNull(Constants.BASE_URL)
        assertEquals("https://newsapi.org", Constants.BASE_URL)
    }

    @Test
    fun `BASE_URL should use HTTPS`() {
        // Assert
        assertTrue(Constants.BASE_URL.startsWith("https://"))
    }

    @Test
    fun `BASE_URL should not have trailing slash`() {
        // Assert
        assertFalse(Constants.BASE_URL.endsWith("/"))
    }

    @Test
    fun `API_KEY should have valid format`() {
        // Assert - API keys are typically alphanumeric
        assertTrue(Constants.API_KEY.matches(Regex("^[a-zA-Z0-9]+$")))
    }

    @Test
    fun `API_KEY should have reasonable length`() {
        // Assert - NewsAPI keys are typically 32 characters
        assertTrue(Constants.API_KEY.length >= 16)
        assertTrue(Constants.API_KEY.length <= 64)
    }
}
