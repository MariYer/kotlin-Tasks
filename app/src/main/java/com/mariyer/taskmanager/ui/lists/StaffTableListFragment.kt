package com.mariyer.taskmanager.ui.lists

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mariyer.taskmanager.R
import com.mariyer.taskmanager.ui.lists.adapters.StaffTableListAdapter
import com.mariyer.taskmanager.ui.lists.viewmodels.StaffTableListViewModel
import com.mariyer.taskmanager.utils.AutoClearedValue
import kotlinx.android.synthetic.main.fragment_staff_table_list.*

class StaffTableListFragment : Fragment(R.layout.fragment_staff_table_list) {

    private val viewModel: StaffTableListViewModel by viewModels<StaffTableListViewModel>()
    private var staffAdapter: StaffTableListAdapter by AutoClearedValue<StaffTableListAdapter>()
    private val args: StaffTableListFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setCurrentOrgId(args.orgId)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.title = getString(R.string.captionStaffTableListFragment)
        init()

        addButton.setOnClickListener {
            val action =
                StaffTableListFragmentDirections.actionStaffTableFragmentToStaffTableAddFragment(args.orgId)
            findNavController().navigate(action)
        }

        viewModel.staffTable.observe(viewLifecycleOwner) {
            staffAdapter.items = it
        }
    }

    private fun init() {
        staffAdapter = StaffTableListAdapter({ depId, postId ->
            val action = StaffTableListFragmentDirections.actionStaffTableFragmentToStaffTableDetailFragment(depId, postId, args.orgId)
            findNavController().navigate(action)
        }, { depId, postId ->
            viewModel.deleteStaffTable(args.orgId, depId, postId)
        })

        with (staffTableList) {
            adapter = staffAdapter
            layoutManager = LinearLayoutManager(requireContext())
//            setHasFixedSize(true)
        }
    }
}