package com.example.dailyplanet.db

import com.example.dailyplanet.models.Source
import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for the Converters class.
 * Tests the conversion between Source objects and Strings for Room database storage.
 */
class ConvertersTest {

    private val converters = Converters()

    @Test
    fun `fromSource should convert Source to String`() {
        // Arrange
        val source = Source(id = "bbc-news", name = "BBC News")

        // Act
        val result = converters.fromSource(source)

        // Assert
        assertEquals("BBC News", result)
    }

    @Test
    fun `toSource should convert String to Source with id and name equal`() {
        // Arrange
        val name = "BBC News"

        // Act
        val result = converters.toSource(name)

        // Assert
        // toSource creates Source(name, name) so both id and name are set to the input
        assertEquals(name, result.id)
        assertEquals(name, result.name)
    }

    @Test
    fun `fromSource and toSource roundtrip preserves name`() {
        // Arrange
        val originalName = "CNN"
        val source = Source(id = "cnn", name = originalName)

        // Act
        val converted = converters.fromSource(source)
        val reversed = converters.toSource(converted)

        // Assert
        // Note: fromSource extracts only the name, so original id is lost in roundtrip
        assertEquals(originalName, converted)
        assertEquals(originalName, reversed.name)
        assertEquals(originalName, reversed.id) // id equals name after roundtrip
    }

    @Test
    fun `fromSource with empty name should return empty string`() {
        // Arrange
        val source = Source(id = "test", name = "")

        // Act
        val result = converters.fromSource(source)

        // Assert
        assertEquals("", result)
    }

    @Test
    fun `toSource with empty string should create Source with empty values`() {
        // Arrange
        val name = ""

        // Act
        val result = converters.toSource(name)

        // Assert
        assertEquals("", result.id)
        assertEquals("", result.name)
    }

    @Test
    fun `fromSource with special characters should preserve them`() {
        // Arrange
        val source = Source(id = "test", name = "News & Updates (2024)")

        // Act
        val result = converters.fromSource(source)

        // Assert
        assertEquals("News & Updates (2024)", result)
    }

    @Test
    fun `toSource with special characters should create valid Source`() {
        // Arrange
        val name = "News & Updates (2024)"

        // Act
        val result = converters.toSource(name)

        // Assert
        assertEquals(name, result.name)
        assertEquals(name, result.id)
    }

    @Test
    fun `conversion roundtrip multiple times should be consistent`() {
        // Arrange
        val name = "Test Source"
        val source1 = Source(id = "test", name = name)

        // Act
        val str1 = converters.fromSource(source1)
        val source2 = converters.toSource(str1)
        val str2 = converters.fromSource(source2)
        val source3 = converters.toSource(str2)

        // Assert
        assertEquals(str1, str2)
        assertEquals(source2.name, source3.name)
        assertEquals(source2.id, source3.id)
    }
}
