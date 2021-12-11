package com.fvaldiviesok.accellmobileapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Boton de home
        val homeBtn = findViewById<ImageView>(R.id.homeBtn)

        homeBtn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        //Boton para navegar al activity de agregar tour
        val addTourBtn = findViewById<Button>(R.id.goToAddTourBtn)

        addTourBtn.setOnClickListener {
            val intent = Intent(this, AddTourActivity::class.java)
            startActivity(intent)
        }

        //hyperlink para navegar al activity de sign in
        val homeSignInBtn = findViewById<TextView>(R.id.homeSignInBtn)

        homeSignInBtn.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        //hyperlink para navegar al activity de sign up
        val homeSignUpBtn = findViewById<TextView>(R.id.homeSignUpBtn)

        homeSignUpBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}