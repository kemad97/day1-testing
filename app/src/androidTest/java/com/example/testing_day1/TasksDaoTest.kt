package com.example.testing_day1

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.testing_day1.data.Task
import com.example.testing_day1.data.source.local.TasksDao
import com.example.testing_day1.data.source.local.ToDoDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TasksDaoTest {

    private lateinit var database: ToDoDatabase
    private lateinit var dao: TasksDao


    @Before
    fun setup(){
        database= Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ToDoDatabase::class.java
        ).build()
        dao=database.taskDao()
    }

    @Test
    fun insertTaskAndGetItByID() = runTest {
        val task=Task("task1")
        dao.insertTask(task)

        val result = dao.getTaskById(task.id)

        assertNotNull(result as Task)
        assertThat(result.id , `is`(task.id))
        assertThat(result.title , `is`(task.title))
        assertThat(result.description , `is`(task.description))
        assertThat(result.isCompleted , `is`(task.isCompleted))

    }

    @Test
    fun saveTask_retrievesTask() = runTest {
        val task = Task("title", "description" )
        database.taskDao().insertTask(task)

        val loaded = database.taskDao().getTaskById(task.id)

        assertThat(loaded as Task, `is`(task))
    }


    @Test
    fun updateTaskAndGetById() = runTest {
        val task = Task("original title", "original description")
        dao.insertTask(task)

        val updatedTask = Task(id = task.id, title = "updated title", description = "updated description", isCompleted = true
        )
        dao.updateTask(updatedTask)

        val loaded = dao.getTaskById(task.id)
        assertThat(loaded?.title, `is`("updated title"))
        assertThat(loaded?.description, `is`("updated description"))
        assertThat(loaded?.isCompleted, `is`(true))
    }

    @Test
    fun completeTask_retrievedTaskIsComplete() = runTest {
        val task = Task("title", "description")
        dao.insertTask(task)

        dao.updateCompleted(task.id, true)
        val loaded = dao.getTaskById(task.id)

        assertThat(loaded?.isCompleted,`is` (true))
    }


}