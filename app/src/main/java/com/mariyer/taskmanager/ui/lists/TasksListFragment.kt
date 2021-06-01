package com.mariyer.taskmanager.ui.lists

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mariyer.taskmanager.R
import com.mariyer.taskmanager.ui.lists.adapters.TaskListAdapter
import com.mariyer.taskmanager.ui.lists.viewmodels.TasksListViewModel
import com.mariyer.taskmanager.utils.AutoClearedValue
import kotlinx.android.synthetic.main.fragment_tasks_list.*

class TasksListFragment: Fragment(R.layout.fragment_tasks_list) {

    private val viewModel: TasksListViewModel by viewModels()
    private val args: TasksListFragmentArgs by navArgs()
    private var tasksAdapter: TaskListAdapter by AutoClearedValue()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setCurrentProjectId(args.projId)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.title = getString(R.string.captionTasksListFragment)
        init()

        addButton.setOnClickListener {
            val action = TasksListFragmentDirections.actionTasksListFragmentToTaskAddFragment(args.projId, args.orgId)
            findNavController().navigate(action)
        }

        viewModel.tasks.observe(viewLifecycleOwner) {
            tasksAdapter.items = it
        }
    }

    private fun init() {
        tasksAdapter = TaskListAdapter( { id, orgId ->
            val action = TasksListFragmentDirections.actionTasksListFragmentToTaskDetailFragment(id,args.orgId)
            findNavController().navigate(action)
        }, { id, projId ->
            viewModel.deleteTask(args.projId,id)
        }
        )
        with(tasksList) {
            adapter = tasksAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}