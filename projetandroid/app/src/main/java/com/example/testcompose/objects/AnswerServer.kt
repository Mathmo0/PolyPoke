package com.example.testcompose.objects

import java.io.Serializable

data class AnswerServer(
    val message: String,
    val type: String
) : Serializable