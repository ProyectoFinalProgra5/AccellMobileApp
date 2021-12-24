package com.fvaldiviesok.accellmobileapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.fvaldiviesok.accellmobileapp.databinding.ActivityHomeBinding
import com.fvaldiviesok.accellmobileapp.model.TourModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        // Initialize Firebase Auth
        auth = Firebase.auth

        //Boton de home
        val homeBtn = findViewById<ImageView>(R.id.homeBtn)

        homeBtn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        //Boton de home
        val weatherNtm = findViewById<ImageView>(R.id.WeatherBtn)

        weatherNtm.setOnClickListener {
            val intent = Intent(this, WeatherActivity::class.java)
            startActivity(intent)
        }

        //Boton de log out
        val logOut = findViewById<ImageView>(R.id.SalirBtn)

        logOut.setOnClickListener {
            signOut()
        }

        val toursCerca = findViewById<ImageView>(R.id.searchTourBtn)

        toursCerca.setOnClickListener{
            val intent = Intent(this, AddTourActivity::class.java)
            startActivity(intent)
        }

        //Boton para navegar al activity de agregar tour
        val addTourBtn = findViewById<Button>(R.id.AddTourBtn)

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

    private fun signOut(){
        Firebase.auth.signOut()
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }
    

}