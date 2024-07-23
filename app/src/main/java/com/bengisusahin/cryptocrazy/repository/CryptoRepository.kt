package com.bengisusahin.cryptocrazy.repository

import com.bengisusahin.cryptocrazy.model.Crypto
import com.bengisusahin.cryptocrazy.model.CryptoList
import com.bengisusahin.cryptocrazy.service.CryptoApi
import com.bengisusahin.cryptocrazy.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CryptoRepository @Inject constructor(
    private val cryptoApi: CryptoApi
) {
    suspend fun getCryptoList() : Resource<CryptoList>{
        val response = try {
            cryptoApi.getCryptoList()
        }catch (e: Exception){
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }

    suspend fun getCryptoDetail() : Resource<Crypto>{
        val response = try {
            cryptoApi.getCryptoDetail()
        }catch (e: Exception){
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }
}