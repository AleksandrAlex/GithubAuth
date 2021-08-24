package com.example.githubauthorization.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubauthorization.models.Item

@Database(entities = [EntityRepo::class], version = 1, exportSchema = false)
abstract class RepoDB : RoomDatabase() {
    abstract val repositoryDAO: RepositoryDAO

    companion object {
        @Volatile
        private var instance: RepoDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }

        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RepoDB::class.java,
                "RepositoryDB"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}