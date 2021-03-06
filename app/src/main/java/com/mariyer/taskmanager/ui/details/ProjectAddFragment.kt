package com.mariyer.taskmanager.ui.details

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mariyer.taskmanager.R
import com.mariyer.taskmanager.data.db.model.entyties.Project
import com.mariyer.taskmanager.ui.details.viewmodels.ProjectDetailViewModel
import com.mariyer.taskmanager.utils.getIdFromSelected
import com.mariyer.taskmanager.utils.stringToInstant
import kotlinx.android.synthetic.main.fragment_organization_detail.saveButton
import kotlinx.android.synthetic.main.fragment_organization_detail.toolbar
import kotlinx.android.synthetic.main.fragment_project_detail.*
import kotlinx.android.synthetic.main.fragment_project_detail.departmentTextDropDown
import kotlinx.android.synthetic.main.fragment_project_detail.departmentTextInput

class ProjectAddFragment: Fragment(R.layout.fragment_project_detail) {

    private val viewModel: ProjectDetailViewModel by viewModels()
    private var departList = emptyList<String>()
    private var orgList = emptyList<String>()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toolbar.title = getString(R.string.captionProjectAddFragment)
        toolbar.menu.clear()

        organizationTextDropDown.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val item = parent?.getItemAtPosition(position).toString()

                getIdFromSelected(item)?.let {
                    viewModel.setCurrentOrgId(
                        it
                    )
                    viewModel.departments.observe(viewLifecycleOwner) {list ->
                        departList = list
                        initDropDownLists(orgList, departList)
                    }
                }
                organizationTextInput.editText?.setText(item)
            }
        }

        saveButton.setOnClickListener {
            viewModel.saveProject(
                Project(
                    id = 0L,
                    departmentId = getIdFromSelected(departmentTextInput.editText?.text.toString())!!,
                    title = projectTitleEditText.text.toString(),
                    description = projectDescrEditView.text.toString(),
                    dateStart = stringToInstant(projectStartDateEditText.text.toString())!!,
                    dateEnd = stringToInstant(projectEndDateEditText.text.toString())
                )
            )
            findNavController().popBackStack()
        }

        viewModel.organizations.observe(viewLifecycleOwner) {
            orgList = it
            initDropDownLists(orgList, departList)
        }
    }

    // выпадающие списки организаций и подразделений
    private fun initDropDownLists(orgList: List<String>, depList: List<String>) {
        val emptyText = resources.getString(R.string.no_seletion)
        var items = emptyList<String>()
        var selectedText = organizationTextInput.editText?.text.toString()

        // если никакая организация еще не была выбрана
        if (depList.isEmpty()) {
            items = orgList
            val organizationsAdapter = ArrayAdapter(requireContext(), R.layout.item_list, items)
            organizationTextDropDown.setAdapter(organizationsAdapter)
        }
        organizationTextDropDown.setText(selectedText)

        departmentTextDropDown.setText(emptyText)
        items = depList
        val departmentsAdapter = ArrayAdapter(requireContext(), R.layout.item_list, items)
        departmentTextDropDown.setAdapter(departmentsAdapter)
    }
}