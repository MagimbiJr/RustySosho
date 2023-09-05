package dev.rustybite.rustysosho.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.auth0.android.jwt.JWT
import dev.rustybite.rustysosho.util.RustyConstants.ACCESS_TOKEN
import dev.rustybite.rustysosho.util.RustyConstants.IS_TOKEN_EXPIRE
import dev.rustybite.rustysosho.util.RustyConstants.PREFERENCES_NAME
import dev.rustybite.rustysosho.util.RustyConstants.REFRESH_TOKEN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferenceManager @Inject constructor(
    private val context: Context
) {
    val message = MutableStateFlow<String?>(null)
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREFERENCES_NAME)

    suspend fun storeAccessToken(accessToken: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = accessToken
        }
    }
    suspend fun storeRefreshToken(refreshToken: String) {
        context.dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN] = refreshToken
        }
    }

    suspend fun storeIsTokenExpired(accessToken: String, expiredAt: Long) {
        val jwt = JWT(accessToken)
        val isExpired = jwt.isExpired(expiredAt)
        context.dataStore.edit { preferences ->
            preferences[IS_TOKEN_EXPIRE] = isExpired
        }
    }

    val getAccessToken: Flow<String> = context.dataStore.data.map { preferences ->
        val accessToken = preferences[ACCESS_TOKEN] ?: ""
        accessToken
    }

    val getRefreshToken: Flow<String> = context.dataStore.data.map { preferences ->
        val refreshToken = preferences[REFRESH_TOKEN] ?: ""
        refreshToken
    }

    val isTokenExpired: Flow<Boolean> = context.dataStore.data.map { preferences ->
        val isExpired = preferences[IS_TOKEN_EXPIRE] ?: false
        isExpired
    }
}