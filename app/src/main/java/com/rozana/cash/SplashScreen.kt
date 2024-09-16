package com.pocket.rush

import android.animation.ObjectAnimator
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import com.pocket.rush.databinding.ActivitySplashScreenBinding
import com.cxzcodes.rupeequiz.Utils.Utils
import com.pocket.rush.MainActivity

class SplashScreen : AppCompatActivity() {
    lateinit var binding:ActivitySplashScreenBinding
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressBar = findViewById(R.id.progressBar)

        startProgressAnimation()
        val mode=Utils.getData(this,"mode")
        if (mode=="no"){
            checkapp()
        }else{
            checkInstalledApps()

        }
    }
    private fun startProgressAnimation() {
        val animator = ObjectAnimator.ofInt(progressBar, "progress", 0, 100)
        animator.duration = 2000
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

    fun checkInstalledApps() {
         val packageNames = listOf(
            "com.google.android.apps.nbu.paisa.user",
            "com.phonepe.app",
            "net.one97.paytm",
            "com.paytmmall",
            "in.org.npci.upiapp",
            "in.amazon.mShop.android.shopping",
            "com.csam.icici.bank.imobile",
            "com.sbi.upi",
            "com.myairtelapp",
            "com.icicibank.pockets"
        )


        val pm: PackageManager = this.packageManager
        var appFound = false

         for (packageName in packageNames) {
            try {
                pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
                 appFound = true
                break
            } catch (e: PackageManager.NameNotFoundException) {
             }
        }

         if (appFound) {
             Utils.saveData(this,"mode","yes")
                     checkapp()
          } else {

             Utils.saveData(this,"mode","no")
                 checkapp()

         }
    }

}