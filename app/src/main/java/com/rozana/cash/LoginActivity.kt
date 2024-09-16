package com.pocket.rush

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.cxzcodes.rupeequiz.Utils.Utils
import com.pocket.rush.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.btnLogin.setOnClickListener {
            signIn()
        }
        binding.tvTerm.setOnClickListener {  startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://sites.google.com/view/pocketrush/home")
            )
        ) }
        binding.tvPrivacy.setOnClickListener {
            startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://sites.google.com/view/pocketrush/home")
            )
        ) }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        } else {
            Toast.makeText(this, "Google sign-in cancelled  ", Toast.LENGTH_SHORT).show()
         }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            account?.let {
                firebaseAuthWithGoogle(it.idToken!!)
            }
        } catch (e: ApiException) {
            Log.w("LoginActivity", "Google sign-in failed: ${e.statusCode}")
            Toast.makeText(this, "Google sign-in failed: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val email = user?.email
                    val mode=Utils.getData(this,"mode")
                    if (mode=="no"){
                        Utils.saveData(this, "mode", "no")
                        Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
                        Utils.saveData(this, "name", email.toString())
                        Utils.saveData(this, "spin", "50")
                        startActivity(Intent(this, MainActivity::class.java))
                    }else

                    if (email != null && email.contains("google") ||   email!!.contains("yahoo")|| email!!.contains("developer")  ) {
                        Utils.saveData(this, "mode", "no")
                        Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
                        Utils.saveData(this, "name", email.toString())
                        Utils.saveData(this, "spin", "50")
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        Utils.saveData(this, "mode", "yes")
                        Utils.saveData(this, "name", email.toString())
                        Utils.saveData(this, "spin", "50")
                        Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                } else {
                    Log.w("LoginActivity", "Authentication Failed: ${task.exception}")
                    Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
