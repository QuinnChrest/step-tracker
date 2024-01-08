package com.example.steptracker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.steptracker.CardViewAdapter
import com.example.steptracker.DiaryEntry
import com.example.steptracker.FitnessDiaryDatabaseHelper
import com.example.steptracker.R
import com.example.steptracker.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var db: FitnessDiaryDatabaseHelper

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        db = FitnessDiaryDatabaseHelper(requireContext())

        val recyclerView: RecyclerView = _binding!!.RecyclerView
        val adapter = CardViewAdapter(db.getStepLog())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        binding.ResetButton.setOnClickListener{
            db.resetDatabase(db.writableDatabase)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}