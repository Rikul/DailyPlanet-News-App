package com.example.dailyplanet

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dailyplanet.ui.composables.FontSizeKey
import com.example.dailyplanet.ui.composables.FontFamilyKey
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun verifySettingsButtonReplacesAboutButton() {
        // Verify "Settings" button is present and displayed
        composeTestRule.onNodeWithText("Settings").assertIsDisplayed()

        // Verify "About" button is NOT present
        composeTestRule.onNodeWithText("About").assertDoesNotExist()
    }

    @Test
    fun verifySettingsNavigation() {
        // Click on the "Settings" button in the bottom navigation
        composeTestRule.onNodeWithText("Settings").performClick()

        // Verify that we are on the Settings screen by checking for unique content
        composeTestRule.onNodeWithText("Powered by NewsAPI.org").assertIsDisplayed()
    }

    @Test
    fun verifyTextSizeOptions() {
        // Click on the "Settings" button in the bottom navigation
        composeTestRule.onNodeWithText("Settings").performClick()

        // Verify that Text Size options are displayed
        composeTestRule.onNodeWithText("Small").assertIsDisplayed()
        composeTestRule.onNodeWithText("Medium").assertIsDisplayed()
        composeTestRule.onNodeWithText("Large").assertIsDisplayed()
    }

    @Test
    fun verifyFontOptions() {
        // Click on the "Settings" button in the bottom navigation
        composeTestRule.onNodeWithText("Settings").performClick()

        // Verify that Font options are displayed
        composeTestRule.onNodeWithText("Default").assertIsDisplayed()
        composeTestRule.onNodeWithText("Serif").assertIsDisplayed()
        composeTestRule.onNodeWithText("Monospace").assertIsDisplayed()
    }

    @Test
    fun verifyViewTypeOptions() {
        // Click on the "Settings" button in the bottom navigation
        composeTestRule.onNodeWithText("Settings").performClick()

        // Verify that View Type options are displayed
        composeTestRule.onNodeWithText("Headlines").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tile").assertIsDisplayed()
    }

    @Test
    fun verifyHeadlinesViewType() {
        // Click on the "Settings" button in the bottom navigation
        composeTestRule.onNodeWithText("Settings").performClick()

        // Select "Headlines" view type
        composeTestRule.onNodeWithText("Headlines").performClick()

        // Navigate back to Home
        composeTestRule.onNodeWithText("Home").performClick()

        composeTestRule.onAllNodesWithTag("NewsImage", useUnmergedTree = true).onFirst().assertDoesNotExist()
        composeTestRule.onAllNodesWithTag("ArticleDescription", useUnmergedTree = true).onFirst().assertDoesNotExist()
    }

    @Test
    fun verifyTileViewType() {
        // Click on the "Settings" button in the bottom navigation
        composeTestRule.onNodeWithText("Settings").performClick()

        // Select "Tile" view type
        composeTestRule.onNodeWithText("Tile").performClick()

        // Navigate back to Home
        composeTestRule.onNodeWithText("Home").performClick()

        composeTestRule.onAllNodesWithTag("NewsImage", useUnmergedTree = true).onFirst().assertExists()
        composeTestRule.onAllNodesWithTag("ArticleDescription", useUnmergedTree = true).onFirst().assertExists()

    }

    @Test
    fun verifyFontSelection() {
        // Click on the "Settings" button in the bottom navigation
        composeTestRule.onNodeWithText("Settings").performClick()

        // Select "Serif" font
        composeTestRule.onNodeWithText("Serif").performClick()

        // Navigate back to Home
        composeTestRule.onNodeWithText("Home").performClick()

        // Go back to settings
        composeTestRule.onNodeWithText("Settings").performClick()

        // Select "Monospace" font
        composeTestRule.onNodeWithText("Monospace").performClick()

        // Navigate back to Home
        composeTestRule.onNodeWithText("Home").performClick()
    }

    @Test
    fun verifySerifFont() {
        // Click on the "Settings" button in the bottom navigation
        composeTestRule.onNodeWithText("Settings").performClick()

        // Select "Serif" font
        composeTestRule.onNodeWithText("Serif").performClick()

        // Navigate back to Home
        composeTestRule.onNodeWithText("Home").performClick()

        // Verify that the article title has Serif font family
        val node = composeTestRule.onAllNodesWithTag("ArticleTitle", useUnmergedTree = true)
            .onFirst()
            .fetchSemanticsNode()

        val fontFamily = node.config[FontFamilyKey]

        assertEquals(FontFamily.Serif, fontFamily)
    }

    @Test
    fun verifyMonospaceFont() {
        // Click on the "Settings" button in the bottom navigation
        composeTestRule.onNodeWithText("Settings").performClick()

        // Select "Monospace" font
        composeTestRule.onNodeWithText("Monospace").performClick()

        // Navigate back to Home
        composeTestRule.onNodeWithText("Home").performClick()

        // Verify that the article title has Monospace font family
        val node = composeTestRule.onAllNodesWithTag("ArticleTitle", useUnmergedTree = true)
            .onFirst()
            .fetchSemanticsNode()

        val fontFamily = node.config[FontFamilyKey]

        assertEquals(FontFamily.Monospace, fontFamily)
    }

    @Test
    fun verifySmallTextSize() {
        // Click on the "Settings" button in the bottom navigation
        composeTestRule.onNodeWithText("Settings").performClick()

        // Select "Small" text size
        composeTestRule.onNodeWithText("Small").performClick()

        // Navigate back to Home
        composeTestRule.onNodeWithText("Home").performClick()

        // Verify that the article title has small font size (16sp)
        val node = composeTestRule.onAllNodesWithTag("ArticleTitle", useUnmergedTree = true)
            .onFirst()
            .fetchSemanticsNode()

        val fontSize = node.config[FontSizeKey]

        assertEquals(16.sp, fontSize)
    }

    @Test
    fun verifyMediumTextSize() {
        // Click on the "Settings" button in the bottom navigation
        composeTestRule.onNodeWithText("Settings").performClick()

        // Select "Medium" text size
        composeTestRule.onNodeWithText("Medium").performClick()

        // Navigate back to Home
        composeTestRule.onNodeWithText("Home").performClick()

        // Verify that the article title has medium font size (20sp)
        val node = composeTestRule.onAllNodesWithTag("ArticleTitle", useUnmergedTree = true)
            .onFirst()
            .fetchSemanticsNode()

        val fontSize = node.config[FontSizeKey]

        assertEquals(20.sp, fontSize)
    }

    @Test
    fun verifyLargeTextSize() {
        // Click on the "Settings" button in the bottom navigation
        composeTestRule.onNodeWithText("Settings").performClick()

        // Select "Large" text size
        composeTestRule.onNodeWithText("Large").performClick()

        // Navigate back to Home
        composeTestRule.onNodeWithText("Home").performClick()

        // Verify that the article title has large font size (24sp)
        val node = composeTestRule.onAllNodesWithTag("ArticleTitle", useUnmergedTree = true)
            .onFirst()
            .fetchSemanticsNode()

        val fontSize = node.config[FontSizeKey]

        assertEquals(24.sp, fontSize)
    }
}
