package com.todoappmoss.todoappmoss

import ApiClient
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.todoappmoss.todoappmoss.data.model.Task
import com.todoappmoss.todoappmoss.ui.calendar.CalendarViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import java.util.*


@ExperimentalCoroutinesApi
class CalendarViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CalendarViewModel

    @Mock
    private lateinit var apiClient: ApiClient
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = CalendarViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun testSelectDate() = runBlockingTest {
        val observer: Observer<Date> = Mockito.mock(Observer::class.java) as Observer<Date>
        val testDate = Date()

        viewModel.selectedDate.observeForever(observer)
        viewModel.selectDate(testDate)

        Mockito.verify(observer).onChanged(testDate)
    }

}
