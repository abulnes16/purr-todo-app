package com.abulnes16.purrtodo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.abulnes16.purrtodo.R
import com.abulnes16.purrtodo.TaskApplication
import com.abulnes16.purrtodo.databinding.FragmentHomeBinding
import com.abulnes16.purrtodo.viewmodels.TaskViewModel
import com.abulnes16.purrtodo.viewmodels.TaskViewModelFactory

/**
 * [HomeFragment]
 * Manages the main screen of the app
 */
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory((activity?.application as TaskApplication).database.taskDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.floatingActionButton.setOnClickListener { goToAddTask() }
        return binding.root
    }


    private fun goToAddTask() {
        val action = R.id.action_homeFragment_to_addTaskFragment
        findNavController().navigate(action)
    }


}