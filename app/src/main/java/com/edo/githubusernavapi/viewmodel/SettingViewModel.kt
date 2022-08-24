package com.edo.githubusernavapi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.edo.githubusernavapi.Preferences
import kotlinx.coroutines.launch

class SettingViewModel(private val preferences: Preferences): ViewModel() {
    class Factory(private val preferences: Preferences): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T = SettingViewModel(preferences) as T
    }

    fun getMode()= preferences.getModeSetting().asLiveData()

    fun saveMode(isDarkMode: Boolean) {
        viewModelScope.launch {
            preferences.saveModeSetting(isDarkMode)
        }
    }
}