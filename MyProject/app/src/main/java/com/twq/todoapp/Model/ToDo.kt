package com.twq.todoapp.Model

import java.io.Serializable

class ToDo (
    var title:String,
    var description:String,
    var dueDate: String,
    var time:String,
    var status: Boolean = false
        ):Serializable {
}