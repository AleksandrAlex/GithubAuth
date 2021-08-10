package com.example.githubauthorization.models

data class ResponseRepositories(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)