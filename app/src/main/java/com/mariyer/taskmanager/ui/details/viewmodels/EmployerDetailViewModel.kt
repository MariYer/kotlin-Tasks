package com.mariyer.taskmanager.ui.details.viewmodels

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.mariyer.taskmanager.data.db.model.EmployerStaffWithNames
import com.mariyer.taskmanager.data.db.model.EmployerWithStaff
import com.mariyer.taskmanager.data.db.model.entyties.Employer
import com.mariyer.taskmanager.data.db.model.entyties.EmployerStaff
import com.mariyer.taskmanager.data.repositories.DepartmentRepository
import com.mariyer.taskmanager.data.repositories.EmployerRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.format.DateTimeFormatter

class EmployerDetailViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository = EmployerRepository(application)
    private val departmentRepository = DepartmentRepository(application)

    var currentOrgId: Long = 0L
        private set
    var currentDepId: Long = 0L
        private set
    var currentEmployerId: Long = 0L
        private set

    private val staffsMutableData = MutableLiveData<List<EmployerStaffWithNames>>()
    val staffs: LiveData<List<EmployerStaffWithNames>>
        get() = staffsMutableData

    val departments: LiveData<List<String>>
        get() = departmentRepository.getDepartmentsWithHier(currentOrgId)
            .map { list ->
                list.map { data ->
                    "${data.hierTitle} (id=${data.id.toString()})"
                }
            }
            .asLiveData(viewModelScope.coroutineContext)

    val posts: LiveData<List<String>>
        get() = repository.getStaffTableByOrgId(currentOrgId)
            .map { list ->
                list.map { record ->
                    if (record.departmentId == (currentDepId ?: 0L) || (currentDepId ?: 0L) == 0L) {
                        "${record.postTitle} (id=${record.postId.toString()})"
                    } else {
                        ""
                    }
                }
            }
            .asLiveData(viewModelScope.coroutineContext)

    private val employerMutableData = MutableLiveData<Employer?>()
    val employer: LiveData<Employer?>
        get() = employerMutableData

    fun setOrgId(newOrgId: Long) {
        currentOrgId = newOrgId
    }

    fun setDepId(newDepId: Long) {
        currentEmployerId = newDepId
    }

    fun setEmployerId(newEmployerId: Long) {
        currentEmployerId = newEmployerId
    }

    fun saveEmployerWithStaff(employer: Employer) {
        viewModelScope.launch {
            try {
                var emplStaffList = mutableListOf<EmployerStaff>()
                staffsMutableData.value.orEmpty().forEach {
                    emplStaffList.add(
                        EmployerStaff(
                            id = 0L,
                            departmentId = it.departmentId,
                            postId = it.postId,
                            employerId = 0L,
                            dateStart = it.dateStart,
                            dateEnd = it.dateEnd
                        )
                    )
                }

                repository.saveEmployerWithStaff(
                    EmployerWithStaff(
                        employer,
                        emplStaffList
                    )
                )
            } catch (t: Throwable) {
                Log.e("EmployerDetailViewModel", "saveEmployerWithStaff: ${t.message}")
            }
        }
    }


    fun getStaffs() {
        val prevList = staffsMutableData.value.orEmpty()
        viewModelScope.launch {
            try {
                staffsMutableData.postValue(repository.getEmployerStaffs(currentEmployerId))
                if (staffsMutableData.value?.isEmpty() == true) {
                    staffsMutableData.postValue(prevList)
                }
            } catch (t: Throwable) {
                staffsMutableData.postValue(prevList)
                Log.e("EmployerDetailViewModel", "getStaffs: ${t.message}")
            }
        }
    }

    fun addStaff(
        emplId: Long,
        depId: Long,
        depTitle: String,
        postId: Long,
        postTitle: String,
        dateStart: Instant,
        dateEnd: Instant?,
        orgId: Long?
    ) {
        viewModelScope.launch {
            try {
                staffsMutableData.postValue(
                    repository.addStaffLocal(
                        staffsMutableData.value.orEmpty(),
                        emplId,
                        "",
                        depId,
                        depTitle,
                        postId,
                        postTitle,
                        dateStart,
                        dateEnd,
                        orgId
                    )
                )
            } catch (t: Throwable) {
                Log.e("EmployerDetailViewModel", "addStaff: ${t.message}")
            }
        }
    }

    fun deleteStaff(emplId: Long, depId: Long, postId: Long) {
        viewModelScope.launch {
            try {
                staffsMutableData.postValue(
                    repository.removeStaffLocal(
                        staffsMutableData.value.orEmpty(),
                        emplId,
                        depId,
                        postId
                    )
                )
            } catch (t: Throwable) {
                Log.e("EmployerDetailViewModel", "deleteStaff: ${t.message}")
            }
        }
    }

    fun getStaff(emplId: Long, depId: Long, postId: Long): EmployerStaffWithNames? {
        return repository.getStaffLocal(staffsMutableData.value.orEmpty(), emplId, depId, postId)
    }

    fun getDepartmentTitle(depId: Long): String? {
        return repository.searchItemById(departments.value.orEmpty(), depId)
    }

    fun getPostTitle(postId: Long): String? {
        return repository.searchItemById(posts.value.orEmpty(), postId)
    }

    fun getEmployer(emplId: Long) {
        viewModelScope.launch {
            try {
                employerMutableData.postValue(repository.getEmployer(emplId))
            } catch (t: Throwable) {
                Log.e("EmployerDetailViewModel", "getEmployer: ${t.message}")
            }
        }
    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    }
}