package com.example.githubauthorization.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.githubauthorization.models.Owner

@Entity
data class EntityRepo (
    @PrimaryKey
    val id: Long,
    val description: String?,
    val name: String,
    val created_at: String,
    val full_name: String,
    val git_url: String,
//    val language: String,
    val repos_url: String,
    val login: String,
    val avatar_url: String,
        )