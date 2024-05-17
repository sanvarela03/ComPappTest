package com.example.compapptest.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.compapptest.data.local.dao.CustomerDao
import com.example.compapptest.data.local.dao.ShoppingCartDao
import com.example.compapptest.data.local.entities.AddressEntity
import com.example.compapptest.data.local.entities.CustomerEntity
import com.example.compapptest.data.local.entities.CustomerInfoEntity
import com.example.compapptest.data.local.entities.DeliveryAddressEntity
import com.example.compapptest.data.local.entities.OrderEntity
import com.example.compapptest.data.local.entities.PickupAddressEntity
import com.example.compapptest.data.local.entities.ProducerEntity
import com.example.compapptest.data.local.entities.ProducerInfoEntity
import com.example.compapptest.data.local.entities.ProductEntity
import com.example.compapptest.data.local.entities.ShoppingCartEntity
import com.example.compapptest.data.local.entities.StatusEntity
import com.example.compapptest.data.local.entities.TransporterInfoEntity

@Database(
    entities = [
        CustomerEntity::class,
        AddressEntity::class,
        OrderEntity::class,
        StatusEntity::class,
        CustomerInfoEntity::class,
        ProducerInfoEntity::class,
        TransporterInfoEntity::class,
        DeliveryAddressEntity::class,
        PickupAddressEntity::class,
        ProducerEntity::class,
        ProductEntity::class,
        ShoppingCartEntity::class
    ],
    version = 1
)
abstract class CustomerInfoDatabase : RoomDatabase() {
    abstract val dao: CustomerDao
    abstract val shoppingCartDao: ShoppingCartDao
}