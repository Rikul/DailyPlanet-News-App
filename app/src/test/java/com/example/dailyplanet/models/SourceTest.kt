package com.example.dailyplanet.models

import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for the Source data class.
 * Tests the creation and properties of Source objects.
 */
class SourceTest {

    @Test
    fun `source creation with all fields should succeed`() {
        // Arrange
        val id = "bbc-news"
        val name = "BBC News"

        // Act
        val source = Source(id = id, name = name)

        // Assert
        assertEquals(id, source.id)
        assertEquals(name, source.name)
    }

    @Test
    fun `source creation with null id should succeed`() {
        // Arrange
        val name = "Unknown Source"

        // Act
        val source = Source(id = null, name = name)

        // Assert
        assertNull(source.id)
        assertEquals(name, source.name)
    }

    @Test
    fun `two sources with same values should be equal`() {
        // Arrange
        val source1 = Source(id = "bbc-news", name = "BBC News")
        val source2 = Source(id = "bbc-news", name = "BBC News")

        // Act & Assert
        assertEquals(source1, source2)
        assertEquals(source1.hashCode(), source2.hashCode())
    }

    @Test
    fun `two sources with different ids should not be equal`() {
        // Arrange
        val source1 = Source(id = "bbc-news", name = "BBC News")
        val source2 = Source(id = "cnn", name = "BBC News")

        // Act & Assert
        assertNotEquals(source1, source2)
    }

    @Test
    fun `source copy should create new instance with modified fields`() {
        // Arrange
        val original = Source(id = "bbc-news", name = "BBC News")

        // Act
        val modified = original.copy(name = "BBC News International")

        // Assert
        assertEquals("BBC News", original.name)
        assertEquals("BBC News International", modified.name)
        assertEquals(original.id, modified.id)
    }

    @Test
    fun `source with empty strings should be valid`() {
        // Arrange & Act
        val source = Source(id = "", name = "")

        // Assert
        assertNotNull(source)
        assertEquals("", source.id)
        assertEquals("", source.name)
    }
}
