package com.bengisusahin.cryptocrazy.service

import com.bengisusahin.cryptocrazy.model.Crypto
import com.bengisusahin.cryptocrazy.model.CryptoList
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApi {

    @GET("atilsamancioglu/IA32-CryptoComposeData/main/cryptolist.json")
    suspend fun getCryptoList(): CryptoList

    @GET("atilsamancioglu/IA32-CryptoComposeData/main/crypto.json")
    suspend fun getCryptoDetail(): Crypto

/*
    // nomics kapanmasaydı
    // https://api.nomics.com/v1/prices?key=%3CKendi%20API%C2%A0Anahtar%C4%B1n%C4%B1z%3E

    @GET("prices")
    suspend fun getCryptoList(
        @Query("key") key: String
    ) : List<CryptoList>

    @GET("currencies")
    suspend fun getCryptoDetail(
        @Query("key") key: String,
        @Query("ids") ids: String,
        @Query("attributes") attributes: String
    ) : Unit

 */

}