package com.example.compapptest.data.repository

import android.util.Log
import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.data.local.dao.CustomerDao
import com.example.compapptest.data.local.entities.ProducerEntity
import com.example.compapptest.data.local.entities.relations.ProducerAndAddress
import com.example.compapptest.data.local.entities.relations.ProducerAndProducts
import com.example.compapptest.data.remote.api.CustomerApi
import com.example.compapptest.domain.repository.ProducerRepository
import com.example.protapptest.security.TokenManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProducerRepositoryImpl @Inject constructor(
    private val tokenManager: TokenManager,
    private val api: CustomerApi,
    private val dao: CustomerDao
) : ProducerRepository {
    override suspend fun searchProducers(fetchFromRemote: Boolean): Flow<ApiResponse<List<ProducerAndAddress>>> =
        flow {
            emit(ApiResponse.Loading)
            val customerId = tokenManager.getUserId().first()
            customerId?.let { id ->
                Log.d("SAD", "ID : $id")
                val localProducersAndAddress = dao.getProducerAndAddress()
                Log.d("SAD", "localProducersAndAddress : $localProducersAndAddress")

                if (localProducersAndAddress != null) {
                    emit(ApiResponse.Success(localProducersAndAddress))
                }

                val isDbEmpty = localProducersAndAddress == null

                val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

                if (shouldJustLoadFromCache) {
                    return@flow
                }

                val remote = getRemote(id)

                remote?.let { producerSearchResponseList ->
//                    dao.clearAddressEntity()
                    producerSearchResponseList.forEach {
                        dao.clearAddressEntityByUserId(it.producerId)
                    }
                    dao.clearProducerEntity()

                    producerSearchResponseList.forEach {
                        dao.insertProducerEntity(it.toProducerEntity())
                        dao.insertAddress(it.currentAddress.toAddressEntity())
                    }

                }

                val newProducerAndAddress = dao.getProducerAndAddress()

                newProducerAndAddress?.let {
                    emit(ApiResponse.Success(newProducerAndAddress))
                }
            }
        }

    override suspend fun searchProducer(
        producerId: Long,
        fetchFromRemote: Boolean
    ): Flow<ApiResponse<ProducerAndProducts>> = flow {
        emit(ApiResponse.Loading)
        val customerId = tokenManager.getUserId().first()
        customerId?.let { id ->
            val local = dao.getProducerAndProducts(producerId = producerId)

            if (local != null) {
                emit(ApiResponse.Success(local))
            }

            val isDbEmpty = local == null

            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

            if (shouldJustLoadFromCache) {
                return@flow
            }

            val remote = getRemote(id, producerId)

            remote?.let {
                dao.clearProductEntityByProducerId(it.producerSearchResponse.producerId)
                dao.clearAddressEntityByUserId(it.producerSearchResponse.producerId)
                dao.clearProducerEntityByProducerId(it.producerSearchResponse.producerId)

                dao.insertProducerEntity(it.producerSearchResponse.toProducerEntity())
                dao.insertAddress(it.producerSearchResponse.currentAddress.toAddressEntity())

                it.productList.forEach { productResponse ->
                    dao.insertProductEntity(productResponse.toProductEntity())
                }
            }

            val newLocal = dao.getProducerAndProducts(producerId)

            newLocal?.let {
                emit(ApiResponse.Success(newLocal))
            }

        }
    }

    private suspend fun FlowCollector<ApiResponse<ProducerAndProducts>>.getRemote(
        id: Long,
        producerId: Long
    ) = try {
        val response = api.searchProducer(customerId = id, producerId = producerId)
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    } catch (e: IOException) {
        e.printStackTrace()
        emit(ApiResponse.Failure("No se pudo cargar los datos", 400))
        null
    } catch (e: HttpException) {
        e.printStackTrace()
        emit(ApiResponse.Failure("No se pudo cargar los datos", 400))
        null
    }

    private suspend fun FlowCollector<ApiResponse<List<ProducerAndAddress>>>.getRemote(
        id: Long
    ) = try {
        val response = api.searchProducers(id)
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }

    } catch (e: IOException) {
        e.printStackTrace()
        emit(ApiResponse.Failure("No se pudo cargar los datos", 400))
        null
    } catch (e: HttpException) {
        e.printStackTrace()
        emit(ApiResponse.Failure("No se pudo cargar los datos", 400))
        null
    }
}