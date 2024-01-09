package com.example.steptracker.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.steptracker.DiaryEntry
import com.example.steptracker.FitnessDiaryDatabaseHelper
import com.example.steptracker.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private lateinit var db: FitnessDiaryDatabaseHelper

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        db = FitnessDiaryDatabaseHelper(requireContext())

        binding.addStepsButton.setOnClickListener{
            if(binding.stepInput.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Steps is required!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            db.insertDiaryEntry(
                DiaryEntry(
                    0,
                    binding.stepInput.text.toString().toInt(),
                    if(binding.caloriesInput.text.toString().isEmpty()) null else binding.stepInput.text.toString().toDouble(),
                    if(binding.weightInput.text.toString().isEmpty()) null else binding.weightInput.text.toString().toDouble(),
                    if(binding.fatPercentInput.text.toString().isEmpty()) null else binding.fatPercentInput.text.toString().toDouble(),
                    if(binding.navelInInput.text.toString().isEmpty()) null else binding.navelInInput.text.toString().toDouble(),
                    "${binding.datePicker.year}-${(binding.datePicker.month + 1)}-${binding.datePicker.dayOfMonth}"
                )
            )
            Toast.makeText(requireContext(), "Steps Logged", Toast.LENGTH_SHORT).show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}