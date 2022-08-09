package com.abulnes16.purrtodo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.abulnes16.purrtodo.R
import com.abulnes16.purrtodo.TaskApplication
import com.abulnes16.purrtodo.database.data.Task
import com.abulnes16.purrtodo.databinding.FragmentTaskDetailBinding
import com.abulnes16.purrtodo.viewmodels.TaskViewModel
import com.abulnes16.purrtodo.viewmodels.TaskViewModelFactory


/**
 * A simple [Fragment] subclass.
 * Use the [TaskDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskDetailFragment : Fragment() {

    private lateinit var binding: FragmentTaskDetailBinding
    private val arguments: TaskDetailFragmentArgs by navArgs()
    private val viewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory((activity?.application as TaskApplication).database.taskDao())
    }

    private lateinit var task: Task

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val taskId = arguments.taskId
        viewModel.retrieveTask(taskId).observe(this.viewLifecycleOwner) { selectedTask ->
            task = selectedTask
            bind(selectedTask)
        }
    }

    private fun bind(task: Task) {
        binding.apply {
            this.txtTaskTitle.text = task.title
            this.txtProjectName.text = task.project
            this.txtProjectDeadline.text = task.deadline
            this.txtProjectDescription.text = task.description
            if (!task.isInProgress) {
                this.btnInProgress.setOnClickListener { markAsInProgress() }
            } else {
                this.btnInProgress.visibility = View.GONE
            }

            if (!task.isDone) {
                this.btnDone.setOnClickListener { markAsDone() }
            } else {
                this.btnDone.visibility = View.GONE
                this.btnInProgress.visibility = View.GONE
            }
        }
    }

    private fun goBack() {
        val action = R.id.action_taskDetailFragment_to_homeFragment
        findNavController().navigate(action)
    }

    private fun goToEdit() {
        val action = TaskDetailFragmentDirections.actionTaskDetailFragmentToAddTaskFragment(task.id)
        findNavController().navigate(action)
    }

    private fun markAsInProgress() {
        viewModel.markInProgress(task)
    }

    private fun markAsDone() {
        viewModel.markDone(task)
    }


}