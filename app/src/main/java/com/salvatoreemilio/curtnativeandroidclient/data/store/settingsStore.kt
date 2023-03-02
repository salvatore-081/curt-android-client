package com.salvatoreemilio.curtnativeandroidclient.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settingsStore")
        private val HOST = stringPreferencesKey("host")
        private val KEY = stringPreferencesKey("key")
    }

    val getHost: Flow<String> =
        context.dataStore.data.map { preferences -> preferences[HOST] ?: "" }

    suspend fun saveHost(host: String) {
        context.dataStore.edit { preferences -> preferences[HOST] = host }
    }

    val getKey: Flow<String> = context.dataStore.data.map { value: Preferences -> value[KEY] ?: "" }

    suspend fun saveKey(key: String) {
        context.dataStore.edit { preferences -> preferences[KEY] = key }
    }
}