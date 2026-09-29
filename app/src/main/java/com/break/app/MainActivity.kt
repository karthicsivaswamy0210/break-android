package com.break.app

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : ComponentActivity() {

    private val notificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val store = BreakStore(this)

        val goalInput = EditText(this).apply {
            hint = "Goal in minutes"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
            setText(store.goalMinutes.toString())
        }

        val saveButton = Button(this).apply {
            text = "Save break goal"
            setOnClickListener {
                val value = goalInput.text.toString().toIntOrNull()
                if (value != null && value > 0) {
                    store.goalMinutes = value
                    status.text = "Goal saved: ${value} minutes"
                }
            }
        }

        val status = TextView(this).apply {
            text = "Lock your phone and stay away.\n\nGoal: ${store.goalMinutes} minutes"
            textSize = 18f
            setPadding(40, 40, 40, 40)
        }

        val best = TextView(this).apply {
            text = "Personal best: ${store.bestSeconds / 60}m ${store.bestSeconds % 60}s"
            textSize = 16f
            setPadding(40, 20, 40, 40)
        }

        val layout = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(32, 80, 32, 32)
            addView(TextView(this@MainActivity).apply {
                text = "BREAK"
                textSize = 42f
            })
            addView(TextView(this@MainActivity).apply {
                text = "Beat your break."
                textSize = 20f
                setPadding(0, 0, 0, 40)
            })
            addView(status)
            addView(goalInput)
            addView(saveButton)
            addView(best)
        }

        setContentView(layout)

        if (Build.VERSION.SDK_INT >= 33) {
            notificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
