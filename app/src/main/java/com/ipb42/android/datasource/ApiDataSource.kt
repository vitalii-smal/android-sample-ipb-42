package com.ipb42.android.datasource

import com.ipb42.android.models.Employee

interface ApiDataSource {
    suspend fun fetchEmployees(): List<Employee>
}