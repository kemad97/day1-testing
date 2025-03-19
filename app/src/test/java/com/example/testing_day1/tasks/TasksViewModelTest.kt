package com.example.testing_day1.tasks

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import getOrAwaitValue
import net.bytebuddy.implementation.FixedValue.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TasksViewModelTest{
@Test
fun addNewTask_newTaskEventIsNotNull ()
{
    val app = ApplicationProvider.getApplicationContext() as Application
    val viewModel=TasksViewModel(app)

    viewModel.addNewTask()
    val result=viewModel.newTaskEvent.getOrAwaitValue(){}
    assertThat(result,not(nullValue() ) )

}

    @Test
    fun setFilter_ReturnFalse() {
        val viewModel = TasksViewModel(ApplicationProvider.getApplicationContext())
        viewModel.setFiltering(TasksFilterType.ACTIVE_TASKS)
        val isTasksAddVisible = viewModel.tasksAddViewVisible.getOrAwaitValue {  }
        assertThat(isTasksAddVisible,`is`(false))

    }
}


