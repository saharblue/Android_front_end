package com.example.shakeshackapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import java.util.*

class OrderSeats : AppCompatActivity() {
    private lateinit var viewSelectedDate: TextView // TextView to display the selected date
    private lateinit var viewSelectedHour: TextView // TextView to display the selected time
    private lateinit var peopleSpinner: Spinner // Spinner for num of people
    private lateinit var veganSpinner: Spinner // Spinner for vegan option

    // strings to pass for the summary activity
    private var date: String = ""
    private var time: String = ""
    private var numOfPeople: String = ""
    private var veganOption: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_seats)

        // handle date selection
        viewSelectedDate = findViewById(R.id.selectedDate)
        val datePickerButton = findViewById<Button>(R.id.btnSelectDate)
        datePickerButton.setOnClickListener {
            showDatePickerDialog()
        }
        // handle time selection
        viewSelectedHour = findViewById(R.id.SelectedTime) // Initialize TextView for time
        val btnPickTime = findViewById<Button>(R.id.btnSelectTime)
        btnPickTime.setOnClickListener {
            showTimePickerDialog()
        }

        // handle people spinner choice
        peopleSpinner = findViewById(R.id.numPeopleChoose)
        setupPeopleSpinner()

        // handle vegan spinner choice
        veganSpinner = findViewById(R.id.veganChoice)
        setupVeganSpinner()

        // handle next button click
        val btnNext = findViewById<Button>(R.id.next_button)
        btnNext.setOnClickListener{
            nextButtonPop()
        }
    }

    // handle date pick function
    private fun showDatePickerDialog() {
        // Get the current date
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        // Create the DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Update the viewSelectedDate TextView with the selected date
                date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                viewSelectedDate.text = date
            },
            year,
            month,
            dayOfMonth
        )

        // Show the DatePickerDialog
        datePickerDialog.show()
    }

    // handle time pick function
    @SuppressLint("SetTextI18n")
    private fun showTimePickerDialog() {
        // Set the initial time to 12:00
        val initialHour = 12
        val initialMinute = 0

        // Create a new instance of TimePickerDialog
        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourPicked, minutePicked ->
                time = String.format("%02d:%02d", hourPicked, minutePicked)
                viewSelectedHour.text = time // Update TextView with selected time
            },
            initialHour,
            initialMinute,
            true // Use 24-hour format
        )

        // Set the initial hour and show dialog
        timePickerDialog.updateTime(initialHour, initialMinute)
        timePickerDialog.show()
    }

    // Set up the onItemSelectedListener for peopleSpinner
    private fun setupPeopleSpinner() {
        peopleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                numOfPeople = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // When no item is selected, assign the default value
                numOfPeople = "1"
            }
        }
    }

    // Set up the onItemSelectedListener for vegan spinner
    private fun setupVeganSpinner() {
        veganSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                veganOption = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // When no item is selected, assign the default value
                veganOption = "Yes"
            }
        }
    }

    // handle next button click
    private fun nextButtonPop() {
        // create alert dialog for cases date or time not selected
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        if (viewSelectedDate.text.toString() == getString(R.string.no_date_text) &&
            viewSelectedHour.text.toString() == getString(R.string.no_time_text)) {
                builder.apply { setTitle(R.string.not_selected_title)
                setMessage(R.string.both_not_selected_message)
                setPositiveButton(R.string.ok_button) { _, _ -> // do nothing
                }
                }
            val dialog = builder.create()
            dialog.show()
            }
        else if (viewSelectedDate.text.toString() == getString(R.string.no_date_text)) {
            builder.apply { setTitle(R.string.not_selected_title)
                setMessage(R.string.date_not_selected_message)
                setPositiveButton(R.string.ok_button) { _, _ -> // do nothing
                }
            }
            val dialog = builder.create()
            dialog.show()
        }
        else if (viewSelectedHour.text.toString() == getString(R.string.no_time_text)) {
            builder.apply { setTitle(R.string.not_selected_title)
                setMessage(R.string.time_not_selected_message)
                setPositiveButton(R.string.ok_button) { _, _ -> // do nothing
                }
            }
            val dialog = builder.create()
            dialog.show()
        }
        else {
            // Navigate to summary page and send the details
            val intent = Intent(this@OrderSeats, ShowOrderSummary::class.java)
                .apply { putExtra("DATE_VARIABLE", date)
                    putExtra("TIME_VARIABLE", time)
                    putExtra("NUM_OF_PEOPLE", numOfPeople)
                    putExtra("VEGAN_CHOICE", veganOption)
            }
            startActivity(intent)
        }
    }
    }

