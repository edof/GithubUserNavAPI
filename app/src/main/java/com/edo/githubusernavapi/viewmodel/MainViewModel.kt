package com.edo.githubusernavapi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.edo.githubusernavapi.Preferences

class MainViewModel(private val preferences: Preferences) : ViewModel() {

    fun getMode() = preferences.getModeSetting().asLiveData()
    class Factory(private val preferences: Preferences): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(preferences) as T
    }
}