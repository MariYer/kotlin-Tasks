package com.mariyer.taskmanager.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mariyer.taskmanager.R
import com.mariyer.taskmanager.data.db.model.entyties.Organization
import com.mariyer.taskmanager.ui.details.viewmodels.OrganizationDetailViewModel
import kotlinx.android.synthetic.main.fragment_organization_detail.*

class OrganizationAddFragment: Fragment(R.layout.fragment_organization_detail) {
    private val viewModel: OrganizationDetailViewModel by viewModels()
    private var organization: Organization? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.title = getString(R.string.captionOrganizationAddFragment)
        toolbar.menu.clear()

        saveButton.setOnClickListener {
            viewModel.saveOrganization(
                Organization(
                    id = 0L,
                    title = titleEditText.text.toString(),
                    fullTitle = fullTitleEditText.text.toString(),
                    entity = if (jurRadioButton.isChecked) 1 else 2,
                    inn = innEditText.text.toString(),
                    notes = notesEditText.text.toString()
                )
            )
            findNavController().popBackStack()
        }
    }
}