package com.example.shakeshackapp

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class ShowOrderSummary : AppCompatActivity() {

    // Views variables that are changed after completing the order
    private lateinit var logoView: ImageView
    private lateinit var summaryOrderButton: Button
    private lateinit var goBackButton: Button
    private lateinit var linearLayout: ViewGroup
    private lateinit var cardView: View
    private lateinit var title: TextView
    private lateinit var goHomeButton: Button

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_order_summary)

        // initialize variables from order seats activity
        val date = intent.getStringExtra("DATE_VARIABLE")
        val time = intent.getStringExtra("TIME_VARIABLE")
        val numOfPeople = intent.getStringExtra("NUM_OF_PEOPLE")
        val veganOption = intent.getStringExtra("VEGAN_CHOICE")

        // initialize TextViews and home button
        val tableFor: TextView = findViewById(R.id.summary_table_for)
        val dateTimeView: TextView = findViewById(R.id.summary_date_time)
        val veganView: TextView = findViewById(R.id.summary_vegan_choice)
        goHomeButton = findViewById(R.id.go_home_button)

        // add order details to text
        tableFor.text = "${getString(R.string.summary_table_for)} $numOfPeople"
        dateTimeView.text = "$date ${getString(R.string.summary_date_time)} $time"
        veganView.text = "${getString(R.string.summary_vegan)} $veganOption"

        // handle back button push
        goBackButton = findViewById(R.id.go_back_button)
        goBackButton.setOnClickListener{
            finish()
        }

        // handle complete button
        summaryOrderButton = findViewById(R.id.summary_order_button)
        summaryOrderButton.setOnClickListener{
            makeOrderClick()
        }

        // initialize all views
        logoView = findViewById(R.id.imageView8)
        goBackButton = findViewById(R.id.go_back_button)
        linearLayout = findViewById(R.id.linearLayout1)
        cardView = findViewById(R.id.summary_layout)
        title = findViewById(R.id.summary_title)
    }

    // handle make order button click
    private fun makeOrderClick(){
        // change title text
        title.text = getString(R.string.summary_title_after)

        // Create an animation for the ImageView
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.zoom_in)
        logoView.startAnimation(animation)

        // Animate CardView background color
        val startColor = Color.parseColor("#86FFE4E4")
        val endColor = Color.parseColor("#9793E633")
        val colorAnimation = ValueAnimator.ofArgb(startColor, endColor)
        colorAnimation.duration = 4000 // duration in milliseconds
        colorAnimation.addUpdateListener { animator ->
            val currentColor = animator.animatedValue as Int
            cardView.setBackgroundColor(currentColor)
        }
        colorAnimation.start()

        // Remove buttons from the LinearLayout
        linearLayout.removeView(summaryOrderButton)
        linearLayout.removeView(goBackButton)

        // show home button
        goHomeButton.visibility = View.VISIBLE
        goHomeButton.setOnClickListener{
            val intent = Intent(this@ShowOrderSummary, MainActivity::class.java)
            startActivity(intent)
        }
    }
}