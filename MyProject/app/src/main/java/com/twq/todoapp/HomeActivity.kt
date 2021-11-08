package com.twq.todoapp

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputEditText
import com.twq.todoapp.Fragments.FragmentAdapter
import java.util.zip.Inflater

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var viewPager2 = findViewById<ViewPager2>(R.id.mViewPager2)
        var mTabLayout = findViewById<TabLayout>(R.id.mTabLayout)

        var fabBtn = findViewById<FloatingActionButton>(R.id.mfloatingActionButton)

        viewPager2.adapter = FragmentAdapter(this)


        var titles = arrayOf("Daily Tasks","Weakly Tasks")

        TabLayoutMediator(mTabLayout,viewPager2){ tab, position ->
            tab.text = titles[position]
        }.attach()

        fabBtn.setOnClickListener {
            var customAddDialog = AlertDialog.Builder(this).create()

            var view = layoutInflater.inflate(R.layout.custom_dialog_add_task,null)

            customAddDialog.setView(view)
            var editTextTaskTitle = view.findViewById<TextInputEditText>(R.id.editTextInputTaskTitle)
            var editTextTaskDescription = view.findViewById<TextInputEditText>(R.id.editTextInputDescriptionAddDialog)
            var imgViewCalander = view.findViewById<ImageView>(R.id.imageViewAddDialogCalanderIcon)
            var imgViewClock = view.findViewById<ImageView>(R.id.imageViewAddDialogClockICon)
            var imgViewLocation = view.findViewById<ImageView>(R.id.imageViewAddDialogLocationIcon)
            var imgViewFolder = view.findViewById<ImageView>(R.id.imageViewAddDialogFolderIcon)
            var spinnerRepeat = view.findViewById<Spinner>(R.id.spinnerRepeatAddDialog)

            var list = arrayOf("No Repeat", "Daily","Weekly","Monthly","Yearly")
            spinnerRepeat.adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,list)



            customAddDialog.setCanceledOnTouchOutside(false)
            customAddDialog.show()
        }



    }


}

