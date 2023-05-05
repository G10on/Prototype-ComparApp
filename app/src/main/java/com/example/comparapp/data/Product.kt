package com.example.comparapp.data

data class Product(
    var brand: String? = null,
    var category: String? = null,
    var discount: Double? = null,
    var ingredients: String? = null,
    var name: String? = null,
    var nutritionalValue: Map<String, Double>? = null,
    var pricePerUnit: Double? = null,
    var supermarket: String? = null,
    var urlPhoto: String? = null,
    var supermarketLogo: String? = null
) : java.io.Serializable