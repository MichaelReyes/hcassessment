package com.example.hcassessment.test

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.hcassessment.core.data.pojo.group.WeatherGroupResponse
import com.example.hcassessment.core.data.pojo.group.WeatherItem
import com.example.hcassessment.core.network.WeatherRepository
import com.example.hcassessment.feature.weather.group.WeatherGroupUi
import com.example.hcassessment.feature.weather.group.WeatherGroupViewModel
import com.example.hcassessment.utils.*
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class WeatherGroupListTest {

    @MockK
    lateinit var context: Context

    @MockK
    lateinit var viewModel: WeatherGroupViewModel

    @MockK(relaxed = true)
    lateinit var repo: WeatherRepository

    @MockK(relaxed = true)
    lateinit var groupsObserver: Observer<List<WeatherItem>>

    @MockK(relaxed = true)
    lateinit var uiObserver: Observer<WeatherGroupUi>

    @get:Rule
    val rule = InstantTaskExecutorRule()


    private val immediateScheduler: Scheduler = object : Scheduler() {

        override fun createWorker() = ExecutorScheduler.ExecutorWorker(Executor { it.run() }, true)

        // This prevents errors when scheduling a delay
        override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
            return super.scheduleDirect(run, 0, unit)
        }

    }


    @Before
    fun setup() {

        MockKAnnotations.init(this)

        RxJavaPlugins.setIoSchedulerHandler { immediateScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediateScheduler }
        RxAndroidPlugins.setMainThreadSchedulerHandler { immediateScheduler }

        repo.apply {
            every { getWeatherGroup(getCityIds().joinToString(separator = ",")) } returns getWeatherGroupsResponse()
            every { getWeatherGroup(getCityIdsNotFound().joinToString(separator = ",")) } returns getWeatherGroupsNotFoundResponse()
        }

        viewModel = WeatherGroupViewModel(context, repo)

        viewModel.apply {
            groups.observeForever(groupsObserver)
            ui.observeForever(uiObserver)
        }
    }

    @After
    fun tearDown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    @Test
    fun `Fetch weather group API success`(){
        viewModel.run {
            fetchWeatherGroups(getCityIds())

            val weatherGroupsCapture = slot<List<WeatherItem>>()
            val uiCapture = slot<WeatherGroupUi>()

            verify(exactly = 2 /* init + actual call */){ groupsObserver.onChanged(capture(weatherGroupsCapture)) }
            verify(exactly = 3 /* init + loading true + loading false */ ){ uiObserver.onChanged(capture(uiCapture)) }

            assertEquals(weatherGroup.list, weatherGroupsCapture.captured)
        }

    }

    @Test
    fun `Fetch weather group API with not found result`(){
        viewModel.run {
            fetchWeatherGroups(getCityIdsNotFound())

            val weatherGroupsCapture = slot<List<WeatherItem>>()
            val uiCapture = slot<WeatherGroupUi>()

            verify(exactly = 1 /* init + actual call */){ groupsObserver.onChanged(capture(weatherGroupsCapture)) }
            verify(exactly = 4 /* init + loading true + loading false + set error*/ ){ uiObserver.onChanged(capture(uiCapture)) }

            assertEquals("No Data", uiCapture.captured.error)
        }

    }
}