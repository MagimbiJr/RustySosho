package dev.rustybite.rustysosho.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.rustybite.rustysosho.data.remote.RustySoshoService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RustyModule {
    @Singleton
    @Provides
    fun providesApi(): RustySoshoService {
        return Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RustySoshoService::class.java)
    }
}