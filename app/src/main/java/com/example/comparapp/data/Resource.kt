package com.example.comparapp.data

sealed class Resource<out R> {
    data class Success<out R>(val result: R): Resource<R>()
    data class Failure(val exception: String): Resource<Nothing>()
}