package com.mariyer.taskmanager.ui.lists

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mariyer.taskmanager.R
import com.mariyer.taskmanager.ui.lists.adapters.ProjectsListAdapter
import com.mariyer.taskmanager.ui.lists.viewmodels.ProjectsListViewModel
import com.mariyer.taskmanager.utils.AutoClearedValue
import kotlinx.android.synthetic.main.fragment_projects_list.*

class ProjectsListFragment: Fragment(R.layout.fragment_projects_list) {
    private var projectsAdapter: ProjectsListAdapter by AutoClearedValue<ProjectsListAdapter>()
    private val viewModel: ProjectsListViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.title = getString(R.string.captionProjectsListFragment)
        init()

        addButton.setOnClickListener {
            findNavController().navigate(R.id.action_projectsListFragment_to_projectAddFragment)
        }

        viewModel.projects.observe(viewLifecycleOwner) { projectsAdapter.items = it }
    }

    private fun init() {
        projectsAdapter = ProjectsListAdapter({ id, organizationId ->
            val action = ProjectsListFragmentDirections.actionProjectsListFragmentToProjectDetailFragment(id, organizationId)
            findNavController().navigate(action)
        }, { id ->
          viewModel.deleteProject(id)
        })

        with(projectsList) {
            adapter = projectsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
}