package com.ipb42.android

import com.ipb42.android.datasource.ApiDataSource
import com.ipb42.android.employees.EmployeesViewModel
import com.ipb42.android.models.Employee
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

@OptIn(ExperimentalCoroutinesApi::class)
class EmployeesViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var apiDataSource: ApiDataSource
    private lateinit var viewModel: EmployeesViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        apiDataSource = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadEmployees returns empty list`() = runTest {
        // Given
        val mockList = emptyList<Employee>()
        coEvery { apiDataSource.fetchEmployees() } returns mockList

        // When
        viewModel = EmployeesViewModel(apiDataSource)
        advanceUntilIdle() // wait for coroutines to finish

        // Then
        assertEquals(emptyList<Employee>(), viewModel.employees.value)
        assertEquals(null, viewModel.errorMessage.value)
    }

    @Test
    fun `loadEmployees should update employees state`() = runTest {
        // Given
        val mockList = listOf(
            Employee(1, "John Doe", 10),
            Employee(2, "Jane Smith", 24)
        )
        coEvery { apiDataSource.fetchEmployees() } returns mockList

        // When
        viewModel = EmployeesViewModel(apiDataSource)
        advanceUntilIdle() // wait for coroutines to finish

        // Then
        assertEquals(mockList, viewModel.employees.value)
        assertEquals(null, viewModel.errorMessage.value)
    }

    @Test
    fun `loadEmployees should update errorMessage on failure`() = runTest {
        // Given
        coEvery { apiDataSource.fetchEmployees() } throws RuntimeException("Server down")

        // When
        viewModel = EmployeesViewModel(apiDataSource)
        advanceUntilIdle()

        // Then
        assertEquals(emptyList<Employee>(), viewModel.employees.value)
        assertEquals(true, viewModel.errorMessage.value?.contains("Помилка завантаження"))
    }
}
