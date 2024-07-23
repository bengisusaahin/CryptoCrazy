package com.bengisusahin.cryptocrazy.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bengisusahin.cryptocrazy.model.CryptoListItem
import com.bengisusahin.cryptocrazy.repository.CryptoRepository
import com.bengisusahin.cryptocrazy.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    var cryptoList = mutableStateOf<List<CryptoListItem>>(listOf())
    var error = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private var initialCryptoList = listOf<CryptoListItem>()
    private var isSearchStarting = true

    init {
        loadCryptoList()
    }

    fun loadCryptoList() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getCryptoList()
                when (result) {
                    is Resource.Success -> {
                        val cryptoListItem = result.data!!.mapIndexed{ index, crypto ->
                            CryptoListItem(
                                currency = crypto.currency,
                                price = crypto.price
                            )
                        }
                        error.value = ""
                        isLoading.value = false
                        cryptoList.value += cryptoListItem
                    }
                    is Resource.Error -> {
                        cryptoList.value = listOf()
                        error.value = result.message!!
                        isLoading.value = false
                    }
                    is Resource.Loading -> TODO()
                }
        }
    }

    fun searchCryptoList(query: String) {
        val listToSearch = if (isSearchStarting) {
            cryptoList.value
        } else {
            initialCryptoList
        }

        // Default because cpu intensive operation like big list filtering
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                cryptoList.value = initialCryptoList
                isSearchStarting = true
                return@launch
            }

            val results = listToSearch.filter{
                // ignore case to make search case insensitive
                it.currency.contains(query.trim(), ignoreCase = true)
            }
            
            if (isSearchStarting) {
                initialCryptoList = cryptoList.value
                isSearchStarting = false
            }
        }
    }

}