package com.mariyer.taskmanager.ui.details

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mariyer.taskmanager.R
import com.mariyer.taskmanager.data.db.model.entyties.StaffTable
import com.mariyer.taskmanager.ui.details.viewmodels.StaffTableDetailViewModel
import kotlinx.android.synthetic.main.fragment_staff_table_detail.*
import kotlinx.android.synthetic.main.fragment_staff_table_detail.saveButton
import kotlinx.android.synthetic.main.fragment_staff_table_detail.toolbar

class StaffTableAddFragment: Fragment(R.layout.fragment_staff_table_detail) {
    private val viewModel: StaffTableDetailViewModel by viewModels()
    private val args: StaffTableAddFragmentArgs by navArgs()
    private var departList = emptyList<String>()
    private var postList = emptyList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setCurrentOrgId(args.orgId)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.title = "Новая позиция штатного расписания"

        saveButton.setOnClickListener {
            viewModel.saveStaffTable(
                makeStaff(),
                true
            )
            findNavController().popBackStack()
        }

        viewModel.departments.observe(viewLifecycleOwner) {
            departList = it
            initDropDownLists(departList,postList)
        }
        viewModel.posts.observe(viewLifecycleOwner) {
            postList = it
            initDropDownLists(departList,postList)
        }
    }
    private fun makeStaff(): StaffTable {
        var selectedText = departmentTextInput.editText?.text.toString().orEmpty()
        var p1 = selectedText.indexOf("=")
        var p2 = selectedText.indexOf(")")
        var depIdStr = ""
        if (p1>0 && p2>0) {
            depIdStr = selectedText.substring(p1 + 1, p2)
        }

        selectedText = postTextInput.editText?.text.toString().orEmpty()
        p1 = selectedText.indexOf("=")
        p2 = selectedText.indexOf(")")
        var postIdStr = ""
        if (p1>0 && p2>0) {
            postIdStr = selectedText.substring(p1 + 1, p2)
        }

        return StaffTable(
            departmentId = depIdStr.toLong(),
            postId = postIdStr.toLong(),
            isChief = isChieffCheckBox.isChecked,
            basicSalary = basicSalaryEditText.text.toString().toBigDecimalOrNull(),
            maxCount = (maxCountEditText.text.toString().toIntOrNull()?:0)
        )
    }
    // выпадающие списки подразделений и должностей
    private fun initDropDownLists(depList: List<String>,postList: List<String>) {
        var items = emptyList<String>()

        departmentTextDropDown.setText(R.string.no_seletion)
        items = depList
        val departamentsAdapter = ArrayAdapter(requireContext(), R.layout.item_list, items)
        departmentTextDropDown.setAdapter(departamentsAdapter)

        postTextDropDown.setText(R.string.no_seletion)
        items = postList
        val postsAdapter = ArrayAdapter(requireContext(), R.layout.item_list, items)
        postTextDropDown.setAdapter(postsAdapter)
    }
}