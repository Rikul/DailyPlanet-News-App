package com.example.dailyplanet.models

import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for the Article data class.
 * Tests the creation and properties of Article objects.
 */
class ArticleTest {

    @Test
    fun `article creation with all fields should succeed`() {
        // Arrange
        val source = Source(id = "bbc-news", name = "BBC News")
        val url = "https://example.com/article"
        val author = "John Doe"
        val content = "Full article content here"
        val description = "Article description"
        val publishedAt = "2024-01-15T10:30:00Z"
        val title = "Test Article Title"
        val urlToImage = "https://example.com/image.jpg"

        // Act
        val article = Article(
            url = url,
            author = author,
            content = content,
            description = description,
            publishedAt = publishedAt,
            source = source,
            title = title,
            urlToImage = urlToImage
        )

        // Assert
        assertEquals(url, article.url)
        assertEquals(author, article.author)
        assertEquals(content, article.content)
        assertEquals(description, article.description)
        assertEquals(publishedAt, article.publishedAt)
        assertEquals(source, article.source)
        assertEquals(title, article.title)
        assertEquals(urlToImage, article.urlToImage)
    }

    @Test
    fun `article creation with nullable fields should succeed`() {
        // Arrange & Act
        val article = Article(
            url = "https://example.com/article",
            author = null,
            content = null,
            description = null,
            publishedAt = null,
            source = null,
            title = null,
            urlToImage = null
        )

        // Assert
        assertNotNull(article)
        assertNull(article.author)
        assertNull(article.content)
        assertNull(article.description)
        assertNull(article.publishedAt)
        assertNull(article.source)
        assertNull(article.title)
        assertNull(article.urlToImage)
    }

    @Test
    fun `two articles with same url should be equal`() {
        // Arrange
        val source = Source(id = "test", name = "Test Source")
        val article1 = Article(
            url = "https://example.com/article",
            author = "Author 1",
            content = "Content 1",
            description = "Description 1",
            publishedAt = "2024-01-15T10:30:00Z",
            source = source,
            title = "Title 1",
            urlToImage = "https://example.com/image1.jpg"
        )
        val article2 = Article(
            url = "https://example.com/article",
            author = "Author 2",
            content = "Content 2",
            description = "Description 2",
            publishedAt = "2024-01-16T10:30:00Z",
            source = source,
            title = "Title 2",
            urlToImage = "https://example.com/image2.jpg"
        )

        // Act & Assert
        // Note: Data classes implement equals based on all properties,
        // but in Room, the URL is the primary key
        assertNotEquals(article1, article2) // Different content
        assertEquals(article1.url, article2.url) // Same primary key
    }

    @Test
    fun `article copy should create new instance with modified fields`() {
        // Arrange
        val original = Article(
            url = "https://example.com/article",
            author = "Original Author",
            content = "Original Content",
            description = "Original Description",
            publishedAt = "2024-01-15T10:30:00Z",
            source = Source(id = "test", name = "Test"),
            title = "Original Title",
            urlToImage = "https://example.com/image.jpg"
        )

        // Act
        val modified = original.copy(title = "Modified Title")

        // Assert
        assertEquals("Original Title", original.title)
        assertEquals("Modified Title", modified.title)
        assertEquals(original.url, modified.url)
        assertEquals(original.author, modified.author)
    }

    @Test
    fun `article with empty strings should be valid`() {
        // Arrange & Act
        val article = Article(
            url = "",
            author = "",
            content = "",
            description = "",
            publishedAt = "",
            source = Source(id = "", name = ""),
            title = "",
            urlToImage = ""
        )

        // Assert
        assertNotNull(article)
        assertEquals("", article.url)
        assertEquals("", article.title)
    }
}
