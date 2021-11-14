package com.twq.todoapp.Adapter

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.twq.todoapp.Model.ToDo
import com.twq.todoapp.R
import java.util.*


class TodayAdapter(var data: MutableList<ToDo>) : RecyclerView.Adapter<TodayHolder>() {
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
//        holder.textViewDescriptionRow.text = data[position].description
        holder.checkBox!!.isChecked = data[position].status

        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->

            if (buttonView.isChecked) {
                var db = Firebase.firestore
                var auth = Firebase.auth

                val todo = mapOf(
                    "status" to true
                )
                db.collection("currentUserTasks").document(auth.currentUser?.uid.toString())
                    .collection("todos").document(data[position].id!!).update(todo)
            } else {
                var db = Firebase.firestore
                var auth = Firebase.auth

                val todo = mapOf(
                    "status" to false
                )
                db.collection("currentUserTasks").document(auth.currentUser?.uid.toString())
                    .collection("todos").document(data[position].id!!).update(todo)
                    .addOnSuccessListener {
                        println("hell yeah")
                    }.addOnFailureListener {
                        println("ops ")
                    }
            }

        }

        var c = Calendar.getInstance()
        var year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)
        var day = c.get(Calendar.DAY_OF_MONTH)
        var dueDate = data[position].dueDate
        var monthOfDueDate = dueDate?.month?.plus(1)

        // over due


        if (dueDate != null) {
            holder.textViewDueDateRow.text = ("Due: ${dueDate.date}" +
                    "/$monthOfDueDate/${dueDate.year + 1900}")
        } else holder.textViewDueDateRow.text = ""


        // due today


        holder.itemView.setOnClickListener {

            var customEditDialog = AlertDialog.Builder(holder.itemView.context).create()

            var view = LayoutInflater.from(holder.itemView.context).inflate(
                R.layout
                    .custom_dialog_edit_task, null, false
            )
            customEditDialog.setView(view)


            var editTextTaskTitleEditDialog =
                view.findViewById<TextInputEditText>(R.id.editTextInputTaskTitleEditDialog)
            var editTextTaskDescriptionEditDialog =
                view.findViewById<TextInputEditText>(R.id.editTextInputDescriptionEditDialog)
            var imgViewCloseDialogIconEditDialog =
                view.findViewById<ImageView>(R.id.imageViewCloseEditDialogIcon)
            var editTextDatePickerEditDialog =
                view.findViewById<TextInputEditText>(R.id.editTextInputDatePickedEditDialog)
            var btnSaveDialog = view.findViewById<Button>(R.id.buttonSaveEditTaskDialog)
            var btnDeleteDialog = view.findViewById<Button>(R.id.buttonDeleteEditTask)
            // set edit texts to the current info of

            editTextTaskTitleEditDialog.setText(holder.textViewTitleRow.text)
            editTextDatePickerEditDialog.setText(holder.textViewDueDateRow.text)
            var db = Firebase.firestore
            var auth = Firebase.auth
            db.collection("currentUserTasks")
                .document(auth.currentUser?.uid.toString())
                .collection("todos")
                .document(data[position].id.toString())
                .addSnapshotListener { user, error ->
                    if (user != null) {
                        editTextTaskDescriptionEditDialog.setText(user.getString("description"))

                    }
                }



            // set edit Texts with the current data from firebase
            var chosenDate = Date()
            editTextDatePickerEditDialog.setOnClickListener {


                var datePickerDialog = DatePickerDialog(
                    holder.itemView.context,
                    DatePickerDialog.OnDateSetListener { view, year, month, day ->
                        editTextDatePickerEditDialog.setText("$day/${month + 1}/$year")
                        chosenDate = Date(year, month, day)

                    },
                    year,
                    month,
                    day
                )
                datePickerDialog.show()
            }

            //btn save changes

            btnSaveDialog.setOnClickListener {
                var updated = mapOf(
                    "name" to editTextTaskTitleEditDialog.text.toString(),
                    "description" to editTextTaskDescriptionEditDialog.text.toString(),
                    "dueDate" to chosenDate,
                    "status" to false,
                )
                db.collection("currentUserTasks")
                    .document(auth.currentUser?.uid.toString())
                    .collection("todos")
                    .document(data[position].id!!).update(updated)


                customEditDialog.dismiss()


            }


            // btn delete item

            btnDeleteDialog.setOnClickListener {

                var confDialog = AlertDialog.Builder(holder.itemView.context)
                confDialog.setTitle("Delete Task")
                confDialog.setMessage("Are you sure you want to delete this task?")
                confDialog.setPositiveButton("Delete") { dialog , which ->


                db.collection("currentUserTasks").document(auth.currentUser?.uid.toString())
                    .collection("todos").document(data[position].id!!).delete()

                    dialog.dismiss()
                customEditDialog.dismiss()

                }
                confDialog.setNegativeButton("Cancel") { dialog , which ->
                    dialog.dismiss()
                }
                confDialog.create()
                confDialog.show()

            }

            //close dialog
            imgViewCloseDialogIconEditDialog.setOnClickListener {
                customEditDialog.dismiss()
            }
            customEditDialog.setCanceledOnTouchOutside(true)
            customEditDialog.show()

        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TodayAdapter

        if (data != other.data) return false

        return true
    }

    override fun hashCode(): Int {
        return data.hashCode()
    }


}

class TodayHolder(v: View) : RecyclerView.ViewHolder(v) {

    var textViewTitleRow = v.findViewById<TextView>(R.id.textViewTaskTitleRaw)
//    var textViewDescriptionRow = v.findViewById<TextView>(R.id.textViewDescriptionRow)
    var textViewDueDateRow = v.findViewById<TextView>(R.id.textViewDueDateRow)
//    var textViewTimeRow = v.findViewById<TextView>(R.id.textViewTimeRow)
    var checkBox = v.findViewById<CheckBox>(R.id.checkBoxRowItem)

}