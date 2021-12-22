package com.fvaldiviesok.accellmobileapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fvaldiviesok.accellmobileapp.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity: AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignInBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize Firebase Auth
        auth = Firebase.auth

        //Boton para navegar devuelta al home page
        val homeBtn = findViewById<ImageView>(R.id.signInHomeBtn)
        val signInBtn = findViewById<Button>(R.id.signInBtn)
        val regis = findViewById<Button>(R.id.registerBtn)

        val emailText = findViewById<TextView>(R.id.singInUserNameTxt)
        val passText = findViewById<TextView>(R.id.signInPasswordTxt)

        homeBtn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        signInBtn.setOnClickListener {
            val Email = emailText.text.toString()
            val Password = passText.text.toString()
            when{
                Email.isEmpty() || Password.isEmpty() ->{
                    Toast.makeText(baseContext, "Correo o Contrasena Incorrectos",
                        Toast.LENGTH_SHORT).show()
                }else -> {
                signIn(Email, Password)
                }
            }
        }
        regis.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
    }

    private fun signIn(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithEmail:success")
                    reload()
                    Toast.makeText(this, "Ingreso como $email", Toast.LENGTH_LONG).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun reload(){
        val intent = Intent(this, HomeActivity::class.java)
        this.startActivity(intent)
    }
}