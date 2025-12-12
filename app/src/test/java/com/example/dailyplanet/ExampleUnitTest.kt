package com.example.dailyplanet

import org.junit.Test
import org.junit.Assert.*

/**
 * Basic unit tests to verify the test infrastructure is working correctly.
 */
class BasicUnitTest {
    
    @Test
    fun `addition is correct`() {
        assertEquals(4, 2 + 2)
    }
    
    @Test
    fun `string concatenation works`() {
        val result = "Daily" + "Planet"
        assertEquals("DailyPlanet", result)
    }
    
    @Test
    fun `list operations work correctly`() {
        val list = listOf(1, 2, 3, 4, 5)
        assertEquals(5, list.size)
        assertEquals(1, list.first())
        assertEquals(5, list.last())
        assertTrue(list.contains(3))
    }
    
    @Test
    fun `nullable type handling works`() {
        val nullable: String? = null
        val nonNull: String? = "test"
        
        assertNull(nullable)
        assertNotNull(nonNull)
        assertEquals("test", nonNull)
    }
}