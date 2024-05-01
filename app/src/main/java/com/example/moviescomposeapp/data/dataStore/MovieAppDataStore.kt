package com.example.moviescomposeapp.data.dataStore


import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


const val PREFERENCES_NAME = "is_first_time"
val Context.dataStore: androidx.datastore.core.DataStore<Preferences> by preferencesDataStore(name = "settings")

class MovieAppDataStore @Inject constructor(context: Context) {

    private val onBoardingScreenKey = booleanPreferencesKey(name = PREFERENCES_NAME)

    private val dataStore = context.dataStore

    suspend fun saveOnBoardingState(showTipsPage: Boolean) {
        dataStore.edit { preferences ->
            preferences[onBoardingScreenKey] = showTipsPage
        }
    }

    fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.data
            .map { preferences ->
                preferences[onBoardingScreenKey] ?: false
            }
    }
}