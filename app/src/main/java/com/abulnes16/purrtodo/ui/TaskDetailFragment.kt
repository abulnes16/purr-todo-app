package com.abulnes16.purrtodo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.abulnes16.purrtodo.R
import com.abulnes16.purrtodo.databinding.FragmentTaskDetailBinding


/**
 * A simple [Fragment] subclass.
 * Use the [TaskDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskDetailFragment : Fragment() {

    private lateinit var binding: FragmentTaskDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskDetailBinding.inflate(inflater, container, false)

        binding.btnGoBack.setOnClickListener { goBack() }
        binding.btnEdit.setOnClickListener { goToEdit() }

        return binding.root
    }


    private fun goBack() {
        val action = R.id.action_taskDetailFragment_to_homeFragment
        findNavController().navigate(action)
    }

    private fun goToEdit() {
        val action = R.id.action_taskDetailFragment_to_addTaskFragment
        findNavController().navigate(action)
    }


}