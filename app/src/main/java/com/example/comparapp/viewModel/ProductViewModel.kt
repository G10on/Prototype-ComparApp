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

    private val _userStatePremiumUser = MutableLiveData<Resource<String>>()
    val userStatePremiumUser: LiveData<Resource<String>> = _userStatePremiumUser

    private val _loginFlow = MutableLiveData<Resource<FirebaseUser>?>()
    val loginFlow: LiveData<Resource<FirebaseUser>?> = _loginFlow

    private val _productsFlow = MutableLiveData<Resource<QuerySnapshot>?>()
    val productsFlow: LiveData<Resource<QuerySnapshot>?> = _productsFlow



    fun getProducts() = viewModelScope.launch {
        val result = firestoreRepository.getProducts()
        _productsFlow.value = result
    }

}
