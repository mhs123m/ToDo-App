package com.twq.todoapp

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.twq.todoapp.Fragments.FragmentAdapter
import com.twq.todoapp.Model.ToDo
import java.sql.Time
import java.util.*
import java.util.zip.Inflater

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var viewPager2 = findViewById<ViewPager2>(R.id.mViewPager2)
        var mTabLayout = findViewById<TabLayout>(R.id.mTabLayout)

        var fabBtn = findViewById<FloatingActionButton>(R.id.mfloatingActionButton)


        viewPager2.adapter = FragmentAdapter(this)




        var titles = arrayOf("Daily Tasks", "Weakly Tasks")

        TabLayoutMediator(mTabLayout, viewPager2) { tab, position ->
            tab.text = titles[position]
        }.attach()

        fabBtn.setOnClickListener {
            var customAddDialog = AlertDialog.Builder(this).create()

            var view = layoutInflater.inflate(R.layout.custom_dialog_add_task, null)

            customAddDialog.setView(view)
            var editTextTaskTitle =
                view.findViewById<TextInputEditText>(R.id.editTextInputTaskTitle)
            var editTextTaskDescription =
                view.findViewById<TextInputEditText>(R.id.editTextInputDescriptionAddDialog)
//            var imgViewCalender = view.findViewById<ImageView>(R.id.imageViewAddDialogCalanderIcon)
//            var imgViewClock = view.findViewById<ImageView>(R.id.imageViewAddDialogClockIcon)
//            var imgViewLocation = view.findViewById<ImageView>(R.id.imageViewAddDialogLocationIcon)
//            var imgViewFolder = view.findViewById<ImageView>(R.id.imageViewAddDialogFolderIcon)
            var editTextDatePicker =
                view.findViewById<TextInputEditText>(R.id.editTextInputDatePicked)
            var editTextTimePicker =
                view.findViewById<TextInputEditText>(R.id.editTextInputTimePicked)
            var spinnerRepeat = view.findViewById<Spinner>(R.id.spinnerRepeatAddDialog)
            var btnAddDialog = view.findViewById<Button>(R.id.buttonAddDialogAddTask)

            // spinner items
            var list = arrayOf("No Repeat", "Daily", "Weekly", "Monthly", "Yearly")
            spinnerRepeat.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)

            //build the calender dialog

            //date dialog
            var c = Calendar.getInstance()
            var day = c.get(Calendar.DAY_OF_MONTH)
            var month = c.get(Calendar.MONTH)
            var year = c.get(Calendar.YEAR)


            editTextDatePicker.setOnClickListener {


                var datePickerDialog = DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        editTextDatePicker.setText("$dayOfMonth/${month + 1}/$year")

                    },
                    year,
                    month,
                    day
                )
                datePickerDialog.show()
            }
            // time picker dialog
            var hour = c.get(Calendar.HOUR_OF_DAY)
            var minute = c.get(Calendar.MINUTE)
            editTextTimePicker.setOnClickListener {
                var timePickerDialog = TimePickerDialog(this,
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        editTextTimePicker.setText("$hourOfDay:$minute")
                    },
                    hour,
                    minute,
                    true
                )
                timePickerDialog.show()
            }

            val db = Firebase.firestore
            val auth = Firebase.auth
            // set on click listener for the add btn in dialog to add edit texts info to firebase
            btnAddDialog.setOnClickListener {

                println("Add is pressed")
                // Create a new user with a first and last name
                val todo = hashMapOf(
                    "name" to editTextTaskTitle.text.toString(),
                    "description" to editTextTaskDescription.text.toString(),
                    "dueDate" to Date(year, month, day).toString(),
                    "time" to Time(hour, minute, 0).toString(),
                    "status" to false

                )

// Add a new document with a generated ID
                db.collection("todos")
                    .document(auth.currentUser?.uid.toString())
                    .collection("todos1").document()
                    .set(todo)
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            this,
                            "task couldn't be added " + e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                customAddDialog.dismiss()


            }
            customAddDialog.setCanceledOnTouchOutside(true)
            customAddDialog.show()

        }


    }
}

