package com.ayberk.rickandmorty20

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ayberk.rickandmorty20.databinding.FragmentSplashBinding
import com.ayberk.rickandmorty20.prefs.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    @Inject
    lateinit var sessionManager: SessionManager
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater,container,false)
        val view = binding.root

        if (sessionManager.getIsFirstRun()) {
            binding.txtwelcome.text = "Welcome"
        }
        else{
            binding.txtwelcome.text = "Hello"
        }

        Handler(Looper.getMainLooper()).postDelayed({

                 val action = SplashFragmentDirections.actionSplashFragmentToHomeFragment(0)
                findNavController().navigate(action)


        },3000)

        return view
    }

}
