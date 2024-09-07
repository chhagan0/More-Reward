package com.cxzcodes.newappspin

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import com.cxzcodes.newappspin.databinding.ActivitySplashScreenBinding
import com.cxzcodes.rupeequiz.Utils.Utils

class SplashScreen : AppCompatActivity() {
    lateinit var binding:ActivitySplashScreenBinding
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressBar = findViewById(R.id.progressBar)

        startProgressAnimation()
        checkapp()
    }
    private fun startProgressAnimation() {
        val animator = ObjectAnimator.ofInt(progressBar, "progress", 0, 100)
        animator.duration = 2000 // 3 seconds
        animator.start()
    }
    fun checkapp(){
        val handler = android.os.Handler()
        val name = Utils.getData(this, "name")
        if (name != null) {
            handler.postDelayed(Runnable {
                startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    )
                )
            }, 2000)

        } else {
            handler.postDelayed(Runnable {
                startActivity(
                    Intent(
                        this,
                        LoginActivity::class.java
                    )
                )
            }, 2000)

        }
    }



}