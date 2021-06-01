package com.mariyer.taskmanager.ui.details

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mariyer.taskmanager.R
import com.mariyer.taskmanager.data.db.model.entyties.Department
import com.mariyer.taskmanager.ui.details.viewmodels.DepartmentDetailViewModel
import kotlinx.android.synthetic.main.fragment_department_detail.*

class DepartmentDetailFragment: Fragment(R.layout.fragment_department_detail) {

    private val viewModel: DepartmentDetailViewModel by viewModels()
    private val args: DepartmentDetailFragmentArgs by navArgs()
    private var department: Department? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setCurrentDepId(args.depId)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.title = "Информация о подразделении"
        viewModel.getDepartmentById(args.depId)

        saveButton.setOnClickListener {
            val selectedText = parentTextInput.editText?.text.toString().orEmpty()
            val p1 = selectedText.indexOf("=")
            val p2 = selectedText.indexOf(")")
            var idStr = ""
            if (p1>0 && p2>0) {
                idStr = selectedText.substring(p1 + 1, p2)
            }
            department?.apply {
                parentId = idStr.toLongOrNull()
                title = titleEditText.text.toString()
                viewModel.saveDepartment(this)
            }
            findNavController().popBackStack()
        }
        viewModel.department.observe(viewLifecycleOwner){showDepartment(it)}
    }

    private fun showDepartment(dep: Department?) {
        dep?.let {
            department = it
            viewModel.setCurrentOrgId(it.organizationId)
            viewModel.parents.observe(viewLifecycleOwner){initParentsList(it)}
            titleEditText.setText(it.title)
        }
    }

    // выпадающий список родительских подразделений
    private fun initParentsList(list: List<String>) {
        if (department?.parentId?:0L > 0L) {
            parentTextDropDown.setText(viewModel.getParentTitle(department!!.parentId!!))
        } else {
            parentTextDropDown.setText(R.string.no_seletion)
        }
        val items = list
        val parentDepsAdapter = ArrayAdapter(requireContext(), R.layout.item_list, items)
        parentTextDropDown.setAdapter(parentDepsAdapter)
    }

}