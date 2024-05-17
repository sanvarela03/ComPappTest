package com.example.compapptest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
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
import com.example.compapptest.data.local.entities.relations.CustomerAndAddress
import com.example.compapptest.data.local.entities.relations.OrderAndStatus
import com.example.compapptest.data.local.entities.relations.ProducerAndAddress
import com.example.compapptest.data.local.entities.relations.ProducerAndProducts
import com.example.compapptest.data.local.entities.relations.ProducerAndShoppingCart
import kotlinx.coroutines.flow.Flow


@Dao
interface CustomerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customerEntity: CustomerEntity)

    @Insert
    suspend fun insertOrder(orderEntity: OrderEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatus(statusEntity: StatusEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducerInfoEntity(producerInfoEntity: ProducerInfoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomerInfoEntity(customerInfoEntity: CustomerInfoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransporterInfoEntity(transporterInfoEntity: TransporterInfoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(addressEntity: AddressEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeliveryAddress(deliveryAddressEntity: DeliveryAddressEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPickupAddress(pickupAddressEntity: PickupAddressEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducerEntity(producerEntity: ProducerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductEntity(productEntity: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingCartEntity(shoppingCartEntity: ShoppingCartEntity)

    @Transaction
    @Query("SELECT * FROM ShoppingCartEntity WHERE producerId = :producerId")
    suspend fun getShoppingCartEntity(producerId: Long): List<ShoppingCartEntity>?

    @Transaction
    @Query("SELECT * FROM ShoppingCartEntity")
    fun getShoppingCartEntity(): Flow<List<ShoppingCartEntity>>

    @Transaction
    @Query("SELECT * FROM CustomerEntity WHERE customerId = :customerId")
    suspend fun getCustomer(customerId: Long): CustomerEntity?

    @Transaction
    @Query("SELECT * FROM OrderEntity")
    suspend fun getOrderAndStatus(): List<OrderAndStatus>?


    @Transaction
    @Query("SELECT * FROM ProducerEntity")
    suspend fun getProducerAndAddress(): List<ProducerAndAddress>?

    @Transaction
    @Query("SELECT * FROM ProducerEntity")
    suspend fun getProducerAndProducts(): List<ProducerAndProducts>?

    @Transaction
    @Query("SELECT * FROM ProducerEntity WHERE producerId = :producerId")
    suspend fun getProducerAndProducts(producerId: Long): ProducerAndProducts?

    @Transaction
    @Query("SELECT * FROM CustomerEntity WHERE customerId = :customerId")
    suspend fun getCustomerAndAddress(customerId: Long): List<CustomerAndAddress>

    @Transaction
    @Query("SELECT * FROM CustomerInfoEntity WHERE customerId = :customerId")
    suspend fun getCustomerInfo(customerId: Long): CustomerInfoEntity

    @Transaction
    @Query("SELECT * FROM ProducerInfoEntity WHERE producerId = :producerId")
    suspend fun getProducerInfo(producerId: Long): ProducerInfoEntity

    @Transaction
    @Query("SELECT * FROM TransporterInfoEntity WHERE transporterId = :transporterId")
    suspend fun getTransporterInfo(transporterId: Long): TransporterInfoEntity

    @Transaction
    @Query("SELECT * FROM DeliveryAddressEntity WHERE orderId = :orderId")
    suspend fun getDeliveryAddress(orderId: Long): DeliveryAddressEntity?

    @Transaction
    @Query("SELECT * FROM PickupAddressEntity WHERE orderId = :orderId")
    suspend fun getPickupAddress(orderId: Long): PickupAddressEntity?

    @Transaction
    @Query("SELECT * FROM CustomerEntity")
    suspend fun getCustomerAndAddressTest(): List<CustomerAndAddress>

    @Transaction
    @Query("SELECT CustomerEntity.currentAddressId FROM CustomerEntity WHERE customerId = :customerId")
    suspend fun getCurrentAddressId(customerId: Long): Long?

    @Transaction
    @Query("SELECT * FROM AddressEntity WHERE  id = :addressId")
    suspend fun getAddress(addressId: Long): AddressEntity?

    @Transaction
    @Query("SELECT * FROM AddressEntity")
    suspend fun getAllAddresses(): List<AddressEntity>?

    @Transaction
    @Query("DELETE FROM AddressEntity")
    suspend fun clearAddressEntity()

    @Transaction
    @Query("DELETE FROM CustomerEntity")
    suspend fun clearCustomerEntity()


    @Transaction
    @Query("DELETE FROM OrderEntity")
    suspend fun clearOrderEntity()

    @Transaction
    @Query("DELETE FROM DeliveryAddressEntity")
    suspend fun clearDeliveryAddressEntity()

    @Transaction
    @Query("DELETE FROM PickupAddressEntity")
    suspend fun clearPickupAddressEntity()

    @Transaction
    @Query("DELETE FROM CustomerInfoEntity")
    suspend fun clearCustomerInfoEntity()

    @Transaction
    @Query("DELETE FROM ProducerInfoEntity")
    suspend fun clearProducerInfoEntity()

    @Transaction
    @Query("DELETE FROM TransporterInfoEntity")
    suspend fun clearTransporterInfoEntity()


    @Transaction
    @Query("DELETE FROM StatusEntity")
    suspend fun clearStatusEntity()


    @Transaction
    @Query("DELETE FROM ProducerEntity")
    suspend fun clearProducerEntity()

    @Transaction
    @Query("DELETE FROM ProducerEntity WHERE producerId = :producerId")
    suspend fun clearProducerEntityByProducerId(producerId: Long)


    @Transaction
    @Query("DELETE FROM ProductEntity WHERE producerId = :producerId")
    suspend fun clearProductEntityByProducerId(producerId: Long)


    @Transaction
    @Query("DELETE FROM AddressEntity WHERE userId = :userId ")
    suspend fun clearAddressEntityByUserId(userId: Long)

}