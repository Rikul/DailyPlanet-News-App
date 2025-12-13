package com.example.dailyplanet.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyplanet.repository.AppFont
import com.example.dailyplanet.repository.SettingsRepository
import com.example.dailyplanet.repository.TextSize
import com.example.dailyplanet.repository.ViewType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = SettingsRepository(application)

    val textSize: StateFlow<TextSize> = repository.textSize
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TextSize.Medium)

    val font: StateFlow<AppFont> = repository.font
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppFont.Default)

    val viewType: StateFlow<ViewType> = repository.viewType
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ViewType.Tile)

    fun setTextSize(textSize: TextSize) {
        viewModelScope.launch {
            repository.setTextSize(textSize)
        }
    }

    fun setFont(font: AppFont) {
        viewModelScope.launch {
            repository.setFont(font)
        }
    }

    fun setViewType(viewType: ViewType) {
        viewModelScope.launch {
            repository.setViewType(viewType)
        }
    }
}
