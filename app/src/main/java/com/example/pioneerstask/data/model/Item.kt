package com.example.pioneerstask.data.model


data class Item(
    val name: String,
    val language: String,
    val owner: Owner?,
    val updated_at: String,
    val url: String
)