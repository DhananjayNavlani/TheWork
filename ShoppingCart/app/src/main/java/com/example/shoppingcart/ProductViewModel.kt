package com.example.shoppingcart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ProductViewModel: ViewModel() {

    fun fetchAndSaveData(){
        Log.d("ProductViewModel","Before async (in f&s): "+Thread.currentThread().name)
//        viewModelScope.async {
//            //Main Thread
//            Log.d("ProductViewModel","Before fetchFromNW (in f&s): "+Thread.currentThread().name)
//            fetchFromNetwork()
//            Log.d("ProductViewModel","After fetchFromNW (in f&s): "+Thread.currentThread().name)
//        }

        viewModelScope.async(Dispatchers.IO){
            Log.d("ProductViewModel","Before fetchFromNW (in f&s): "+Thread.currentThread().name)
            fetchFromNetwork()
            Log.d("ProductViewModel","After fetchFromNW (in f&s): "+Thread.currentThread().name)
        }
    }

    private suspend fun fetchFromNetwork() {
        withContext(Dispatchers.IO){
            //Non main thread
            delay(1000)
            Log.d("ProductViewModel","After delay in fFN (with context): "+Thread.currentThread().name)
        }
        //Main thread
        Log.d("ProductViewModel","After (with context): "+Thread.currentThread().name)
    }
}