package com.ayberk.rickandmorty20

import android.app.ProgressDialog
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.lifecycle.DEFAULT_ARGS_KEY
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayberk.rickandmorty20.adapter.CharacterAdapter
import com.ayberk.rickandmorty20.adapter.LocationAdapter
import com.ayberk.rickandmorty20.databinding.FragmentHomeBinding
import com.ayberk.rickandmorty20.models.Location
import com.ayberk.rickandmorty20.prefs.SessionManager
import com.ayberk.rickandmortyy.viewmodel.HomePageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var sessionManager: SessionManager
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var locationAdapter: LocationAdapter
    private val viewModel: HomePageViewModel by viewModels()
    lateinit var locationList : com.ayberk.rickandmorty20.models.ResultX


    var charId: String=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        if (sessionManager.getIsFirstRun())
            sessionManager.setIsFirstRun(false)

        initRecyclerViews()
        fetchCharacters(0)



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)




        viewModel.getObserverLiveData().observe(viewLifecycleOwner, object : Observer<com.ayberk.rickandmorty20.models.Character> {
            override fun onChanged(t: com.ayberk.rickandmorty20.models.Character?) {
                if (t != null) {
                    characterAdapter.setLists(t.results)
                    println(t)
                }
            }

        })
        var charId = 0
        viewModel.getLocationData().observe(viewLifecycleOwner, object : Observer<com.ayberk.rickandmorty20.models.LocationX> {
            override fun onChanged(t: com.ayberk.rickandmorty20.models.LocationX?) {

                if (t != null) {
                    arguments?.let {

                        val locationPosition = HomeFragmentArgs.fromBundle(it).locationId
                        locationList = t.results[locationPosition]
                        for (residentUrl in t.results[locationPosition].residents) {
                            val uri = Uri.parse(residentUrl)
                            val id = uri.lastPathSegment
                            println(id)
                        }

                        if (charId < locationPosition) {
                            fetchCharacters(locationPosition)
                            charId = locationPosition

                        }
                    }

                    locationAdapter.setLists(t.results)

                    println(t)
                }


            }
        })


    }


    fun initRecyclerViews() {

        val lmHorizontal = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val lmVertical = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.rcyclerCharacter.layoutManager = lmVertical
        binding.rcyclerLocation.layoutManager = lmHorizontal
        characterAdapter = CharacterAdapter()
        locationAdapter = LocationAdapter()

        binding.rcyclerLocation.adapter = locationAdapter
        binding.rcyclerCharacter.adapter = characterAdapter


    }


    fun fetchCharacters(charId : Int) {
        CoroutineScope(Dispatchers.Main).launch {

            val job1: Deferred<Unit> = async {
                viewModel.loadCharacterData("")

            }
            val job2: Deferred<Unit> = async {

                viewModel.loadLocationData("")
            }
            job1.await()
            job2.await()

        }
    }
}
