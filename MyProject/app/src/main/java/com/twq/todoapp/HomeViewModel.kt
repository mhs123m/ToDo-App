package com.twq.todoapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.twq.todoapp.Model.ToDo

class HomeViewModel() : ViewModel() {

    fun getAllTodos(): MutableLiveData<MutableList<ToDo>> {

        var mutableLiveData = MutableLiveData<MutableList<ToDo>>()

        var db = Firebase.firestore
        var auth = Firebase.auth


        db.collection("currentUserTasks").document(auth.currentUser?.uid.toString())
            .collection("todos")
            .addSnapshotListener { result, error ->
                var todoList = mutableListOf<ToDo>()
                if (result != null) {
                    for (document in result)
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
                mutableLiveData.postValue(todoList)
            }

        return mutableLiveData
    }
}