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
import androidx.navigation.fragment.navArgs
import com.mariyer.taskmanager.R
import com.mariyer.taskmanager.data.db.model.entyties.Task
import com.mariyer.taskmanager.ui.details.viewmodels.TaskDetailViewModel
import com.mariyer.taskmanager.utils.getIdFromSelected
import com.mariyer.taskmanager.utils.stringToInstant
import kotlinx.android.synthetic.main.fragment_task_detail.*
import java.time.LocalDateTime
import java.time.ZoneId


class TaskAddFragment : Fragment(R.layout.fragment_task_detail) {

    private val viewModel: TaskDetailViewModel by viewModels()
    private val args: TaskAddFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setCurrentProjId(args.projId)
        viewModel.setCurrentOrgId(args.orgId)
        viewModel.fillTaskStates()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.title = "Новая задача"

        initDropDownList(TaskDetailViewModel.PRIORITY_LIST, emptyList())

        projectTextDropDown.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val item = parent?.getItemAtPosition(position).toString()
                getIdFromSelected(item)?.let {
                    viewModel.setCurrentProjId(it)
                }
                projectTextInput.editText?.setText(item)
            }
        }

        departmentForTextDropDown.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val item = parent?.getItemAtPosition(position).toString()
                getIdFromSelected(item)?.let {
                    viewModel.setCurrentDepId(it)
                    viewModel.executors.observe(viewLifecycleOwner) { initDropDownList(
                        TaskDetailViewModel.EXECUTORS_LIST, it) }
                }
                departmentForTextInput.editText?.setText(item)
            }
        }

        saveButton.setOnClickListener {
            val projectId = getIdFromSelected(projectTextDropDown.text.toString())!!
            val requesterId = getIdFromSelected(requesterTextDropDown.text.toString())!!
            val executorId = getIdFromSelected(executorTextDropDown.text.toString())
            val stateId = getIdFromSelected(stateTextDropDown.text.toString())!!
            val departmentForId = getIdFromSelected(departmentForTextDropDown.text.toString())
            viewModel.saveTask(
                Task(
                    id = 0L,
                    projectId = projectId,
                    requesterId = requesterId,
                    executorId = executorId,
                    stateId = stateId,
                    title = taskTitleEditText.text.toString(),
                    priority = getPriority(),
                    description = taskDescriptionEditText.text.toString(),
                    createdAt = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()!!,
                    deadline = stringToInstant(deadlineEditText.text.toString())!!,
                    startedAt = stringToInstant(startDateEditText.text.toString())!!,
                    endedAt = stringToInstant(endDateEditText.text.toString()),
                    departmentForId = departmentForId
                )
            )
            findNavController().popBackStack()
        }
        projectTextInput.editText?.setText(viewModel.getProjectTitle(args.projId))

        viewModel.projects.observe(viewLifecycleOwner) { initDropDownList(TaskDetailViewModel.PROJECTS_LIST, it) }
        viewModel.requesters.observe(viewLifecycleOwner) { initDropDownList(TaskDetailViewModel.REQUESTERS_LIST, it) }
        viewModel.departments.observe(viewLifecycleOwner) { initDropDownList(TaskDetailViewModel.DEPARTMENTS_LIST, it) }
        viewModel.states.observe(viewLifecycleOwner) { initDropDownList(TaskDetailViewModel.STATES_LIST, it) }
    }

    private fun getPriority(): Int {
        return when (priorityTextInput.editText?.toString()) {
            resources.getString(R.string.lowPriority) -> 1
            resources.getString(R.string.belowNormalPriority) -> 2
            resources.getString(R.string.normalPriority) -> 3
            resources.getString(R.string.higherNormalPriority) -> 4
            resources.getString(R.string.highPriority) -> 5
            else -> 0
        }
    }

    private fun initDropDownList(typeList: String, list: List<String>) {
        var listAdapter = ArrayAdapter(requireContext(), R.layout.item_list, list)
        when (typeList) {
            TaskDetailViewModel.PROJECTS_LIST -> {
                projectTextDropDown.setAdapter(listAdapter)
            }
            TaskDetailViewModel.REQUESTERS_LIST -> {
                requesterTextDropDown.setAdapter(listAdapter)
            }
            TaskDetailViewModel.DEPARTMENTS_LIST -> {
                departmentForTextDropDown.setAdapter(listAdapter)
            }
            TaskDetailViewModel.EXECUTORS_LIST -> {
                executorTextDropDown.setAdapter(listAdapter)
            }
            TaskDetailViewModel.STATES_LIST -> {
                stateTextDropDown.setAdapter(listAdapter)
            }
            TaskDetailViewModel.PRIORITY_LIST -> {
                val items = resources.getStringArray(R.array.priority_level).toList()
                listAdapter = ArrayAdapter(requireContext(), R.layout.item_list, items)
                priorityTextDropDown.setAdapter(listAdapter)
            }
        }
    }

}