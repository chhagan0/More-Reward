package com.cxzcodes.newappspin

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.cxzcodes.newappspin.databinding.ActivityLoginBinding
import com.cxzcodes.rupeequiz.Utils.Utils

class LoginActivity : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Account Creating...")
        progressDialog.setCancelable(false)
        binding.btnLogin.setOnClickListener {
            if (binding.etEmail.text.isEmpty()){
                Toast.makeText(this, "Enter your Name", Toast.LENGTH_SHORT).show()
            }else if (binding.etPassword.text.isEmpty()){
                binding.etPassword.error = "Invalid Phone"
                return@setOnClickListener
                Toast.makeText(this, "Enter your Phone number", Toast.LENGTH_SHORT).show()
            }else{
                progressDialog.show()
                Utils.saveData(this, "name", binding.etEmail.text.toString())
                Utils.saveData(this, "number", binding.etPassword.text.toString())
                Utils.saveData(this, "spin", "50")

                val handler = Handler()
                handler.postDelayed(Runnable {
                    progressDialog.dismiss()
                    Toast.makeText(this, "Account Createda Successfully!!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                }, 3000)
            }
        }
    }

    override fun onBackPressed() {
        finishAffinity()
        super.onBackPressed()
    }
}