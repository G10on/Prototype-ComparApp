package com.example.comparapp.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.comparapp.R
import com.example.comparapp.data.Product
import com.example.comparapp.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.ArrayList
import com.bumptech.glide.Glide


@Suppress("UNCHECKED_CAST")
class CardViewAdapter(productList: List<Product>, isPremium: String): RecyclerView.Adapter<CardViewAdapter.ViewHolder>() {


    private var products: List<Product> = productList
    private var isPremium: String = isPremium

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val productSupermarketLogo: ImageView = itemView.findViewById(R.id.supermarket)
        val product: View = itemView.findViewById(R.id.product)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {

        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.product_item, viewGroup, false)

        return ViewHolder(itemView)

    }


        //val usuario2: LocalUser = documentSnapshot.toObject(LocalUser::class.java)!!

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.productName.text = products[position].name
        holder.productPrice.text = products[position].pricePerUnit.toString()

        Glide.with(holder.itemView.context).asBitmap().load(products[position].urlPhoto)
            .into(holder.productImage)

        Glide.with(holder.itemView.context).asBitmap().load(products[position].supermarketLogo)
            .into(holder.productSupermarketLogo)



        holder.product.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("my_object_key", products[position])

            Navigation.createNavigateOnClickListener(R.id.action_searchFrame_to_productExtraInformationFragment, bundle)
                .onClick(holder.product)
        }








    }

}
