package com.abulnes16.purrtodo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.abulnes16.purrtodo.R
import com.abulnes16.purrtodo.TaskApplication
import com.abulnes16.purrtodo.databinding.FragmentAddTaskBinding
import com.abulnes16.purrtodo.viewmodels.TaskViewModel
import com.abulnes16.purrtodo.viewmodels.TaskViewModelFactory


/**
 * Add Task Fragment
 * Manage the form to create a new task in the application
 */
class AddTaskFragment : Fragment() {

    private lateinit var binding: FragmentAddTaskBinding
    private val arguments: AddTaskFragmentArgs by navArgs()
    private val viewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory((activity?.application as TaskApplication).database.taskDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        bind()
        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        viewModel.error.observe(viewLifecycleOwner) { hasError ->
            if (hasError) {
                Toast.makeText(
                    context?.applicationContext,
                    getString(R.string.failed_task),
                    Toast.LENGTH_LONG
                ).show()
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
                this.txtTaskAdd.text = getString(R.string.edit_task)
            }
        }
    }

    private fun goBack() {
        val action = if (arguments.taskId != 0) {
            AddTaskFragmentDirections.actionAddTaskFragmentToTaskDetailFragment(arguments.taskId)
        } else {
            AddTaskFragmentDirections.actionAddTaskFragmentToHomeFragment()
        }

        findNavController().navigate(action)
    }

    private fun saveTask() {
        with(binding) {
            val title = this.txtTaskTitle.text.toString();
            val description = this.txtDescription.text.toString();
            val project = this.txtProject.text.toString();
            val deadline = this.txtDeadline.text.toString();
            if (viewModel.isEntryValid(title, description, project, deadline)) {
                viewModel.createTask(title, project, description, deadline)
                clearErrors()
                Toast.makeText(
                    context?.applicationContext,
                    getString(R.string.successful_task),
                    Toast.LENGTH_SHORT
                ).show()
                goBack()
            } else {
                manageFormError()
            }
        }
    }

    private fun manageFormError() {
        binding.apply {
            if (this.txtTaskTitle.length() == 0) {
                this.txtTaskTitle.error = formatFieldValidation("task title")
            }

            if (this.txtProject.length() == 0) {
                this.txtProject.error = formatFieldValidation("project")
            }

            if (this.txtDeadline.length() == 0) {
                this.txtDeadline.error = formatFieldValidation("deadline")
            }

            if (this.txtDescription.length() == 0) {
                this.txtDescription.error = formatFieldValidation("description")
            }
        }
    }

    private fun formatFieldValidation(fieldName: String): String {
        return getString(R.string.field_empty, fieldName)
    }

    private fun clearErrors() {
        binding.apply {
            this.txtTaskTitle.error = null
            this.txtProject.error = null
            this.txtDescription.error = null
            this.txtDeadline.error = null
        }
    }

}