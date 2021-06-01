package com.mariyer.taskmanager.ui.details

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mariyer.taskmanager.R
import com.mariyer.taskmanager.ui.details.adapters.EmployerStaffListAdapter
import com.mariyer.taskmanager.ui.details.viewmodels.EmployerDetailViewModel
import com.mariyer.taskmanager.utils.AutoClearedValue
import com.mariyer.taskmanager.utils.getIdFromSelected
import com.mariyer.taskmanager.utils.stringToInstant
import kotlinx.android.synthetic.main.fragment_employer_carier.*
import kotlinx.android.synthetic.main.fragment_employer_carier.departmentTextDropDown
import kotlinx.android.synthetic.main.fragment_employer_carier.departmentTextInput
import kotlinx.android.synthetic.main.fragment_employer_carier.postTextDropDown
import kotlinx.android.synthetic.main.fragment_employer_carier.postTextInput
import java.time.ZoneId

class EmployerStaffFragment : Fragment(R.layout.fragment_employer_carier) {

    private val viewModel: EmployerDetailViewModel by activityViewModels()
    private var staffAdapter: EmployerStaffListAdapter by AutoClearedValue<EmployerStaffListAdapter>()
    private var departList = emptyList<String>()
    private var postList = emptyList<String>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()

//        viewModel.getDepartmentsDropDownList(viewModel.currentOrgId)
        viewModel.getStaffs()

        addButton.setOnClickListener {
            addCarierLayout.isVisible = true
//            viewModel.getDepartmentsDropDownList(viewModel.currentOrgId)
        }

        saveCarrierButton.setOnClickListener {
            viewModel.addStaff(
                emplId = 0L,
                depId = getIdFromSelected(
                    departmentTextInput.editText?.text.toString().orEmpty()
                )!!,
                depTitle = getTitleFromSelected(
                    departmentTextInput.editText?.text.toString().orEmpty()
                )!!,
                postId = getIdFromSelected(
                    postTextInput.editText?.text.toString().orEmpty()
                )!!,
                postTitle = getTitleFromSelected(
                    postTextInput.editText?.text.toString().orEmpty()
                )!!,
                dateStart = stringToInstant(startDateEditText.text.toString())!!,
                dateEnd = stringToInstant(endDateEditText.text.toString().orEmpty()),
                orgId = viewModel.currentOrgId
            )
            addCarierLayout.isVisible = false
        }

        cancelCarrierButton.setOnClickListener {
            addCarierLayout.isVisible = false
        }

        departmentTextDropDown.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val item = parent?.getItemAtPosition(position).toString()

                viewModel.setDepId(getIdFromSelected(item) ?: 0L)

                departmentTextInput.editText?.setText(item)   //.setSelection(position)
            }
        }


        viewModel.departments.observe(viewLifecycleOwner) {
            departList = it
            initDropDownLists(departList, postList)
        }
        viewModel.posts.observe(viewLifecycleOwner) {
            postList = it
            initDropDownLists(departList, postList)
        }

        viewModel.staffs.observe(viewLifecycleOwner) {
            staffAdapter.items = it
        }
    }

    private fun getTitleFromSelected(selectedText: String): String? {
        var p1 = 0
        (0..(selectedText.length - 1)).forEach {
            if (selectedText[it] == '.') {
                p1++
            }
        }
        val p2 = selectedText.indexOf("(")
        var tmpStr = ""
        if (p2 > 0) {
            p1 = if (p1 > 0) p1 - 1 else p1
            tmpStr = selectedText.substring(p1, p2)
        }
        return tmpStr.trim()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun init() {
        staffAdapter = EmployerStaffListAdapter( { emplId, depId, postId ->
            showStaffInfo( emplId, depId, postId)
        }, {  emplId, depId, postId ->
            viewModel.deleteStaff(emplId, depId, postId)
        })

        with(carrierList) {
            adapter = staffAdapter
            layoutManager = LinearLayoutManager(requireContext())
//            setHasFixedSize(true)
        }

        addCarierLayout.isVisible = false
    }

    // выпадающие списки подразделений и должностей
    private fun initDropDownLists(depList: List<String>, postList: List<String>) {
        var items = emptyList<String>()
        var selectedText = departmentTextInput.editText?.text.toString()

        departmentTextDropDown.setText(selectedText)
        items = depList
        val departamentsAdapter = ArrayAdapter(requireContext(), R.layout.item_list, items)
        departmentTextDropDown.setAdapter(departamentsAdapter)

        postTextDropDown.setText(R.string.no_seletion)
        items = postList
        val postsAdapter = ArrayAdapter(requireContext(), R.layout.item_list, items)
        postTextDropDown.setAdapter(postsAdapter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showStaffInfo(emplId: Long, depId: Long, postId: Long) {
        val staff = viewModel.getStaff(emplId, depId, postId)

        departmentTextDropDown.setText(viewModel.getDepartmentTitle(depId))
        postTextDropDown.setText(viewModel.getPostTitle(postId))
        startDateEditText.setText(staff?.dateStart?.atZone(ZoneId.systemDefault())?.format(
            EmployerDetailViewModel.formatter))
        endDateEditText.setText(staff?.dateEnd?.atZone(ZoneId.systemDefault())?.format(
            EmployerDetailViewModel.formatter))
    }

}