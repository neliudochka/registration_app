package com.example.rgr

import java.io.Serializable

data class Field(
    val name: String,
    val hint: String,
    val inputType: String,
    val inputRestriction: String,
    val required: Boolean,
    var enabled: Boolean
): Serializable

