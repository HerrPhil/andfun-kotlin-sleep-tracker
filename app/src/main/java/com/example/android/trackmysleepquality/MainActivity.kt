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

package com.example.android.trackmysleepquality

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


/**
 * This is the toy app for lesson 6 of the
 * Android App Development in Kotlin course on Udacity(https://www.udacity.com/course/???).
 *
 * The SleepQualityTracker app is a demo app that helps you collect information about your sleep.
 * - Start time, end time, quality, and time slept
 *
 * This app demonstrates the following views and techniques:
 * - Room database, DAO, and Coroutines
 *
 * It also uses and builds on the following techniques from previous lessons:
 * - Transformation map
 * - Data Binding in XML files
 * - ViewModel Factory
 * - Using Backing Properties to protect MutableLiveData
 * - Observable state LiveData variables to trigger navigation
 */

/**
 * This main activity is just a container for our fragments,
 * where the real action is.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        //
        // START OF EDGE-TO-EDGE ADAPTATION
        ViewCompat.setOnApplyWindowInsetsListener(findViewById<View>(R.id.nav_host_fragment)) { v: View, insets: WindowInsetsCompat ->
            val systemBars: Insets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val insetsController = window.insetsController
            val appearance: Int = WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            insetsController?.setSystemBarsAppearance(appearance, appearance)

            // the following clears this setting
//            insetsController?.setSystemBarsAppearance(0, appearance)
        } else {
            val decor: View = window.decorView
            val configureVisibility: Int = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

            // the following clears this setting
//            val mixed: Int = 0

            decor.systemUiVisibility = configureVisibility
        }
        // END OF EDGE-TO-EDGE ADAPTATION
        //

    }
}
