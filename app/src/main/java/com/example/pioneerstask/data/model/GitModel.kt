package com.example.pioneerstask.data.model

data class GitModel(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)