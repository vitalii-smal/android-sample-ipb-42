package com.ipb42.android.employees

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ipb42.android.datasource.ApiDataSource
import com.ipb42.android.models.Employee
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EmployeesViewModel(private val api: ApiDataSource) : ViewModel() {

    private val _employees = MutableStateFlow<List<Employee>>(emptyList())
    val employees: StateFlow<List<Employee>> = _employees

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadEmployees()
    }

    private fun loadEmployees() {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _employees.value = api.fetchEmployees()
            } catch (e: Exception) {
                _errorMessage.value = "Помилка завантаження: ${e.localizedMessage}"
            }
        }
    }
}