package com.bengisusahin.cryptocrazy.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.bengisusahin.cryptocrazy.model.Crypto
import com.bengisusahin.cryptocrazy.util.Resource
import com.bengisusahin.cryptocrazy.viewmodel.CryptoDetailViewModel
import kotlinx.coroutines.launch

@Composable
fun CryptoDetailScreen(
    id:String,
    price:String,
    navController: NavController,
    viewModel: CryptoDetailViewModel = hiltViewModel()
) {

    /*
    // Step 1 -> Wrong
    val scope = rememberCoroutineScope()

    var cryptoItem : Resource<Crypto> = Resource.Loading()
    /*
    var cryptoItem by remember {
        mutableStateOf<Resource<Crypto>>(Resource.Loading())
    }
     */

    // compositionun baştan devamlı çalışmasını sağlar
    scope.launch {
        cryptoItem = viewModel.getCryptoDetail()
        println(cryptoItem.data)
    }

     */


    /*
    // Step 2 -> Better
    var cryptoItem by remember {
        mutableStateOf<Resource<Crypto>>(Resource.Loading())
    }

    LaunchedEffect(key1 = Unit) {
        cryptoItem = viewModel.getCryptoDetail()
        println(cryptoItem.data)
    }
     */

    // Step 3 -> Best
    val cryptoItem by produceState<Resource<Crypto>>(initialValue = Resource.Loading()) {
        value = viewModel.getCryptoDetail()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            when(cryptoItem){
                is Resource.Success -> {
                    // zaten tek eleman olcak [0]
                    val selectedCrypto = cryptoItem.data!![0]
                    Text(text = selectedCrypto.name,
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                        )
                    
                    Image(
                        painter = rememberImagePainter(data = selectedCrypto.logo_url),
                        contentDescription = selectedCrypto.name,
                        modifier = Modifier
                            .padding(16.dp)
                            .size(200.dp, 200.dp)
                            .clip(shape = CircleShape)
                            .border(2.dp, color = Color.Gray, shape = CircleShape)
                    )

                    Text(text = price,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        textAlign = TextAlign.Center
                    )
                }
                is Resource.Error -> {
                    Text(text = cryptoItem.message!!)
                }
                is Resource.Loading -> {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
    
}