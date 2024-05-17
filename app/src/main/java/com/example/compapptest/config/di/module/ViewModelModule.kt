package com.example.compapptest.config.di.module

import com.example.compapptest.domain.use_cases.customer.CustomerUseCases
import com.example.compapptest.ui.viewmodels.HomeViewModel
import com.example.compapptest.ui.viewmodels.SignInViewModel
import com.example.compapptest.ui.viewmodels.AuthViewModel
import com.example.protapptest.domain.use_cases.auth.AuthUseCases
import com.example.protapptest.security.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ViewModelModule {

    @Provides
    @Singleton
    fun provideSplashViewModel(authUseCases: AuthUseCases): AuthViewModel {
        return AuthViewModel(authUseCases)
    }

    @Provides
    @Singleton
    fun provideSignInViewModel(
        authUseCases: AuthUseCases,
        tokenManager: TokenManager,
        authViewModel: AuthViewModel
    ): SignInViewModel {

        return SignInViewModel(authUseCases, tokenManager, authViewModel)
    }

    @Provides
    @Singleton
    fun provideHomeViewModel(
        authUseCases: AuthUseCases,
        customerUseCases: CustomerUseCases,
        tokenManager: TokenManager,
        authViewModel: AuthViewModel,
        signInViewModel: SignInViewModel

    ): HomeViewModel {
        return HomeViewModel(
            authUseCases,
            customerUseCases,
            tokenManager,
            authViewModel
        )
    }
}