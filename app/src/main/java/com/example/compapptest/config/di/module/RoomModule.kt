package com.example.compapptest.config.di.module

import android.app.Application
import androidx.room.Room
import com.example.compapptest.data.local.dao.AddressDao
import com.example.compapptest.data.local.dao.CustomerDao
import com.example.compapptest.data.local.dao.ResourceDao
import com.example.compapptest.data.local.dao.ShoppingCartDao
import com.example.compapptest.data.local.db.CustomerInfoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Provides
    @Singleton
    fun provideCustomerInfoDatabase(app: Application): CustomerInfoDatabase {
        return Room.databaseBuilder(
            app,
            CustomerInfoDatabase::class.java,
            "customerinfodb.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCustomerDao(db: CustomerInfoDatabase): CustomerDao {
        return db.dao
    }


    @Provides
    @Singleton
    fun provideShoppingCartDao(db: CustomerInfoDatabase): ShoppingCartDao {
        return db.shoppingCartDao
    }

    @Provides
    @Singleton
    fun provideAddressDao(db: CustomerInfoDatabase): AddressDao {
        return db.addressDao
    }

    @Provides
    @Singleton
    fun provideResourceDao(db: CustomerInfoDatabase): ResourceDao {
        return db.resourceDao
    }
}

