package com.ipb42.android.datasource

import com.ipb42.android.api.ApiService
import com.ipb42.android.models.Employee
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiDataSource {
    private val api: ApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummy.restapiexample.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ApiService::class.java)
    }

    suspend fun fetchEmployees(): List<Employee> {
        return api.getEmployees().data
    }
}