package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

// RecyclerView will never interact directly with views.
// Instead, all of its operations are done on view holders.
//class SleepNightAdapter : RecyclerView.Adapter<SleepNightAdapter.ViewHolder>() {

// The recycler view that holds a list of items is an incredibly common pattern.
// There is a class to help implement this exact pattern.
// The class ListAdapter helps you build a recycler view adapter that is backed by a list.
// ListAdapter will take of keeping track of the list for you.
//class SleepNightAdapter(val clickListener: SleepNightListener) : ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {
class SleepNightAdapter(val clickListener: SleepNightListener) : ListAdapter<DataItem, RecyclerView.ViewHolder>(SleepNightDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    // This list is made unnecessary by ListAdapter.
//    var data = listOf<SleepNight>()
//        // Note that updating field is Kotlin's way of saying of actually set the value in the setter.
//        // In Kotlin, a field is only used as a part of a property to hold its value in memory.
//        // Fields cannot be declared directly. However, when a property needs a backing
//        // field, Kotlin provides it automatically. This backing field can be referenced in the
//        // accessors using the 'field' identifier.
//        // See https://kotlinlang.org/docs/properties.html#backing-fields
//        set(value) {
//            field = value
//            // This works, but is inefficient.
//            // It tells RecyclerView all the data changed, and must redraw all the data on the screen.
//            notifyDataSetChanged()
//        }

    // This item is made unnecessary by ListAdapter.
//    override fun getItemCount() = data.size

    // When code assists says "Convert parameter to receiver" it is essentially saying
    // that it will convert it to an extension function, of ViewHolder.

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return ViewHolder.from(parent)
        return when(viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    // RecyclerView manages what will scroll onto the screen.
    // The app need only set a value at a position in the backing data.
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder) {
            is ViewHolder -> {
                // ListAdapter change
//        val item = data[position]
//                val item = getItem(position)
                val nightItem = getItem(position) as DataItem.SleepNightItem
                // Here the bind code was re-factored to a private function.
                // Then the private function was re-factored to make its view holder parameter a receiver.
                // This is called making an extension function.
                // Then the extension function was moved manually to the ViewHolder class,
                // along with the one line to get resources.
                holder.bind(nightItem.sleepNight, clickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.SleepNightItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    fun addHeaderAndSubmitList(list: List<SleepNight>?) {
        adapterScope.launch {
            val items = when(list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.SleepNightItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    // Encapsulate the logic for ViewHolder; achieve separation of concerns
    class ViewHolder private constructor(val binding: ListItemSleepNightBinding) : RecyclerView.ViewHolder(binding.root) {

        // data binding removes the need for these properties; inline them all
//        val sleepLength: TextView = binding.findViewById(R.id.sleep_length)
//        val quality: TextView = binding.findViewById(R.id.quality_string)
//        val qualityImage: ImageView = binding.findViewById(R.id.quality_image)
        // Binding adapters can replace the individual assignments to the binding we inflated in from().

        fun bind(item: SleepNight, clickListener: SleepNightListener) {
//            val res = itemView.context.resources
//            binding.sleepLength.text =
//                convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
//            binding.qualityString.text = convertNumericQualityToString(item.sleepQuality, res)
//            binding.qualityImage.setImageResource(
//                when (item.sleepQuality) {
//                    0 -> R.drawable.ic_sleep_0
//                    1 -> R.drawable.ic_sleep_1
//                    2 -> R.drawable.ic_sleep_2
//                    3 -> R.drawable.ic_sleep_3
//                    4 -> R.drawable.ic_sleep_4
//                    5 -> R.drawable.ic_sleep_5
//                    else -> R.drawable.ic_sleep_active
//                }
//            )

            // Now, we use the data binding variable of the binding (list item layout).
            binding.sleep = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
//                val view = layoutInflater
//                    .inflate(R.layout.list_item_sleep_night, parent, false)
                val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
//                return ViewHolder(view)
                return ViewHolder(binding)
            }
        }

    }

}

class SleepNightDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    // This equality comparison is possible because SleepNight is a data class.
    // And, the data class includes an equality check.
    // That means all the values of SleepNight with be compared.
    // https://kotlinlang.org/docs/equality.html#structural-equality
    // "Value classes and data classes are two specific Kotlin types that automatically override
    // the equals() function. That's why they implement structural equality by default."
    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }

}

class SleepNightListener(val clickListener: (sleepId: Long) -> Unit) {
    fun onClick(night: SleepNight) = clickListener(night.nightId)
}

sealed class DataItem {
    data class SleepNightItem(val sleepNight: SleepNight ): DataItem() {
        override val id: Long = sleepNight.nightId
    }
    // header can be declared as an object - it has no data
    object Header: DataItem() {
        override val id: Long = Long.MIN_VALUE
    }

    abstract val id: Long
}

class TextViewHolder(view: View): RecyclerView.ViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup): TextViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.header, parent, false)
            return TextViewHolder(view)
        }
    }
}