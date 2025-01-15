/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// The exportSchema is true by default, it will save a schema of the database to a folder.
// This provides you with a version history of your database.
// For this lesson, exportSchema can be false.
@Database(entities = [SleepNight::class], version = 1, exportSchema = false)
abstract class SleepDatabase: RoomDatabase() {

    abstract val sleepDatabaseDao: SleepDatabaseDao

    companion object {

        // The @Volatile annotation makes sure the instance is always up-to-date
        // and the same to all execution threads.
        // @Volatile variable values will never be cached
        // and all writes and reads will be done to and from the main memory.
        @Volatile
        private var INSTANCE: SleepDatabase? = null

        fun getInstance(context: Context): SleepDatabase {
            synchronized(this) {
                // Take advantage of kotlin smart-cast, to make sure we always return a sleep database.
                // Kotlin smart-cast is only available to local variables.
                var instance = INSTANCE

                // Migration is out-of-scope for this.
                // Therefore, destructive migration is used.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SleepDatabase::class.java,
                        "sleep_history_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }

    }

}