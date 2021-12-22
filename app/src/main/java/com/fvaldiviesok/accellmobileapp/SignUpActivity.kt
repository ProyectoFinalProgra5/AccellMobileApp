package com.fvaldiviesok.accellmobileapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.fvaldiviesok.accellmobileapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class SignUpActivity: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize Firebase Auth
        auth = Firebase.auth

        val emailText = findViewById<TextView>(R.id.SUemailTxt)
        val passText = findViewById<TextView>(R.id.SUpasswordTxt)
        val passVal = findViewById<TextView>(R.id.SUconfirmPasswordTxt)


        binding.signUpBtn.setOnClickListener{
            val Email = emailText.text.toString()
            val Pass = passText.text.toString()
            val passRegex = Pattern.compile("^" +
                    "(?=.*[-@#$%^&+=])" + //al menos un caracter especial
                    ".{6,}" + // al menos 6 caracteres
                    "$")
            val passVali = passVal.text.toString()

            if (Email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                Toast.makeText(baseContext, "Ingrese un Email",
                    Toast.LENGTH_SHORT).show()
            }else if (Pass.isEmpty() || !passRegex.matcher(Pass).matches()){
                Toast.makeText(baseContext, "Ingrese una Contrasena valida",
                    Toast.LENGTH_SHORT).show()
            }else if (Pass != passVali ){
                Toast.makeText(baseContext, "Contrasena no coincide",
                    Toast.LENGTH_SHORT).show()
            }else{
                createAccount(Email, Pass)
                Toast.makeText(baseContext, "Usuario creado con exito $Email",
                    Toast.LENGTH_LONG).show()
                emailText.text = ""
                passText.text = ""
                passVal.text = ""
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        }



        val signUpBtn = findViewById<Button>(R.id.signUpBtn)

        //Boton para navegar devuelta al home page
        val homeBtn = findViewById<ImageView>(R.id.signUpHomeBtn)

        homeBtn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
    private fun createAccount(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}