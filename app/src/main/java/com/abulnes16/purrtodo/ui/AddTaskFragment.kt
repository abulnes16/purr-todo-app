package com.abulnes16.purrtodo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginEnd
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.abulnes16.purrtodo.R
import com.abulnes16.purrtodo.TaskApplication
import com.abulnes16.purrtodo.databinding.FragmentAddTaskBinding
import com.abulnes16.purrtodo.viewmodels.TaskViewModel
import com.abulnes16.purrtodo.viewmodels.TaskViewModelFactory


/**
 * A simple [Fragment] subclass.
 * Use the [AddTaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddTaskFragment : Fragment() {

    private lateinit var binding: FragmentAddTaskBinding
    private val arguments: AddTaskFragmentArgs by navArgs()
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
        // Inflate the layout for this fragment
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        bind()

        return binding.root
    }


    private fun goBack() {
        val action = R.id.action_addTaskFragment_to_homeFragment
        findNavController().navigate(action)
    }

    private fun saveTask() {
        with(binding) {
            val title = this.txtTaskTitle.text.toString();
            val description = this.txtDescription.text.toString();
            val project = this.txtProject.text.toString();
            val deadline = this.txtDeadline.text.toString();
            if (isEntryValid(title, description, project, deadline)) {
                viewModel.createTask(title, project, description, deadline)
            }
        }
    }

    private fun bind() {
        binding.apply {
            this.btnGoBackAddTask.setOnClickListener { goBack() }
            this.btnSave.setOnClickListener { saveTask() }
            this.btnDelete.visibility = View.GONE
            if (arguments.taskId != 0) {
                this.btnDelete.visibility = View.VISIBLE
            }
        }
    }

    private fun isEntryValid(
        title: String,
        description: String,
        project: String,
        deadline: String
    ): Boolean {
        if (title.isBlank() || description.isBlank() || project.isBlank() || deadline.isBlank()) {
            return false
        }
        return true
    }


}