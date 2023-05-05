package com.example.comparapp.data

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.util.*
import kotlin.collections.ArrayList

interface FirestoreRepository {
    val dataBase: FirebaseFirestore?
    suspend fun registerNewUser(userId: String): Resource<DocumentReference>
    suspend fun isPremiumUser(userId: String): Resource<String>
    suspend fun changeStatePremiumUser(userId: String, newValue: Boolean): Resource<String>
    suspend fun getProducts(): Resource<QuerySnapshot>
    suspend fun getProductsByCategory(category: String): Resource<QuerySnapshot>
}
