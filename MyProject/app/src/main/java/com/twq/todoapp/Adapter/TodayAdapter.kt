package com.twq.todoapp.Adapter

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.twq.todoapp.Model.ToDo
import com.twq.todoapp.R

class TodayAdapter(var data: MutableList<ToDo>):RecyclerView.Adapter<TodayHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayHolder {

        var v = LayoutInflater.from(parent.context).inflate(R.layout.task_row,parent,false)

        return TodayHolder(v)
    }

    fun doneIndication(textViewTaskTitle: TextView, status: Boolean){
        if (status){
            textViewTaskTitle.paintFlags = textViewTaskTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else{
            textViewTaskTitle.paintFlags = textViewTaskTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
    override fun onBindViewHolder(holder: TodayHolder, position: Int) {
        holder.textViewTitleRow.text = data[position].title
        holder.textViewDescriptionRow.text = data[position].description
        holder.textViewDueDateRow.text = data[position].dueDate
        holder.textViewTimeRow.text = data[position].time
        holder.checkBox!!.isChecked = data[position].status
//        if (data[position].status == true){
//            holder.checkBox.isChecked
//        }
//        else
//        {
//            holder.checkBox.isChecked = false
//        }
        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
//            doneIndication(holder.textViewTitleRow,isChecked)
//            doneIndication(holder.textViewDescriptionRow,isChecked)
//            doneIndication(holder.textViewDueDateRow,isChecked)
//            doneIndication(holder.textViewTimeRow,isChecked)
//            data[position].status = !data[position].status
            if(buttonView.isChecked) {
                var db = Firebase.firestore
//                var database = Firebase.database
//                var myRef = database.getReference("tod")
                var auth = Firebase.auth
                var key = getItemId(position).toString()
                val todo = mapOf(
                    "status" to true
                )
                db.collection("todos").document(auth.currentUser?.uid.toString())
                    .collection("todos1").document(data[position].id).update(todo)
//                    .addOnSuccessListener {
//                        doneIndication(holder.textViewTitleRow,isChecked)
//                        doneIndication(holder.textViewDescriptionRow,isChecked)
//                        doneIndication(holder.textViewDueDateRow,isChecked)
//                        doneIndication(holder.textViewTimeRow,isChecked)
//                        data[position].status = !data[position].status
//                        println(key)
//                    }.addOnFailureListener {
//                        println("ops " + key)
//                    }

            } else {
                var db = Firebase.firestore
//                var database = Firebase.database
//                var myRef = database.getReference("tod")
                var auth = Firebase.auth

                val todo = mapOf(
                    "status" to false
                )
                db.collection("todos").document(auth.currentUser?.uid.toString())
                    .collection("todos1").document(data[position].id).update(todo)
                    .addOnSuccessListener {
                        println("hell yeah")
                    }.addOnFailureListener {
                        println("ops ")
                    }
            }
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }


}

class TodayHolder(v: View):RecyclerView.ViewHolder(v){

    var textViewTitleRow = v.findViewById<TextView>(R.id.textViewTaskTitleRaw)
    var textViewDescriptionRow = v.findViewById<TextView>(R.id.textViewDescriptionRow)
    var textViewDueDateRow = v.findViewById<TextView>(R.id.textViewDueDateRow)
    var textViewTimeRow = v.findViewById<TextView>(R.id.textViewTimeRow)
    var checkBox = v.findViewById<CheckBox>(R.id.checkBoxRowItem)

}