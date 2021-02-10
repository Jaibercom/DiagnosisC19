package co.covid19.diagnosis.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 *
 * Created by Jaiber Yepes
 */
object PreferenceHelper {

    const val PREFERENCE_FILE = "preference-file"
    private const val PRIVACY_KEY = "privacy-key"

    fun customPreference(context: Context, name: String): SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    var SharedPreferences.privacy
        get() = getBoolean(PRIVACY_KEY, false)
        set(value) {
            edit {
                it.putBoolean(PRIVACY_KEY, value)
            }
        }

    var SharedPreferences.clearValues
        get() = run { }
        set(value) {
            edit {
                it.clear()
            }
        }
}

fun SharedPreferences.Editor.put(pair: Pair<String, Any>) {
    val key = pair.first
    val value = pair.second
    when (value) {
        is String -> putString(key, value)
        is Int -> putInt(key, value)
        is Boolean -> putBoolean(key, value)
        is Long -> putLong(key, value)
        is Float -> putFloat(key, value)
        else -> error("Only primitive types can be stored in SharedPreferences")
    }
}
