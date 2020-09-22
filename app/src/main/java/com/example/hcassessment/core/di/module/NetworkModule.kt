package com.example.hcassessment.core.di.module

import androidx.annotation.NonNull
import com.example.hcassessment.BuildConfig
import com.example.hcassessment.core.di.scope.AppScope
import com.example.hcassessment.core.network.AppApi

import java.util.concurrent.TimeUnit

import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

const val BACKEND_BASE_URL = "https://api.openweathermap.org/"

@Module
class NetworkModule {

    internal val loggingInterceptor: HttpLoggingInterceptor
        @Provides
        get() = HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        )

    @Provides
    @Named("Backend")
    fun getBackendApiEndpoint(): String {
        return BACKEND_BASE_URL
    }
    @Provides
    @Named("Backend")
    @AppScope
    internal fun provideApiRetrofit(
        @Named("Backend") @NonNull baseUrl: String, client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @AppScope
    internal fun getHttpClient(
        interceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()

        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1

        builder
            .addInterceptor(interceptor)
            .dispatcher(dispatcher)
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)

        return builder.build()
    }

    @Provides
    @Named("Backend")
    @AppScope
    fun getBackendApiService(@Named("Backend") retrofit: Retrofit): AppApi {
        return retrofit.create(AppApi::class.java)
    }

}

