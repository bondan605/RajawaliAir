package com.rajawali.core.data.local.dataStorePreference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.rajawali.core.domain.model.IsLoginModel
import com.rajawali.core.util.Constant.USER_PREF
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(USER_PREF)

class DataStore(private val dataStore: DataStore<Preferences>) {
    private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token_key")
    private val IS_LOGIN_KEY = stringPreferencesKey("is_login_key")

    suspend fun isAccessToken(): String = dataStore.data.first()[ACCESS_TOKEN_KEY] ?: ""


    suspend fun isLogin(): String = dataStore.data.first()[IS_LOGIN_KEY] ?: ""

    fun createLoginSession(accessToken: String, id: String): Flow<Boolean> = flow {
        try {
            dataStore.edit { preference ->
                preference[IS_LOGIN_KEY] = id
                preference[ACCESS_TOKEN_KEY] = accessToken
            }

            if (isLogin().isNotEmpty())
                emit(true)
            else
                throw Exception()
        } catch (e: Exception) {
            Timber.w(e)
            emit(false)
        }
    }

    fun logout(): Flow<Boolean> = flow {
        try {
            dataStore.edit { preference ->
                preference[IS_LOGIN_KEY] = ""
                preference[ACCESS_TOKEN_KEY] = ""
            }

            if (isLogin().isEmpty())
                emit(true)
            else
                throw Exception()
        } catch (e: Exception) {
            Timber.w(e)
            emit(false)
        }
    }
}