package kr.hs.dgsw.projectpractice

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE

class Prefs(context: Context) {
    private val prefNm = "mPref"
    private val prefs = context.getSharedPreferences(prefNm, MODE_PRIVATE)

    var title: String?
        get() = prefs.getString("title", null)
        set(value) {
            prefs.edit().putString("title", value).apply()
        }
    var content: String?
        get() = prefs.getString("content", null)
        set(value) {
            prefs.edit().putString("content", value).apply()
        }
    var category: Int
        get() = prefs.getInt("category", 0)
        set(value) {
            prefs.edit().putInt("category", value).apply()
        }
}

class App : Application() {
    companion object {
        lateinit var prefs: Prefs
    }

    override fun onCreate() {
        prefs = Prefs(applicationContext)
        super.onCreate()
    }
}