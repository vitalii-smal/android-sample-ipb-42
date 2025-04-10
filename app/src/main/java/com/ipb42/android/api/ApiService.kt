package com.ipb42.android.api

import com.ipb42.android.models.EmployeeResponse
import retrofit2.http.GET

interface ApiService {
    @GET("employees")
    suspend fun getEmployees(): EmployeeResponse
}