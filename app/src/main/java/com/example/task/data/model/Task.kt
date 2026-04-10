package com.example.task.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Task (
    val id: String,
    val description: String,
    val status: Status = Status.TODO
): Parcelable