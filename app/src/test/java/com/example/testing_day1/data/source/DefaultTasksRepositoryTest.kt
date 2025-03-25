package com.example.testing_day1.data.sources

import com.example.testing_day1.data.Task
import com.example.testing_day1.data.source.DefaultTasksRepository
import com.example.testing_day1.data.source.TasksDataSource
import com.example.testing_day1.data.sources.remote.FakeRemoteDataSource
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import com.example.testing_day1.data.Result
import com.example.testing_day1.data.source.FakeLocalDataSource

class DefaultTasksRepositoryTest {

    private val localTasks = listOf(
        Task(id = "1", title = "Task 1"),
        Task(id = "2", title = "Task 2"),
    )

    private val remoteTasks = listOf(
        Task(id = "3", title = "Task 3"),
        Task(id = "4", title = "Task 4"),
    )

    private lateinit var localDataSource: TasksDataSource
    private lateinit var remoteDataSource: TasksDataSource
    private lateinit var repo: DefaultTasksRepository

    @Before
    fun setUp() {
        localDataSource = FakeLocalDataSource(localTasks.toMutableList())
        remoteDataSource = FakeRemoteDataSource(remoteTasks.toMutableList())
        repo = DefaultTasksRepository(remoteDataSource, localDataSource)
    }

    @Test
    fun getTask_false_Success() = runTest {

        val result = repo.getTask("1", false) as Result.Success

        assertThat(result.data, `is`(localTasks[0]))
    }

}