package com.todoappmoss.todoappmoss

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.todoappmoss.todoappmoss.viewmodel.RegisterViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegisterViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setUp() {
        viewModel = RegisterViewModel()
    }

    @Test
    fun `test default register result is false`() {
        val result = viewModel.registerResult.value
        assertNull(result)
    }

    @Test
    fun `test hashPassword returns same value`() {
        val password = "password123"
        val hashedPassword = viewModel.hashPassword(password)
        assertEquals(password, hashedPassword)
    }
}