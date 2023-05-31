package com.example.shakeshackapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnMenu = findViewById<Button>(R.id.menubutton)
        val btnOrderSeats = findViewById<Button>(R.id.seatsbutton)

        // Set OnClickListener on the Home Button
        btnMenu.setOnClickListener {
            // Navigate to Menu page
            val intent = Intent(this@MainActivity, Menu::class.java)
            startActivity(intent)
        }

        // Set OnClickListener on the Seats Button
        btnOrderSeats.setOnClickListener {
            // Navigate to order seats page
            val intent = Intent(this@MainActivity, OrderSeats::class.java)
            startActivity(intent)
        }
    }

    }


