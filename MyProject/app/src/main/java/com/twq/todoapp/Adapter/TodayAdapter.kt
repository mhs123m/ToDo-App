package com.twq.todoapp.Adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.twq.todoapp.Model.ToDo
import com.twq.todoapp.R

class TodayAdapter(var data: MutableList<ToDo>):RecyclerView.Adapter<TodayHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayHolder {

        var v = LayoutInflater.from(parent.context).inflate(R.layout.task_row,parent,false)

        return TodayHolder(v)
    }

    override fun onBindViewHolder(holder: TodayHolder, position: Int) {
        holder.textViewTitleRow.text = data[position].title
        holder.textViewDescriptionRow.text = data[position].description
        holder.textViewDueDateRow.text = data[position].dueDate
        holder.textViewTimeRow.text = data[position].time
        if (data[position].status){
            holder.checkBox.isChecked
        } else holder.checkBox.isChecked = false
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