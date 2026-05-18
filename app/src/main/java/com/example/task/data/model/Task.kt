package com.example.task.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task (
    val id: String,
    val description: String,
    var status: Status = Status.TODO
): Parcelable