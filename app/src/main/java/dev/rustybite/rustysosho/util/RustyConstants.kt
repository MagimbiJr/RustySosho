package dev.rustybite.rustysosho.util

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.JsonObject
import dev.rustybite.rustysosho.BuildConfig

object RustyConstants {
    const val TAG = "RUSTY TAG"
    const val RUSTY_SOSHO_BASE_URL = "https://yndbirwnmipkoasmlwdn.supabase.co/"
    const val API_KEY = BuildConfig.apiKey
    const val PREFERENCES_NAME = "Rusty Sosho"
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    val IS_TOKEN_EXPIRE = booleanPreferencesKey("is_token_expire")
}