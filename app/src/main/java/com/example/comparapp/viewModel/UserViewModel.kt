package com.example.comparapp.viewModel

import com.example.comparapp.data.AuthRepository
import com.example.comparapp.data.FirestoreRepository
import com.example.comparapp.data.Resource

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val authRepository: AuthRepository, private val firestoreRepository: FirestoreRepository) : ViewModel() {

    private val _userStatePremiumUser = MutableLiveData<Resource<String>>()
    val userStatePremiumUser: LiveData<Resource<String>> = _userStatePremiumUser

    private val _loginFlow = MutableLiveData<Resource<FirebaseUser>?>()
    val loginFlow: LiveData<Resource<FirebaseUser>?> = _loginFlow

    private val _signupFlow = MutableLiveData<Resource<FirebaseUser>?>()
    val signupFlow: LiveData<Resource<FirebaseUser>?> = _signupFlow

    val currentUser: FirebaseUser?
        get() = authRepository.currentUser

    init {
        if(authRepository.currentUser != null){
            _loginFlow.value = Resource.Success(authRepository.currentUser!!)
            loadDataUser()
        }
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        val result = authRepository.login(email, password)
        _loginFlow.value = result
        loadDataUser()
    }

    fun register(name: String, email: String, password: String) = viewModelScope.launch {
        val result = authRepository.signup(name, email, password)
        _signupFlow.value = result
    }

    fun logout() {
        authRepository.logout()
        _loginFlow.value = null
        _signupFlow.value = null
    }

    fun resetFlow(){
        _loginFlow.value = null
        _signupFlow.value = null
    }

    fun getNameCurrentUser(): String? {
        return currentUser?.displayName
    }

    fun getEmailCurrentUser(): String? {
        return currentUser?.email
    }

    fun getStatePremiumUser() = viewModelScope.launch {
        if(currentUser != null){
            val result = firestoreRepository.isPremiumUser(currentUser!!.uid)
            _userStatePremiumUser.value = result

        }
    }

    fun loadDataUser() = viewModelScope.launch {
        getStatePremiumUser()
    }

    fun setNameUser(name: String) = viewModelScope.launch {
        val result = authRepository.setNameUser(name)
    }

    fun setStatePremiumUser(value: Boolean) = viewModelScope.launch {
        val result = firestoreRepository.changeStatePremiumUser(currentUser!!.uid, value)
        _userStatePremiumUser.value = result
    }

    fun changePassword() {
        if (currentUser != null) {
            authRepository.sendChangePasswordEmail(currentUser!!.email!!)
        }
    }


}