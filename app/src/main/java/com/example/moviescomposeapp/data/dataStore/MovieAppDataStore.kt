package com.example.moviescomposeapp.data.dataStore


import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject


const val PREFERENCES_NAME = "is_first_time"

val Context.dataStore: androidx.datastore.core.DataStore<Preferences> by preferencesDataStore(name = "settings")

class MovieAppDataStore @Inject constructor(context: Context) {

    private val onBoardingScreenKey = booleanPreferencesKey(name = PREFERENCES_NAME)
    val LATEST_FETCHED_PAGE = intPreferencesKey("latest_fetched_page")

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

    suspend fun saveLatestPage(page: Int) {
        dataStore.edit { preferences ->
            preferences[LATEST_FETCHED_PAGE] = page
        }
    }

    suspend fun readLatestPage(): Int{
        return dataStore.data.first()[LATEST_FETCHED_PAGE] ?: -1

    }
}