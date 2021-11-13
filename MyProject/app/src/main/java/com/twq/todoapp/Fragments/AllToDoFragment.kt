package com.twq.todoapp.Fragments

import android.os.Bundle
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
import java.util.*
lateinit var todayAdapter: TodayAdapter
lateinit var todoList:MutableList<ToDo>
lateinit var mRecyclerView: RecyclerView
class AllToDoFragment : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_alltodo, container, false)


        mRecyclerView = v.findViewById<RecyclerView>(R.id.mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(context)


        var db = Firebase.firestore
        var auth = Firebase.auth
        todoList = mutableListOf<ToDo>()
        db.collection("currentUserTasks")
            .document(auth.currentUser?.uid.toString())
            .collection("todos")
            .addSnapshotListener { task, error ->
                todoList.clear()
                if (task != null) {
                    for (document in task) {

                        todoList.add(
                            ToDo(
                                document.id,
                                document.getString("name"),
                                document.getString("description"),
                                document.getDate("dueDate"),
                                document.getBoolean("status")!!
                            )
                        )


                    }
                }

                 todayAdapter = TodayAdapter(todoList)
                mRecyclerView.adapter = todayAdapter

//                mRecyclerView.notify

            }






        return v
    }


}