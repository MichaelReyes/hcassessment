package com.example.hcassessment.core.di.module

import android.content.Context
import com.example.hcassessment.core.base.App
import com.example.hcassessment.core.di.scope.AppScope
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    @AppScope
    fun provideContext(app: App
    ): Context {
        return app
    }

}