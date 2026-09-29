package com.break.app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock

class ScreenStateReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val store = BreakStore(context)

        when (intent.action) {
            Intent.ACTION_SCREEN_OFF -> {
                if (store.breakStartElapsed == 0L) {
                    store.breakStartElapsed = SystemClock.elapsedRealtime()
                }
            }

            Intent.ACTION_SCREEN_ON -> {
                val start = store.breakStartElapsed
                if (start == 0L) return

                val elapsed = (SystemClock.elapsedRealtime() - start) / 1000L
                store.breakStartElapsed = 0L

                val goal = store.goalMinutes * 60L

                when {
                    elapsed >= goal && elapsed > store.bestSeconds -> {
                        store.bestSeconds = elapsed
                        BreakNotification.show(
                            context,
                            "🏆 NEW RECORD!",
                            "You were away for ${format(elapsed)}. Beat your last break!"
                        )
                    }

                    elapsed >= goal -> {
                        BreakNotification.show(
                            context,
                            "👏 WELL DONE!",
                            "You were away for ${format(elapsed)}."
                        )
                    }

                    elapsed >= goal / 2 -> {
                        BreakNotification.show(
                            context,
                            "🙂 Nice little break.",
                            "You were away for ${format(elapsed)}."
                        )
                    }

                    else -> {
                        BreakNotification.show(
                            context,
                            "🥲 Already back?",
                            "You were away for ${format(elapsed)}."
                        )
                    }
                }
            }
        }
    }

    private fun format(seconds: Long): String {
        val minutes = seconds / 60
        val remaining = seconds % 60
        return if (minutes > 0) "${minutes}m ${remaining}s" else "${remaining}s"
    }
}
