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
import com.mariyer.taskmanager.data.db.model.TaskWithNames
import com.mariyer.taskmanager.data.db.model.entyties.Task
import com.mariyer.taskmanager.ui.details.viewmodels.TaskDetailViewModel
import com.mariyer.taskmanager.utils.getIdFromSelected
import com.mariyer.taskmanager.utils.stringToInstant
import kotlinx.android.synthetic.main.fragment_task_detail.*
import kotlinx.android.synthetic.main.fragment_task_detail.saveButton
import kotlinx.android.synthetic.main.fragment_task_detail.toolbar
import kotlinx.coroutines.InternalCoroutinesApi
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@InternalCoroutinesApi
class TaskDetailFragment: Fragment(R.layout.fragment_task_detail) {

    @RequiresApi(Build.VERSION_CODES.O)
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    private val viewModel: TaskDetailViewModel by viewModels()
    private val args: TaskDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getTask(args.taskId)
        viewModel.setCurrentOrgId(args.orgId)
        viewModel.fillTaskStates()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.title = "Информация о задаче"

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
                }
                departmentForTextInput.editText?.setText(item)
            }
        }

        saveButton.setOnClickListener {
            viewModel.saveTask(
                Task(
                    id = args.taskId,
                    projectId = getIdFromSelected(projectTextDropDown.text.toString())!!,
                    requesterId = getIdFromSelected(requesterTextDropDown.text.toString())!!,
                    executorId = getIdFromSelected(executorTextDropDown.text.toString()),
                    stateId = getIdFromSelected(stateTextDropDown.text.toString())!!,
                    title = taskTitleEditText.text.toString(),
                    priority = getPriority(),
                    description = taskDescriptionEditText.text.toString(),
                    createdAt = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()!!,
                    deadline = stringToInstant(deadlineEditText.text.toString())!!,
                    startedAt = stringToInstant(startDateEditText.text.toString())!!,
                    endedAt = stringToInstant(endDateEditText.text.toString()),
                    departmentForId = getIdFromSelected(departmentForTextDropDown.text.toString())
                )
            )
            findNavController().popBackStack()
        }

        viewModel.task.observe(viewLifecycleOwner) { showTask(it)}
    }

    private fun observeDropDownLists() {
        viewModel.projects.observe(viewLifecycleOwner) {initDropDownList(TaskDetailViewModel.PROJECTS_LIST,it)}
        viewModel.requesters.observe(viewLifecycleOwner) {initDropDownList(TaskDetailViewModel.REQUESTERS_LIST,it)}
        viewModel.departments.observe(viewLifecycleOwner) {initDropDownList(TaskDetailViewModel.DEPARTMENTS_LIST,it)}
        viewModel.states.observe(viewLifecycleOwner) {initDropDownList(TaskDetailViewModel.STATES_LIST,it)}
        viewModel.executors.observe(viewLifecycleOwner) {initDropDownList(TaskDetailViewModel.EXECUTORS_LIST,it)}
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showTask(task: TaskWithNames?) {
        viewModel.setCurrentProjId(task?.projectId!!)
        viewModel.setCurrentDepId(task?.departmentForId)

        observeDropDownLists()

        priorityTextDropDown.setText(when (task?.priority) {
            1 -> resources.getString(R.string.lowPriority)
            2 -> resources.getString(R.string.belowNormalPriority)
            3 -> resources.getString(R.string.normalPriority)
            4 -> resources.getString(R.string.higherNormalPriority)
            5 -> resources.getString(R.string.highPriority)
            else -> ""
        })
        projectTextDropDown.setText("${task.projectTitle}(id=${task.projectId.toString()})")
        requesterTextDropDown.setText("${task.requesterName}(id=${task.requesterId.toString()})")
        departmentForTextDropDown.setText("${task.departmentForTitle}(id=${task.departmentForId.toString()})")
        executorTextDropDown.setText("${task.executorName}(id=${task.executorId.toString()})")
        stateTextDropDown.setText("${task.stateTitle}(id=${task.stateId.toString()})")
        taskTitleEditText.setText(task?.title)
        deadlineEditText.setText(task?.deadline?.atZone(ZoneId.systemDefault())?.format(
            TaskDetailViewModel.formatter))
        startDateEditText.setText(task?.startedAt?.atZone(ZoneId.systemDefault())?.format(
            TaskDetailViewModel.formatter))
        endDateEditText.setText(task?.endedAt?.atZone(ZoneId.systemDefault())?.format(
            TaskDetailViewModel.formatter))
        taskDescriptionEditText.setText(task?.description)

    }

    private fun getPriority(): Int {
        return when (priorityTextDropDown.text.toString()) {
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