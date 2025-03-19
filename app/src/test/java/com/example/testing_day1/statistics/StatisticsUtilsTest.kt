package com.example.testing_day1.statistics

import com.example.testing_day1.data.Task
import junit.framework.TestCase.assertEquals
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test


class StatisticsUtilsTest
{
    @Test
    fun getActiveAndCompletedStats_noComplete_hundredActiveZeroComplete() {
        val tasks = listOf<Task>(Task(isCompleted = false))
        val result = getActiveAndCompletedStats(tasks)

        assertEquals(result.activeTasksPercent, 100f)
        assertEquals(result.completedTasksPercent, 0f)

        assertThat(result.activeTasksPercent, `is`(100f))
        assertThat(result.completedTasksPercent, `is`(0f))

    }

    @Test
    fun getActiveAndCompletedStats_twoCompletedThreeActive() {
        val tasks = listOf(
            Task(isCompleted = true),
            Task(isCompleted = true),
            Task(isCompleted = false),
            Task(isCompleted = false),
            Task(isCompleted = false)
        )
        val result = getActiveAndCompletedStats(tasks)

        assertEquals(result.activeTasksPercent, 60f)
        assertEquals(result.completedTasksPercent, 40f)

        assertThat(result.activeTasksPercent, `is`(60f))
        assertThat(result.completedTasksPercent, `is`(40f))
    }

    @Test
    fun getActiveAndCompletedStats_emptyList() {
        val tasks = emptyList<Task>()
        val result = getActiveAndCompletedStats(tasks)

        assertEquals(result.activeTasksPercent, 0f)
        assertEquals(result.completedTasksPercent, 0f)

        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(0f))
    }

    @Test
    fun getActiveAndCompletedStats_nullTasks() {
        val result = getActiveAndCompletedStats(null)

        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(0f))
    }



}