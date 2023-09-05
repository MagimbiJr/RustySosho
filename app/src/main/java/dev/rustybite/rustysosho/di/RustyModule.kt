package dev.rustybite.rustysosho.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.rustybite.rustysosho.data.local.PreferenceManager
import dev.rustybite.rustysosho.data.remote.RustySoshoService
import dev.rustybite.rustysosho.data.repository.AuthRepository
import dev.rustybite.rustysosho.domain.repository.AuthRepositoryImpl
import dev.rustybite.rustysosho.domain.use_cases.AuthenticateUseCase
import dev.rustybite.rustysosho.domain.use_cases.RefreshTokenUseCase
import dev.rustybite.rustysosho.domain.use_cases.VerifyNumberUseCase
import dev.rustybite.rustysosho.util.ResourceProvider
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

    @Singleton
    @Provides
    fun provideAuthRepository(service: RustySoshoService): AuthRepository {
        return AuthRepositoryImpl(service)
    }

    @Singleton
    @Provides
    fun provideAuthenticationUseCase(repository: AuthRepository): AuthenticateUseCase {
        return AuthenticateUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideVerifyNumberUseCase(repository: AuthRepository): VerifyNumberUseCase {
        return VerifyNumberUseCase(repository)
    }

    @Singleton
    @Provides
    fun providePrefManager(@ApplicationContext context: Context): PreferenceManager {
        return PreferenceManager(context)
    }

    @Singleton
    @Provides
    fun provideRefreshTokenUseCase(repository: AuthRepository): RefreshTokenUseCase {
        return RefreshTokenUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider {
        return ResourceProvider(context)
    }
}