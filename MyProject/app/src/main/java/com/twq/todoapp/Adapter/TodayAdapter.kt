package com.twq.todoapp.Adapter

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.twq.todoapp.Model.ToDo
import com.twq.todoapp.R
import java.sql.Time
import java.sql.Timestamp
import java.util.*
import java.util.zip.Inflater

class TodayAdapter(var data: MutableList<ToDo>, var db: FirebaseFirestore) : RecyclerView.Adapter<TodayHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayHolder {

        var v = LayoutInflater.from(parent.context).inflate(R.layout.task_row, parent, false)

        return TodayHolder(v)
    }

    fun doneIndication(textViewTaskTitle: TextView, status: Boolean) {
        if (status) {
            textViewTaskTitle.paintFlags = textViewTaskTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            textViewTaskTitle.paintFlags =
                textViewTaskTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TodayHolder, position: Int) {
        holder.textViewTitleRow.text = data[position].title
        holder.textViewDescriptionRow.text = data[position].description
        holder.textViewDueDateRow.text = data[position].dueDate.toString()
//        holder.textViewTimeRow.text = data[position].time.toString()
        holder.checkBox!!.isChecked = data[position].status

        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->

            if (buttonView.isChecked) {
                var db = Firebase.firestore
//                var database = Firebase.database
//                var myRef = database.getReference("tod")
                var auth = Firebase.auth

                val todo = mapOf(
                    "status" to true
                )
                db.collection("todos").document(auth.currentUser?.uid.toString())
                    .collection("todos1").document(data[position].id!!).update(todo)
            } else {
                var db = Firebase.firestore
//                var database = Firebase.database
//                var myRef = database.getReference("tod")
                var auth = Firebase.auth

                val todo = mapOf(
                    "status" to false
                )
                db.collection("todos").document(auth.currentUser?.uid.toString())
                    .collection("todos1").document(data[position].id!!).update(todo)
                    .addOnSuccessListener {
                        println("hell yeah")
                    }.addOnFailureListener {
                        println("ops ")
                    }
            }
        }


        holder.itemView.setOnClickListener {

            var customEditDialog = AlertDialog.Builder(holder.itemView.context).create()

            var view = LayoutInflater.from(holder.itemView.context).inflate(R.layout
                .custom_dialog_edit_task,null,false)


            customEditDialog.setView(view)
            var editTextTaskTitleEditDialog =
                view.findViewById<TextInputEditText>(R.id.editTextInputTaskTitleEditDialog)
            var editTextTaskDescriptionEditDialog =
                view.findViewById<TextInputEditText>(R.id.editTextInputDescriptionEditDialog)
//            var imgViewCalender = view.findViewById<ImageView>(R.id.imageViewAddDialogCalanderIcon)
//            var imgViewClock = view.findViewById<ImageView>(R.id.imageViewAddDialogClockIcon)
//            var imgViewLocation = view.findViewById<ImageView>(R.id.imageViewAddDialogLocationIcon)
//            var imgViewFolder = view.findViewById<ImageView>(R.id.imageViewAddDialogFolderIcon)
            var imgViewCloseDialogIconEditDialog =
                view.findViewById<ImageView>(R.id.imageViewCloseEditDialogIcon)
            var editTextDatePickerEditDialog =
                view.findViewById<TextInputEditText>(R.id.editTextInputDatePickedEditDialog)
            var editTextTimePickerEditDialog =
                view.findViewById<TextInputEditText>(R.id.editTextInputTimePickedEditDialog)
            //var spinnerRepeat = view.findViewById<Spinner>(R.id.spinnerRepeatAddDialog)
            var btnSaveDialog = view.findViewById<Button>(R.id.buttonSaveEditTaskDialog)
            var btnDeleteDialog = view.findViewById<Button>(R.id.buttonDeleteEditTask)


            // set edit Texts with the current data from firebase


            //btn save changes
            var db = Firebase.firestore
            var auth = Firebase.auth
            btnSaveDialog.setOnClickListener {


            var updated = mapOf(
                "name" to editTextTaskTitleEditDialog.text.toString(),
                "description" to editTextTaskDescriptionEditDialog.text.toString(),
                "date" to editTextDatePickerEditDialog.text.toString(),
                "time" to editTextTimePickerEditDialog.text.toString(),

            )
            db.collection("todos").document(auth.currentUser?.uid.toString())
                .collection("todos1")
                .document(data[position].id!!).update(updated)

                customEditDialog.dismiss()
            }


            // btn delete item

            btnDeleteDialog.setOnClickListener {

                db.collection("todos").document(auth.currentUser?.uid.toString())
                    .collection("todos1").document(data[position].id!!).delete()

                customEditDialog.dismiss()
            }
            // TODO: 11/9/21 ADD  // set edit Texts with the current data from firebase
//            db.collection("todos")
//                .document(auth.currentUser?.uid.toString())
//                .collection("todos1")
//                .get()
//                .addOnSuccessListener { task ->
//                    var todoList = mutableListOf<ToDo>()
//                    for (document in task) {
//
////                        todoList.add(
////                            ToDo(
////                                document.id, document.getString("name")!!,
////                                document.getString("description")!!,
////                                (document.getString("date") as? Date).toString(),
////                                (document.getString("time") as? Timestamp).toString(),
////                                document.getBoolean("status")!!
////                            )
////                        )
//
//                        editTextTaskTitleEditDialog.setText(document.getString("name"))
//
//
//                    }
//
//
//
//
//                }



            //close dialog
            imgViewCloseDialogIconEditDialog.setOnClickListener {
                customEditDialog.dismiss()
            }
            // spinner items
//            var list = arrayOf("No Repeat", "Daily", "Weekly", "Monthly", "Yearly")
//            spinnerRepeat.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)

            //build the calender dialog

            //date dialog
            var c = Calendar.getInstance()
            var day = c.get(Calendar.DAY_OF_MONTH)
            var month = c.get(Calendar.MONTH)
            var year = c.get(Calendar.YEAR)


            editTextDatePickerEditDialog.setOnClickListener {


                var datePickerDialog = DatePickerDialog(
                    view.context,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        editTextDatePickerEditDialog.setText("$dayOfMonth/${month + 1}/$year")

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
            editTextTimePickerEditDialog.setOnClickListener {
                var timePickerDialog = TimePickerDialog(
                    view.context,
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        editTextTimePickerEditDialog.setText("$hourOfDay:$minute")
                    },
                    hour,
                    minute,
                    true
                )
                timePickerDialog.show()
            }

//            val db = Firebase.firestore
//            val auth = Firebase.auth
            // set on click listener for the add btn in dialog to add edit texts info to firebase
//            btnAddDialog.setOnClickListener {
//
//                println("Add is pressed")
//                // Create a new user with a first and last name
//                val todo = hashMapOf(
//                    "name" to editTextTaskTitle.text.toString(),
//                    "description" to editTextTaskDescription.text.toString(),
//                    "dueDate" to Date(year, month, day).toString(),
//                    "time" to Time(hour, minute, 0).toString(),
//                    "status" to false
//
//                )
//
//// Add a new document with a generated ID
//                db.collection("todos")
//                    .document(auth.currentUser?.uid.toString())
//                    .collection("todos1").document()
//                    .set(todo)
//                    .addOnFailureListener { e ->
//
//                    }
//                customEditDialog.dismiss()
//
//
//            }
            customEditDialog.setCanceledOnTouchOutside(true)
            customEditDialog.show()

        }

    }

    override fun getItemCount(): Int {
        return data.size
    }


}

class TodayHolder(v: View) : RecyclerView.ViewHolder(v) {

    var textViewTitleRow = v.findViewById<TextView>(R.id.textViewTaskTitleRaw)
    var textViewDescriptionRow = v.findViewById<TextView>(R.id.textViewDescriptionRow)
    var textViewDueDateRow = v.findViewById<TextView>(R.id.textViewDueDateRow)
    var textViewTimeRow = v.findViewById<TextView>(R.id.textViewTimeRow)
    var checkBox = v.findViewById<CheckBox>(R.id.checkBoxRowItem)

}