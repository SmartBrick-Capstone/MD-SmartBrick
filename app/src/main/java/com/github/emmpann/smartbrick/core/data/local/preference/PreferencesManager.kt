package com.github.emmpann.smartbrick.core.data.local.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.github.emmpann.smartbrick.core.data.remote.response.LoginResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    fun getToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY] ?: ""
        }
    }

    fun getName(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[NAME] ?: ""
        }
    }

    fun getEmail(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[EMAIL] ?: ""
        }
    }

    fun getUserId(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[USER_ID] ?: ""
        }
    }

    fun getUserFirstTime(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[USER_FIRST_TIME] ?: true
        }
    }

    suspend fun setUserFirstTime(userFirstTime: Boolean) {
        dataStore.edit{ preferences ->
            preferences[USER_FIRST_TIME] = userFirstTime
        }
    }

    suspend fun setSession(user: LoginResult) {
        dataStore.edit { preferences ->
            preferences[USER_ID] = user.id
            preferences[TOKEN_KEY] = user.token
            preferences[NAME] = user.name
            preferences[EMAIL] = user.email
//            preferences[IS_VERIFIED] = user
        }
    }

    suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences[USER_ID] = ""
            preferences[TOKEN_KEY] = ""
            preferences[NAME] = ""
            preferences[EMAIL] = ""
        }
    }

    companion object {
        private val USER_ID = stringPreferencesKey("user_id")
        private val NAME = stringPreferencesKey("name")
        private val TOKEN_KEY = stringPreferencesKey("token_key")
        private val EMAIL = stringPreferencesKey("email_key")
        private val USER_FIRST_TIME = booleanPreferencesKey("user_first_time")
        private val IS_VERIFIED = booleanPreferencesKey("is_verified")
    }
}