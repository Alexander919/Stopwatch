package com.hfad.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer

class MainActivity : AppCompatActivity() {
    lateinit var stopwatch: Chronometer
    var running = false
    var offset: Long = 0

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("running", running)
        outState.putLong("offset", offset)
        super.onSaveInstanceState(outState)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Get a reference to the stopwatch
        stopwatch = findViewById<Chronometer>(R.id.stopwatch)


        if(savedInstanceState != null) {
            running = savedInstanceState.getBoolean("running")
            offset = savedInstanceState.getLong("offset")
            setBaseTime()
            if(running) {
                stopwatch.start()
            }
        }

        val startButton = findViewById<Button>(R.id.start_button)
        val pauseButton = findViewById<Button>(R.id.pause_button)
        val resetButton = findViewById<Button>(R.id.reset_button)

        startButton.setOnClickListener {
            if(!running) {
                stopwatch.start()
                running = true
                setBaseTime()
            }
        }

        pauseButton.setOnClickListener {
            if(running) {
                running = false
                stopwatch.stop()
                saveOffset()
            }
        }

        resetButton.setOnClickListener {
            offset = 0
            setBaseTime()
        }

    }

    fun setBaseTime() {
        stopwatch.base = SystemClock.elapsedRealtime() - offset
    }

    fun saveOffset() {
        offset = SystemClock.elapsedRealtime() - stopwatch.base
    }

}