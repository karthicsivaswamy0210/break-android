package com.breakapp

import android.content.Context

class BreakStore(context: Context) {
    private val prefs = context.getSharedPreferences("break_store", Context.MODE_PRIVATE)

    var goalMinutes: Int
        get() = prefs.getInt("goal_minutes", 30)
        set(value) = prefs.edit().putInt("goal_minutes", value).apply()

    var breakStartElapsed: Long
        get() = prefs.getLong("break_start_elapsed", 0L)
        set(value) = prefs.edit().putLong("break_start_elapsed", value).apply()

    var bestSeconds: Long
        get() = prefs.getLong("best_seconds", 0L)
        set(value) = prefs.edit().putLong("best_seconds", value).apply()
}
