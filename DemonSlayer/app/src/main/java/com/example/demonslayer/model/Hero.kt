package com.example.demonslayer.model

data class Hero(
    val id: Long,
    val image: String,
    val name: String,
    val height: String,
    val weight: String,
    val gender: String,
    val favorite: Boolean
)