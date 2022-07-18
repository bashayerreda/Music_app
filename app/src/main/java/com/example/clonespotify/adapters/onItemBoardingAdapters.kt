package com.example.clonespotify.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clonespotify.OnBoardingItem
import com.example.clonespotify.R
import kotlinx.android.synthetic.main.onboarding_items.view.*

class onItemBoardingAdapters(private val onBoardingItemList: List<OnBoardingItem>) : RecyclerView.Adapter<onItemBoardingAdapters.OnBoardingItemViewHolder>() {
    inner class OnBoardingItemViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val onImageBoarding = view.img_onBoarding
        private val titletext = view.text_title
        private val descriptionText = view.text_desc
        fun onBind(onBoardingItem: OnBoardingItem){
            onImageBoarding.setImageResource(onBoardingItem.onBoardingImage)
            titletext.text = onBoardingItem.title
            descriptionText.text = onBoardingItem.description

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingItemViewHolder {
        return OnBoardingItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
               R.layout.onboarding_items,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OnBoardingItemViewHolder, position: Int) {
        holder.onBind(onBoardingItemList[position])
    }

    override fun getItemCount(): Int {
      return  onBoardingItemList.size
    }

}