package com.example.comparapp.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.comparapp.R
import com.example.comparapp.data.Product
import kotlin.collections.ArrayList
import com.bumptech.glide.Glide


@Suppress("UNCHECKED_CAST")
class CardViewAdapter(productList: List<Product>, isPremium: Boolean): RecyclerView.Adapter<CardViewAdapter.ViewHolder>() {


    private var products: ArrayList<Product> = productList as ArrayList<Product>
    private var isPremium: Boolean = isPremium

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productFinalPrice: TextView = itemView.findViewById(R.id.product_final_price)
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

    @SuppressLint("ResourceAsColor", "ResourceType")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.productName.text = products[position].name

        var price = products[position].pricePerUnit
        var discount = products[position].discount

        if (discount != .0) {
            if (price != null) {
                var containerLayout = holder.productFinalPrice.parent.parent as View
                var addBtn = containerLayout.findViewById<Button>(R.id.add_to_SL_btn)
                containerLayout.setBackgroundColor(ContextCompat.getColor(containerLayout.context, R.color.disccount))
                addBtn.setBackgroundResource(R.drawable.product_plus_button_disccount)
                holder.productPrice.text = price.toString() + "€/u"
                price -= discount!!
                holder.productPrice.visibility = View.VISIBLE
                holder.productPrice.paintFlags = holder.productFinalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                holder.productPrice.setTypeface(null, Typeface.ITALIC)
                holder.productPrice.setTextColor(R.color.textInfo)
            }
        }

        holder.productFinalPrice.text = price.toString() + "€/u"

        Glide.with(holder.itemView.context).asBitmap().load(products[position].urlPhoto)
            .into(holder.productImage)

        Glide.with(holder.itemView.context).asBitmap().load(products[position].supermarketLogo)
            .into(holder.productSupermarketLogo)


        holder.product.setOnClickListener {
            if (isPremium) {
                Log.e("ES PREMIUM ??", isPremium.toString())

                val bundle = Bundle()
                bundle.putSerializable("product", products[position])

                Navigation.createNavigateOnClickListener(
                    R.id.action_searchFrame_to_productExtraInformationFragment,
                    bundle
                )
                    .onClick(holder.product)

            }
            else {
                Toast.makeText(holder.itemView.context, "Funcionalidad solo para usuarios PREMIUM :)", Toast.LENGTH_SHORT).show()
            }
        }

    }


    fun clear() {
        products.clear()
    }

}
