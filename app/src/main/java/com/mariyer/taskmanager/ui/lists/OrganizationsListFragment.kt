package com.mariyer.taskmanager.ui.lists

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mariyer.taskmanager.R
import com.mariyer.taskmanager.ui.lists.adapters.OrganizationListAdapter
import com.mariyer.taskmanager.ui.lists.viewmodels.OrganizationsListViewModel
import com.mariyer.taskmanager.utils.AutoClearedValue
import kotlinx.android.synthetic.main.fragment_organization_list.*

class OrganizationsListFragment: Fragment(R.layout.fragment_organization_list) {
    private val viewModel: OrganizationsListViewModel by viewModels()
    private var orgAdapter: OrganizationListAdapter by AutoClearedValue<OrganizationListAdapter>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.title = getString(R.string.captionOrganizationsListFragment)
        init()

        addButton.setOnClickListener {
            findNavController().navigate(R.id.action_organizationsListFragment_to_organizationAddFragment)
        }

        viewModel.organizations.observe(viewLifecycleOwner) {
            orgAdapter.items = it
        }
    }

    private fun init() {
        orgAdapter = OrganizationListAdapter({ id ->
            val action = OrganizationsListFragmentDirections.actionOrganizationsListFragmentToOrganizationDetailFragment(id)
            findNavController().navigate(action)
        }, {id ->
            viewModel.deleteOrganization(id)
        })

        with(organizationList) {
            adapter = orgAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
}