package com.example.dailyplanet.models

import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for the NewsResponse data class.
 * Tests the creation and properties of NewsResponse objects.
 */
class NewsResponseTest {

    @Test
    fun `newsResponse creation with articles should succeed`() {
        // Arrange
        val article1 = Article(
            url = "https://example.com/article1",
            author = "Author 1",
            content = "Content 1",
            description = "Description 1",
            publishedAt = "2024-01-15T10:30:00Z",
            source = Source(id = "test", name = "Test Source"),
            title = "Title 1",
            urlToImage = "https://example.com/image1.jpg"
        )
        val article2 = Article(
            url = "https://example.com/article2",
            author = "Author 2",
            content = "Content 2",
            description = "Description 2",
            publishedAt = "2024-01-16T10:30:00Z",
            source = Source(id = "test", name = "Test Source"),
            title = "Title 2",
            urlToImage = "https://example.com/image2.jpg"
        )
        val articles = listOf(article1, article2)
        val status = "ok"
        val totalResults = 2

        // Act
        val newsResponse = NewsResponse(
            articles = articles,
            status = status,
            totalResults = totalResults
        )

        // Assert
        assertEquals(articles, newsResponse.articles)
        assertEquals(status, newsResponse.status)
        assertEquals(totalResults, newsResponse.totalResults)
        assertEquals(2, newsResponse.articles.size)
    }

    @Test
    fun `newsResponse with empty articles list should succeed`() {
        // Arrange
        val articles = emptyList<Article>()
        val status = "ok"
        val totalResults = 0

        // Act
        val newsResponse = NewsResponse(
            articles = articles,
            status = status,
            totalResults = totalResults
        )

        // Assert
        assertNotNull(newsResponse)
        assertTrue(newsResponse.articles.isEmpty())
        assertEquals(0, newsResponse.totalResults)
        assertEquals("ok", newsResponse.status)
    }

    @Test
    fun `newsResponse with error status should succeed`() {
        // Arrange
        val articles = emptyList<Article>()
        val status = "error"
        val totalResults = 0

        // Act
        val newsResponse = NewsResponse(
            articles = articles,
            status = status,
            totalResults = totalResults
        )

        // Assert
        assertEquals("error", newsResponse.status)
        assertTrue(newsResponse.articles.isEmpty())
    }

    @Test
    fun `newsResponse totalResults should match article count`() {
        // Arrange
        val articles = listOf(
            Article(
                url = "https://example.com/article1",
                author = null,
                content = null,
                description = null,
                publishedAt = null,
                source = null,
                title = "Title 1",
                urlToImage = null
            ),
            Article(
                url = "https://example.com/article2",
                author = null,
                content = null,
                description = null,
                publishedAt = null,
                source = null,
                title = "Title 2",
                urlToImage = null
            ),
            Article(
                url = "https://example.com/article3",
                author = null,
                content = null,
                description = null,
                publishedAt = null,
                source = null,
                title = "Title 3",
                urlToImage = null
            )
        )

        // Act
        val newsResponse = NewsResponse(
            articles = articles,
            status = "ok",
            totalResults = 3
        )

        // Assert
        assertEquals(newsResponse.articles.size, newsResponse.totalResults)
    }

    @Test
    fun `newsResponse copy should create new instance with modified fields`() {
        // Arrange
        val original = NewsResponse(
            articles = emptyList(),
            status = "ok",
            totalResults = 0
        )

        // Act
        val modified = original.copy(status = "error")

        // Assert
        assertEquals("ok", original.status)
        assertEquals("error", modified.status)
        assertEquals(original.articles, modified.articles)
        assertEquals(original.totalResults, modified.totalResults)
    }

    @Test
    fun `two newsResponses with same values should be equal`() {
        // Arrange
        val articles = listOf(
            Article(
                url = "https://example.com/article",
                author = "Author",
                content = null,
                description = null,
                publishedAt = null,
                source = null,
                title = "Title",
                urlToImage = null
            )
        )
        val newsResponse1 = NewsResponse(
            articles = articles,
            status = "ok",
            totalResults = 1
        )
        val newsResponse2 = NewsResponse(
            articles = articles,
            status = "ok",
            totalResults = 1
        )

        // Act & Assert
        assertEquals(newsResponse1, newsResponse2)
    }
}
