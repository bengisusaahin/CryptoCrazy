package com.bengisusahin.cryptocrazy.viewmodel

import androidx.lifecycle.ViewModel
import com.bengisusahin.cryptocrazy.model.Crypto
import com.bengisusahin.cryptocrazy.repository.CryptoRepository
import com.bengisusahin.cryptocrazy.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel(){

    suspend fun getCryptoDetail() : Resource<Crypto> {
        return repository.getCryptoDetail()
    }

}