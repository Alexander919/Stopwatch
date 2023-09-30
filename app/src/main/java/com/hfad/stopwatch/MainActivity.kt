package com.hfad.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import com.hfad.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var running = false
    var offset: Long = 0

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onPause() {
        super.onPause()
        if(running) {
            saveOffset()
            binding.stopwatch.stop()
        }
    }

    override fun onResume() {
        super.onResume()
        if(running) {
            setBaseTime()
            binding.stopwatch.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("running", running)
        outState.putLong("offset", offset)
        outState.putLong("base", binding.stopwatch.base)
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        if(savedInstanceState != null) {
            running = savedInstanceState.getBoolean("running")
            offset = savedInstanceState.getLong("offset")

            if(running) {
                binding.stopwatch.base = savedInstanceState.getLong("base")
                binding.stopwatch.start()
            } else {
                setBaseTime()
            }
        }

        binding.startButton.setOnClickListener {
            if(!running) {
                binding.stopwatch.start()
                running = true
                setBaseTime()
            }
        }

        binding.pauseButton.setOnClickListener {
            if(running) {
                running = false
                binding.stopwatch.stop()
                saveOffset()
            }
        }

        binding.resetButton.setOnClickListener {
            offset = 0
            setBaseTime()
        }

    }

    fun setBaseTime() {
        binding.stopwatch.base = SystemClock.elapsedRealtime() - offset
    }

    fun saveOffset() {
        offset = SystemClock.elapsedRealtime() - binding.stopwatch.base
    }

}