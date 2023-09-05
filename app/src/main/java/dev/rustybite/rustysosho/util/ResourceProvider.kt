package dev.rustybite.rustysosho.util

import android.content.Context
import javax.inject.Inject

class ResourceProvider @Inject constructor(
    private val context: Context
) {
    fun getStringResource(id: Int): String =
        context.resources.getString(id)
}