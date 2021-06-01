package com.mariyer.taskmanager.ui.lists

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mariyer.taskmanager.R
import com.mariyer.taskmanager.ui.lists.adapters.EmployersListAdapter
import com.mariyer.taskmanager.ui.lists.viewmodels.EmployerListViewModel
import com.mariyer.taskmanager.utils.AutoClearedValue
import kotlinx.android.synthetic.main.fragment_employer_list.*

class EmployerListFragment: Fragment(R.layout.fragment_employer_list) {
    private val viewModel: EmployerListViewModel by viewModels()
    private var employerAdapter: EmployersListAdapter by AutoClearedValue<EmployersListAdapter>()
    private val args: EmployerListFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setCurrentOrgId(args.orgId)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        addButton.setOnClickListener {
            val action = EmployerListFragmentDirections.actionEmployerListFragmentToEmployerAddFragment(args.orgId)
            findNavController().navigate(action)
        }

        viewModel.employers.observe(viewLifecycleOwner) {
            employerAdapter.items = it
        }
    }

    private fun init() {
        employerAdapter = EmployersListAdapter ({ id ->
            val action = EmployerListFragmentDirections.actionEmployerListFragmentToEmployerDetailFragment(args.orgId, id)
            findNavController().navigate(action)
        }, { employerId ->
            viewModel.deleteEmployer(args.orgId, employerId)
        })

        with(employersList) {
            adapter = employerAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
}