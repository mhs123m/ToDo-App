package com.twq.todoapp.Model

import com.google.firebase.Timestamp
import java.io.Serializable


data class ToDo(
    var id: String?,
    var title:String?,
    var description:String?,
    var dueDate: Timestamp?,
    var time: Timestamp?,
    var status: Boolean = false
        ):Serializable
