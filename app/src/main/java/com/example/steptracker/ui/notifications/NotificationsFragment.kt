package com.example.steptracker.ui.notifications

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.steptracker.StepLog
import com.example.steptracker.StepLogDatabaseHelper
import com.example.steptracker.databinding.FragmentNotificationsBinding
import kotlin.math.log

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private lateinit var db: StepLogDatabaseHelper

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        db = StepLogDatabaseHelper(requireContext())

        binding.addStepsButton.setOnClickListener{
            db.insertStepLog(StepLog(0, binding.stepInput.text.toString().toInt(), "${binding.datePicker.year}-${(binding.datePicker.month + 1)}-${binding.datePicker.dayOfMonth}"))
            Toast.makeText(requireContext(), "Steps Logged", Toast.LENGTH_SHORT).show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}