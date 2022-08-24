package com.edo.githubusernavapi

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.prefDatastore by preferencesDataStore("setting")

class Preferences constructor(context: Context) {

    private var settingDataStore = context.prefDatastore
    private val modeKey = booleanPreferencesKey("mode_setting")

    fun getModeSetting(): Flow<Boolean> = settingDataStore.data.map { preferences ->
        preferences[modeKey] ?: false
    }

    suspend fun saveModeSetting(isDarkModeActive: Boolean) {
        settingDataStore.edit { preferences -> preferences[modeKey] = isDarkModeActive }
    }
}