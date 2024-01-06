package com.example.steptracker

import android.health.connect.datatypes.units.Percentage

data class DiaryEntry(
    val id: Int,
    val steps: Int,
    val calories: Double?,
    val weight: Double?,
    val fatPercentage: Double?,
    val navelInches: Double?,
    val date: String
)
