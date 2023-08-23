package dev.rustybite.rustysosho.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.rustybite.rustysosho.data.remote.RustySoshoService
import dev.rustybite.rustysosho.util.RustyConstants
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
            .baseUrl(RustyConstants.RUSTY_SOSHO_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RustySoshoService::class.java)
    }
}