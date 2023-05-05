package com.example.comparapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comparapp.data.AuthRepository
import com.example.comparapp.data.FirestoreRepository
import com.example.comparapp.data.Resource
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(private val authRepository: AuthRepository, private val firestoreRepository: FirestoreRepository) : ViewModel() {

    private val _productsFlow = MutableLiveData<Resource<QuerySnapshot>?>()
    val productsFlow: LiveData<Resource<QuerySnapshot>?> = _productsFlow


    private val _productsFilterByCategoryFlow = MutableLiveData<Resource<QuerySnapshot>?>()
    val productsFilterByCategoryFlow: LiveData<Resource<QuerySnapshot>?> = _productsFilterByCategoryFlow


    fun getProducts() = viewModelScope.launch {
        val result = firestoreRepository.getProducts()
        _productsFlow.value = result
    }

    fun getProductsCategory(category: String) = viewModelScope.launch {
        _productsFilterByCategoryFlow.value = null
        val result = firestoreRepository.getProductsByCategory(category)
        _productsFilterByCategoryFlow.value = result
    }

}
