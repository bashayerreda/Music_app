package com.example.clonespotify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.clonespotify.adapters.onItemBoardingAdapters
import kotlinx.android.synthetic.main.fragment_tips.*

class TipsFragment : Fragment() {
    lateinit var onItemBoardingAdapters: onItemBoardingAdapters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnBoardingItems()
        textSkip.setOnClickListener {
          findNavController().navigate(R.id.homeFragment)
        }
        btn_getstarted.setOnClickListener {
            findNavController().navigate(R.id.action_tipsFragment_to_homeFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tips, container, false)
    }
    private fun setOnBoardingItems(){
        onItemBoardingAdapters = onItemBoardingAdapters(
            listOf(
                OnBoardingItem(
                    onBoardingImage = R.drawable.songs ,
                    title = "listen to music anywhere",
                    description = "relax and start your music app to listen to your favorite music anywhere"
                ),
                OnBoardingItem(
                    onBoardingImage = R.drawable.person ,
                    title = "be happy ",
                    description = "open your music app now and start your happiness journey"
                ),
                OnBoardingItem(
                    onBoardingImage = R.drawable.people ,
                    title = "share your type of songs ",
                    description = "share music with your friends"
                ),

                )
        )
        val viewPagger = view!!.findViewById<ViewPager2>(R.id.onboardingVp)
        viewPagger.adapter = onItemBoardingAdapters
        if (viewPagger.currentItem +1 < viewPagger.currentItem){
            viewPagger.currentItem +=1
        }
        else{

        }

    }

}