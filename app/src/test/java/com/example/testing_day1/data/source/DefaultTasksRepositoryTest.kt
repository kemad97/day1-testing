package com.example.testing_day1.data.source

import androidx.media3.test.utils.FakeDataSource
import com.example.testing_day1.data.Task
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test


class DefaultTasksRepositoryTest{

    private val localTasks = mutableListOf(
        Task("task1"),
        Task("task2")

    )

    private val remoteTasks = mutableListOf(
        Task("task1"),
        Task("task2")
    )

    private lateinit var fakeLocalDataSource: FakeDataSource
    private lateinit var fakeRemoteDataSource: FakeDataSource
    private lateinit var repository: DefaultTasksRepository

    @Before
    fun setup(){
        fakeLocalDataSource = FakeTasksDataSource(localTasks)
        fakeRemoteDataSource = FakeTasksDataSource(remoteTasks)
        repository = DefaultTasksRepository(fakeLocalDataSource, fakeRemoteDataSource)

    }

    @Test
    fun getTasks_requestsAllTasksFromLocalDataSource() = runBlocking {

        val result=repository.getTasks(false) as Result.Success
        assertThat(result.data, `is`(localTasks))

    }


}

