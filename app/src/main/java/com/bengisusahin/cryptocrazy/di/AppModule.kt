package com.bengisusahin.cryptocrazy.di

import com.bengisusahin.cryptocrazy.repository.CryptoRepository
import com.bengisusahin.cryptocrazy.service.CryptoApi
import com.bengisusahin.cryptocrazy.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCryptoApi() : CryptoApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(CryptoApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCryptoRepository(
        api: CryptoApi
    ) = CryptoRepository(api)
}