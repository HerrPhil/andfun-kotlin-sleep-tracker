package com.example.android.trackmysleepquality.sleeptracker

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight

// To define a binding adapter, you create a method that takes a view and some data.
// The binding adapter is responsible for updating the view to represent data.
// Use extension functions in Kotlin to code the binding adapter.
// The binding view is told about this function using the @BindingAdapter annotation.
@BindingAdapter("sleepDurationFormatted")
fun TextView.setSleepDurationFormatted(item: SleepNight?) {
    item?.let {
        // as an extension function, it has access to member variables of TextView
        // The TextView is known as the receiver object.
        // The 'this' keyword in this context refers to the receiver object.
        text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, context.resources)
    }

}

@BindingAdapter("sleepQualityString")
fun TextView.setSleepQualityString(item: SleepNight?) {
    item?.let {
        text = convertNumericQualityToString(item.sleepQuality, context.resources)
    }

}

@BindingAdapter("sleepImage")
fun ImageView.setImage(item: SleepNight?) {
    item?.let {
        setImageResource(
            when (item.sleepQuality) {
                0 -> R.drawable.ic_sleep_0
                1 -> R.drawable.ic_sleep_1
                2 -> R.drawable.ic_sleep_2
                3 -> R.drawable.ic_sleep_3
                4 -> R.drawable.ic_sleep_4
                5 -> R.drawable.ic_sleep_5
                else -> R.drawable.ic_sleep_active
            }
        )
    }
}
