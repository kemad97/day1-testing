package com.example.testing_day1

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.testing_day1.data.Task
import com.example.testing_day1.data.source.local.TasksDao
import com.example.testing_day1.data.source.local.TasksLocalDataSource
import com.example.testing_day1.data.source.local.ToDoDatabase
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class TasksLocalDataSourceTest {

    private lateinit var database: ToDoDatabase
    private lateinit var tasksDao: TasksDao
    private lateinit var tasksLocalDataSource: TasksLocalDataSource

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ToDoDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        tasksDao = database.taskDao()
        tasksLocalDataSource = TasksLocalDataSource(tasksDao)
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun saveTask_retrievesTask() = runTest {
        val task = Task("title", "description")
        tasksDao.insertTask(task)

        val loaded = tasksDao.getTaskById(task.id)

        assertThat(loaded, notNullValue())
        assertThat(loaded?.id, `is`(task.id))
        assertThat(loaded?.title, `is`(task.title))
        assertThat(loaded?.description, `is`(task.description))
        assertThat(loaded?.isCompleted, `is`(task.isCompleted))
    }

    @Test
    fun updateTaskAndGetById() = runTest {
        val task = Task("original title", "original description")
        tasksDao.insertTask(task)

        val updatedTask = Task(
            id = task.id,
            title = "updated title",
            description = "updated description",
            isCompleted = true
        )
        tasksDao.updateTask(updatedTask)

        val loaded = tasksDao.getTaskById(task.id)
        assertThat(loaded?.title, `is`("updated title"))
        assertThat(loaded?.description, `is`("updated description"))
        assertThat(loaded?.isCompleted, `is`(true))
    }

    @Test
    fun completeTask_retrievedTaskIsComplete() = runTest {
        val task = Task("title", "description")
        tasksDao.insertTask(task)

        tasksDao.updateCompleted(task.id, true)
        val loaded = tasksDao.getTaskById(task.id)

        assertThat(loaded?.isCompleted, `is`(true))
    }
}