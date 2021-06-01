package com.mariyer.taskmanager.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mariyer.taskmanager.R
import com.mariyer.taskmanager.data.db.model.entyties.Organization
import com.mariyer.taskmanager.ui.details.viewmodels.OrganizationDetailViewModel
import kotlinx.android.synthetic.main.fragment_organization_detail.*

class OrganizationDetailFragment : Fragment(R.layout.fragment_organization_detail) {
    private val viewModel: OrganizationDetailViewModel by viewModels()

    private val args: OrganizationDetailFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.title = "Информация об организации"
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.actionGetDepartments -> {
                    val action =
                        OrganizationDetailFragmentDirections.actionOrganizationDetailFragmentToDepartmentsListFragment(
                            args.orgId
                        )
                    findNavController().navigate(action)
                    true
                }
                R.id.actionGetPosts -> {
                    val action =
                        OrganizationDetailFragmentDirections.actionOrganizationDetailFragmentToPostsListFragment(
                            args.orgId
                        )
                    findNavController().navigate(action)
                    true
                }
                R.id.actionGetStaffTable -> {
                    val action =
                        OrganizationDetailFragmentDirections.actionOrganizationDetailFragmentToStaffTableFragment(
                            args.orgId
                        )
                    findNavController().navigate(action)
                    true
                }
                R.id.actionGetEmployersStaff -> {
                    val action = OrganizationDetailFragmentDirections.actionOrganizationDetailFragmentToEmployerListFragment(
                        args.orgId
                    )
                    findNavController().navigate(action)
                    true
                }
                else -> false
            }
        }

        viewModel.organization.observe(viewLifecycleOwner, ::showDetails)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getOrganizationById(args.orgId)
    }

    private fun showDetails(org: Organization) {
        titleEditText.setText(org.title)
        innEditText.setText(org.inn)
        fullTitleEditText.setText(org.fullTitle)
        jurRadioButton.isChecked = (org.entity == 1)
        privRadioButton.isChecked = (org.entity == 2)
        notesEditText.setText(org.notes)
    }
}