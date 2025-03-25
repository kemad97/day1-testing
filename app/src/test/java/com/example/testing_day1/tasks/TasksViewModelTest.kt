package com.example.testing_day1.tasks

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testing_day1.data.source.DefaultTasksRepository
import getOrAwaitValue
import io.mockk.mockk
import net.bytebuddy.implementation.FixedValue.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TasksViewModelTest{


    lateinit var viewModel: TasksViewModel
    lateinit var repo: DefaultTasksRepository

    @Before
    fun setUp(){
        repo = mockk(relaxed = true)
        viewModel = TasksViewModel(repo)
    }

@Test
fun addNewTask_newTaskEventIsNotNull ()
{/*
    val app = ApplicationProvider.getApplicationContext() as Application
    val viewModel=TasksViewModel(app)
*/
    viewModel.addNewTask()

    val result=viewModel.newTaskEvent.getOrAwaitValue(){}
    assertThat(result,not(nullValue() ) )

}

    @Test
    fun setFilter_ReturnFalse() {


        viewModel.setFiltering(TasksFilterType.ACTIVE_TASKS)


        val isTasksAddVisible = viewModel.tasksAddViewVisible.getOrAwaitValue {  }
        assertThat(isTasksAddVisible,`is`(false))

    }
}


