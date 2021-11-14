package com.twq.todoapp

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.SearchManager
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.twq.todoapp.Adapter.TodayAdapter
import com.twq.todoapp.Fragments.FragmentAdapter
import com.twq.todoapp.Fragments.mRecyclerView
import com.twq.todoapp.Fragments.todayAdapter
import com.twq.todoapp.Model.ToDo
import java.util.*

class HomeActivity : AppCompatActivity() {

    var todoList = mutableListOf<ToDo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val db = Firebase.firestore
        val auth = Firebase.auth

        var viewPager2 = findViewById<ViewPager2>(R.id.mViewPager2)
        var mTabLayout = findViewById<TabLayout>(R.id.mTabLayout)
        var fabBtn = findViewById<FloatingActionButton>(R.id.mfloatingActionButton)
        var mToolBar = findViewById<Toolbar>(R.id.mToolBar)



        var titles = arrayOf("All", "Pending","Done")
        var iconsofTabs = arrayOf(R.drawable.to_do_list,R.drawable.pending,R.drawable.success)
        viewPager2.adapter = FragmentAdapter(this)


        TabLayoutMediator(mTabLayout, viewPager2) { tab, position ->
            tab.text = titles[position]
            tab.setIcon(iconsofTabs[position])
        }.attach()

// to tell the app mToolBar is the main toolbar
        setSupportActionBar(mToolBar)

        mToolBar.title = "ToDo M"

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
            var imgViewCloseDialogIcon = view.findViewById<ImageView>(R.id.imageViewCloseAddDialogIcon)
            var editTextDatePicker =
                view.findViewById<TextInputEditText>(R.id.editTextInputDatePicked)

            var btnAddDialog = view.findViewById<Button>(R.id.buttonAddDialogAddTask)

            //close dialog
            imgViewCloseDialogIcon.setOnClickListener {
                customAddDialog.dismiss()
            }


            //build the calender dialog

            //date dialog
            var c = Calendar.getInstance()
            var day = c.get(Calendar.DAY_OF_MONTH)
            var month = c.get(Calendar.MONTH)
            var year = c.get(Calendar.YEAR)

            var chosenDate = Date()
            editTextDatePicker.setOnClickListener {


                var datePickerDialog = DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { view, year, month, day ->
                        editTextDatePicker.setText("$day/${month + 1}/$year")
                        chosenDate = Date(year-1900,month,day)

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
            var seconds = c.get(Calendar.SECOND)

            // set on click listener for the add btn in dialog to add edit texts info to firebase
            btnAddDialog.setOnClickListener {

                println("Add is pressed")
                // Create a new user with a first and last name
                val todo = hashMapOf(
                    "name" to editTextTaskTitle.text.toString(),
                    "description" to editTextTaskDescription.text.toString(),
                    "dueDate" to chosenDate,
                    "creation" to (Date(year, month, day,hour,minute,seconds)),
                    "status" to false

                )


// Add a new document with a generated ID

                db.collection("currentUserTasks")
                    .document(auth.currentUser?.uid.toString())
                    .collection("todos").document()
                    .set(todo)
                    .addOnSuccessListener {
//                         todayAdapter = TodayAdapter(todoList)
//                        todayAdapter.notifyDataSetChanged()
                    }
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
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_bar_mune, menu)
//        val searchItem: MenuItem? = menu?.findItem(R.id.toolbarSearch)
//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        val searchView: SearchView = searchItem!!.actionView as SearchView
//
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                //search in
//                var searched = todoList.filter { it.title?.toLowerCase()!!.contains(newText!!.toLowerCase()) } as MutableList
//                mRecyclerView.adapter = TodayAdapter(searched)
//                return true
//            }


//        })
        return super.onCreateOptionsMenu(menu)
    }
    // listener
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId){
            R.id.toolbarSetting -> {
                val settingIntent: Intent = Intent(this,SettingsActivity::class.java)

                startActivity(settingIntent)
            }
//            R.id.toolbarSearch -> {
//
//
//            }
//            R.id.toolbarSort -> {
//
//            }
        }
        return super.onOptionsItemSelected(item)
    }
}

