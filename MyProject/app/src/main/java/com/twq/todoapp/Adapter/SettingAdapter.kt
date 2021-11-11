//package com.twq.todoapp.Adapter
//
//import android.media.Image
//import android.util.Log.i
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ArrayAdapter
//import android.widget.ImageView
//import android.widget.Spinner
//import androidx.recyclerview.widget.RecyclerView
//import com.twq.todoapp.Model.Setting
//import com.twq.todoapp.R
//
//class SettingAdapter( var data: MutableList<Setting>):RecyclerView.Adapter<SettingHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingHolder {
//
//        var v = LayoutInflater.from(parent.context).inflate(R.layout.setting_row_list,parent,false)
//        return SettingHolder(v)
//    }
//
//    override fun onBindViewHolder(holder: SettingHolder, position: Int) {
//        holder.settingIcon = data[position].icon
//        holder.settingSpinner = data[position].spinner
//
//        var list = arrayOf("No Repeat", "Daily", "Weekly", "Monthly", "Yearly")
//        holder.settingSpinner.adapter = ArrayAdapter(, android.R.layout.simple_spinner_item, list)
//    }
//
//    override fun getItemCount(): Int {
//
//        return data.size
//    }
//
//}
//
//class SettingHolder(v: View):RecyclerView.ViewHolder(v){
//    var settingIcon = v.findViewById<ImageView>(R.id.imageViewSettingRowIcon)
//    var settingSpinner = v.findViewById<Spinner>(R.id.spinnerSettingRow)
//}