package com.mariyer.taskmanager.ui.lists

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mariyer.taskmanager.ui.lists.viewmodels.DepartmentsListViewModel
import com.mariyer.taskmanager.R
import com.mariyer.taskmanager.ui.lists.adapters.DepartmentsListAdapter
import com.mariyer.taskmanager.utils.AutoClearedValue
import kotlinx.android.synthetic.main.fragment_departments_list.*

class DepartmentsListFragment: Fragment(R.layout.fragment_departments_list) {
    private val viewModel: DepartmentsListViewModel by viewModels()
    private var depAdapter: DepartmentsListAdapter by AutoClearedValue<DepartmentsListAdapter>()
    private val args: DepartmentsListFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setCurrentOrgId(args.orgId)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.title = getString(R.string.captionDepartmentsListFragment)
        init()

        addButton.setOnClickListener {
            val action = DepartmentsListFragmentDirections.actionDepartmentsListFragmentToDepartmentAddFragment(args.orgId)
            findNavController().navigate(action)
        }

        viewModel.departmentsList.observe(viewLifecycleOwner) {
            depAdapter.items = it
        }
    }

    private fun init() {
        depAdapter = DepartmentsListAdapter({id ->
            val action = DepartmentsListFragmentDirections.actionDepartmentsListFragmentToDepartmentDetailFragment(id)
            findNavController().navigate(action)
        }, { id ->
            viewModel.deleteDepartment(id,args.orgId)
        })
        with(departmentsList) {
            adapter = depAdapter
            layoutManager = LinearLayoutManager(requireContext())
//            setHasFixedSize(true)
        }
    }
}