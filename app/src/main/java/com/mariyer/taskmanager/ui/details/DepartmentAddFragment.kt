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

class DepartmentAddFragment: Fragment(R.layout.fragment_department_detail) {

    private val viewModel: DepartmentDetailViewModel by viewModels()
    private val args: DepartmentAddFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.title = getString(R.string.captionDepartmentAddFragment)

        viewModel.setCurrentOrgId(args.orgId)

        saveButton.setOnClickListener {
            val selectedText = parentTextInput.editText?.text.toString().orEmpty()
            val p1 = selectedText.indexOf("=")
            val p2 = selectedText.indexOf(")")
            var idStr = ""
            if (p1>0 && p2>0) {
                idStr = selectedText.substring(p1 + 1, p2)
            }
            viewModel.saveDepartment(
                Department(
                    id = 0L,
                    organizationId = args.orgId,
                    parentId = idStr.toLongOrNull(),
                    title = titleEditText.text.toString()
                )
            )
            findNavController().popBackStack()
        }

        viewModel.parents.observe(viewLifecycleOwner) {initParentsList(it)}
    }

    // выпадающий список родительских подразделений
    private fun initParentsList(list: List<String>) {
        parentTextDropDown.setText(R.string.no_seletion)
        val items = list
        val typeMoviesAdapter = ArrayAdapter(requireContext(), R.layout.item_list, items)
        parentTextDropDown.setAdapter(typeMoviesAdapter)
    }

}