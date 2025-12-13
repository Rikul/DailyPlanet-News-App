package com.example.dailyplanet.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

enum class TextSize {
    Small, Medium, Large
}

enum class AppFont {
    Default, Serif, Monospace
}

enum class ViewType {
    HeadlinesOnly, Tile
}

class SettingsRepository(private val context: Context) {

    companion object {
        val TEXT_SIZE_KEY = stringPreferencesKey("text_size")
        val FONT_KEY = stringPreferencesKey("font")
        val VIEW_TYPE_KEY = stringPreferencesKey("view_type")
    }

    val textSize: Flow<TextSize> = context.dataStore.data
        .map { preferences ->
            val value = preferences[TEXT_SIZE_KEY] ?: TextSize.Medium.name
            TextSize.valueOf(value)
        }

    val font: Flow<AppFont> = context.dataStore.data
        .map { preferences ->
            val value = preferences[FONT_KEY] ?: AppFont.Default.name
            AppFont.valueOf(value)
        }

    val viewType: Flow<ViewType> = context.dataStore.data
        .map { preferences ->
            val value = preferences[VIEW_TYPE_KEY] ?: ViewType.Tile.name
            ViewType.valueOf(value)
        }

    suspend fun setTextSize(textSize: TextSize) {
        context.dataStore.edit { preferences ->
            preferences[TEXT_SIZE_KEY] = textSize.name
        }
    }

    suspend fun setFont(font: AppFont) {
        context.dataStore.edit { preferences ->
            preferences[FONT_KEY] = font.name
        }
    }

    suspend fun setViewType(viewType: ViewType) {
        context.dataStore.edit { preferences ->
            preferences[VIEW_TYPE_KEY] = viewType.name
        }
    }
}
