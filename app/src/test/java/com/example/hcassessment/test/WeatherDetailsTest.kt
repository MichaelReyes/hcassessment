package com.example.hcassessment.test

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.hcassessment.core.base.App
import com.example.hcassessment.core.data.pojo.group.WeatherItem
import com.example.hcassessment.core.network.WeatherRepository
import com.example.hcassessment.feature.weather.details.WeatherDetailsUi
import com.example.hcassessment.feature.weather.details.WeatherDetailsViewModel
import com.example.hcassessment.utils.*
import com.orhanobut.hawk.*
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
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
class WeatherDetailsTest {

    @MockK(relaxed = true)
    lateinit var app: App

    @MockK
    lateinit var context: Context

    @MockK
    lateinit var viewModel: WeatherDetailsViewModel

    @MockK(relaxed = true)
    lateinit var repo: WeatherRepository

    @MockK(relaxed = true)
    lateinit var weatherObserver: Observer<WeatherItem>

    @MockK(relaxed = true)
    lateinit var uiObserver: Observer<WeatherDetailsUi>

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

        Hawk.init(app).build()

        RxJavaPlugins.setIoSchedulerHandler { immediateScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediateScheduler }
        RxAndroidPlugins.setMainThreadSchedulerHandler { immediateScheduler }

        repo.apply {
            every { getWeatherDetails("Manila") } returns getManilaWeather()
            every { getWeatherDetails("Prague") } returns getPragueWeather()
            every { getWeatherDetails("Seoul") } returns getSeoulWeather()
        }

        viewModel = WeatherDetailsViewModel(context, repo)

        viewModel.apply {
            weather.observeForever(weatherObserver)
            ui.observeForever(uiObserver)
        }
    }

    @After
    fun tearDown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    @Test
    fun `On select Manila set details(passed from list) and fetch updated details from API`(){
        viewModel.apply {
            setWeatherDetails(manilaWeatherIdOnly)

            val weatherCapture = slot<WeatherItem>()

            fetchDetails()

            verify(exactly = 2 /* on set after click on list + fetch details API response */) { weatherObserver.onChanged(capture(weatherCapture)) }

            assertEquals(manilaWeather, weatherCapture.captured)
        }
    }

    @Test
    fun `On select Manila and toggle favorite`(){
        viewModel.apply {
            setWeatherDetails(manilaWeatherIdOnly)

            val weatherCapture = slot<WeatherItem>()

            fetchDetails()

            verify(exactly = 2 /* on set after click on list + fetch details API response */) { weatherObserver.onChanged(capture(weatherCapture)) }

            assertEquals(manilaWeather, weatherCapture.captured)

            onToggleFavorite()

            verify(exactly = 3 /* on set after click on list + fetch details API response + toggle fav */) { weatherObserver.onChanged(capture(weatherCapture)) }

            assertEquals(true, weatherCapture.captured.isFavorite)
        }
    }
}