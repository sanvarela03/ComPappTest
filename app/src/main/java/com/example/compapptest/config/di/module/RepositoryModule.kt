package com.example.compapptest.config.di.module

import com.example.compapptest.data.local.dao.AddressDao
import com.example.compapptest.data.local.dao.CustomerDao
import com.example.compapptest.data.local.dao.ResourceDao
import com.example.compapptest.data.local.dao.ShoppingCartDao
import com.example.compapptest.data.remote.api.AddressApi
import com.example.compapptest.data.remote.api.AuthApi
import com.example.compapptest.data.remote.api.CustomerApi
import com.example.compapptest.data.repository.AddressRepositoryImpl
import com.example.compapptest.data.repository.AuthRepositoryImpl
import com.example.compapptest.data.repository.CustomerRepositoryImpl
import com.example.compapptest.data.repository.OrderRepositoryImpl
import com.example.compapptest.data.repository.ProducerRepositoryImpl
import com.example.compapptest.data.repository.ShoppingCartRepositoryImpl
import com.example.compapptest.domain.repository.AddressRepository
import com.example.compapptest.domain.repository.AuthRepository
import com.example.compapptest.domain.repository.CustomerRepository
import com.example.compapptest.domain.repository.OrderRepository
import com.example.compapptest.domain.repository.ProducerRepository
import com.example.compapptest.domain.repository.ShoppingCartRepository
import com.example.protapptest.security.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApi,
        tokenManager: TokenManager
    ): AuthRepository {
        return AuthRepositoryImpl(api, tokenManager)
    }

    @Provides
    @Singleton
    fun provideCustomerRepository(
        api: CustomerApi,
        dao: CustomerDao
    ): CustomerRepository {
        return CustomerRepositoryImpl(
            api,
            dao
        )
    }

    @Provides
    @Singleton
    fun provideAddressRepository(
        api: AddressApi,
        addressDao: AddressDao,
        customerDao: CustomerDao,
        tokenManager: TokenManager
    ): AddressRepository {
        return AddressRepositoryImpl(
            addressApi = api,
            addressDao = addressDao,
            customerDao = customerDao,
            tokenManager = tokenManager
        )
    }

    @Provides
    @Singleton
    fun provideOrderRepository(
        customerDao: CustomerDao,
        shoppingCartDao: ShoppingCartDao,
        customerApi: CustomerApi,
        tokenManager: TokenManager
    ): OrderRepository {
        return OrderRepositoryImpl(
            customerDao = customerDao,
            customerApi = customerApi,
            shoppingCartDao = shoppingCartDao,
            tokenManager = tokenManager
        )
    }


    @Provides
    @Singleton
    fun provideProducerRepository(
        dao: CustomerDao,
        api: CustomerApi,
        resourceDao: ResourceDao,
        tokenManager: TokenManager
    ): ProducerRepository {
        return ProducerRepositoryImpl(
            tokenManager,
            api,
            dao,
            resourceDao
        )
    }


    @Provides
    @Singleton
    fun provideShoppingCartRepository(
        dao: CustomerDao,
        shoppingCartDao: ShoppingCartDao
    ): ShoppingCartRepository {
        return ShoppingCartRepositoryImpl(
            dao,
            shoppingCartDao
        )
    }

}