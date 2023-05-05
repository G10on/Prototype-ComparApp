package com.example.comparapp.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor(private val firebaseFirestore: FirebaseFirestore): FirestoreRepository {
    override val dataBase: FirebaseFirestore?
        get() = firebaseFirestore

    override suspend fun registerNewUser(userId: String): Resource<DocumentReference> {
        val data = hashMapOf(
            "isPremium" to false
        )
        return try{
            val document = dataBase?.collection("users")?.document(userId)!!
            document.set(data).await()
            Resource.Success(document)

        } catch (e: FirebaseFirestoreException){
            e.printStackTrace()
            Log.e("Mel","Falla")
            Resource.Failure(e.message!!)
        }
    }

    override suspend fun isPremiumUser(userId: String): Resource<String>{
        return try {
            val docRef = dataBase?.collection("users")?.document(userId)!!.get().await()
            val data = docRef.data?.get("isPremium").toString()
            Resource.Success(data)

        } catch (e: FirebaseFirestoreException){
            e.printStackTrace()
            Resource.Failure(e.message!!)
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun changeStatePremiumUser(userId: String, newValue: Boolean): Resource<String>{
        return try {
            val docRef = dataBase?.collection("users")?.document(userId)!!.update("isPremium", newValue)
            Resource.Success(newValue.toString())
        } catch (e: FirebaseFirestoreException){
            e.printStackTrace()
            Resource.Failure(e.message!!)
        }

    }

    override suspend fun getProducts(): Resource<QuerySnapshot> {
        return try {
            val querySnapshot = dataBase?.collection("products")?.get()?.await()
            Resource.Success(querySnapshot!!)
        } catch (e: Exception) {
            Resource.Failure(e.message!!)
        }
    }


}


