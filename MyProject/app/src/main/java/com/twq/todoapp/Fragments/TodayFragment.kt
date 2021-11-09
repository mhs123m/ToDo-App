package com.twq.todoapp.Fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.twq.todoapp.Adapter.TodayAdapter
import com.twq.todoapp.Model.ToDo
import com.twq.todoapp.R
import java.sql.Time
import java.sql.Timestamp
import java.util.*

class TodayFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_today, container, false)


        var mRecyclerView = v.findViewById<RecyclerView>(R.id.mRecyclerView)


        var db = Firebase.firestore
        var auth = Firebase.auth
        db.collection("todos")
            .document(auth.currentUser?.uid.toString())
            .collection("todos1")
            .get()
            .addOnSuccessListener { task ->
                var todoList = mutableListOf<ToDo>()
                for (document in task) {

                    todoList.add(
                        ToDo(
                            document.id, document.getString("name")!!,
                            document.getString("description")!!,
                            (document.getString("date") as? Date).toString(),
                            (document.getString("time") as? Timestamp).toString(),
                            document.getBoolean("status")!!
                        )
                    )


                }



                mRecyclerView.layoutManager = LinearLayoutManager(context)
                mRecyclerView.adapter = TodayAdapter(todoList)
            }



        return v
    }


}