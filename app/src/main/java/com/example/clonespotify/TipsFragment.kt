package com.example.clonespotify

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricConstants.ERROR_USER_CANCELED
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.clonespotify.adapters.onItemBoardingAdapters
import kotlinx.android.synthetic.main.fragment_tips.*
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt

class TipsFragment : Fragment() {
    lateinit var onItemBoardingAdapters: onItemBoardingAdapters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnBoardingItems()
        prepareBiometric()
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
    private fun prepareBiometric(){
        val biometricManager = BiometricManager.from(activity!!.applicationContext)

        //Some more information on biometric support
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                Log.d("MY_APP_TAG", "App can authenticate using biometrics.")
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                Log.e("MY_APP_TAG", "No biometric features available on this device.")
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                Log.e("MY_APP_TAG", "Biometric features are currently unavailable.")
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                Log.e("MY_APP_TAG", "The user hasn't associated " +
                        "any biometric credentials with their account.")
        }

        if (biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS){
            Log.d("MainActivity","Device supports biometric auth")

            val executor = ContextCompat.getMainExecutor(activity!!.applicationContext)

            val callback = object: BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Log.d("MainActivity", "$errorCode :: $errString")
                    if (errorCode == BiometricPrompt.ERROR_USER_CANCELED) {
                        activity!!.finish()
                    }
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Log.d("MainActivity","Authentication failed")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Log.d("MainActivity","Authentication was successful")
                }
            }

            val biometricPrompt = BiometricPrompt(this, executor, callback)
            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setDescription("User needs to be authenticated before using the app just choose the method you want to get started")
                .setDeviceCredentialAllowed(true)
                .build()

            biometricPrompt.authenticate(promptInfo)
        }

    }

}