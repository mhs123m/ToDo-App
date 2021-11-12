package com.twq.todoapp.Model

import com.google.firebase.Timestamp
import java.io.Serializable
import java.util.*


data class ToDo(
    var id: String?,
    var title:String?,
    var description:String?,
    var dueDate: Date?,
    var creation: Date?,
    var status: Boolean = false
        ):Serializable
