package com.example.steptracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale

class CardViewAdapter (private val itemList: List<DiaryEntry>) : RecyclerView.Adapter<CardViewAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        val notes: TextView = itemView.findViewById(R.id.notes)
        val steps: TextView = itemView.findViewById(R.id.steps)
        val calories: TextView = itemView.findViewById(R.id.calories)
        val weight: TextView = itemView.findViewById(R.id.weight)
        val fatPercentage: TextView = itemView.findViewById(R.id.fatPercentage)
        val navelIn: TextView = itemView.findViewById(R.id.navelIn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.diary_entry_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemList[position]

        // Bind your data to the card here
        holder.textTitle.text = convertDateString(currentItem.date)
        holder.steps.text = currentItem.steps.toString()
        holder.calories.text = if(currentItem.calories == 0.0) "N/A" else currentItem.calories.toString()
        holder.weight.text = if(currentItem.weight == 0.0) "N/A" else currentItem.calories.toString()
        holder.fatPercentage.text = if(currentItem.fatPercentage == 0.0) "N/A" else currentItem.calories.toString()
        holder.navelIn.text = if(currentItem.navelInches == 0.0) "N/A" else currentItem.calories.toString()
        holder.notes.text = currentItem.calories.toString()

        if(currentItem.calories == 0.0)
            holder.notes.visibility = View.GONE
        else
            holder.notes.visibility = View.VISIBLE
    }

    fun convertDateString(inputDateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault())

        return try {
            val date = inputFormat.parse(inputDateString)
            date?.let { outputFormat.format(it) } ?: "Invalid Date"
        } catch (e: Exception) {
            "Invalid Date"
        }
    }
}