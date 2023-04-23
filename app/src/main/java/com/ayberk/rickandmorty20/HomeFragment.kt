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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayberk.rickandmorty20.adapter.CharacterAdapter
import com.ayberk.rickandmorty20.adapter.LocationAdapter
import com.ayberk.rickandmorty20.databinding.FragmentHomeBinding
import com.ayberk.rickandmorty20.models.AnaCharacter.SingilurCharacter
import com.ayberk.rickandmorty20.prefs.SessionManager
import com.ayberk.rickandmorty20.viewmodel.HomePageViewModel

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
    lateinit var progressDialog: ProgressDialog
    private var selectedPosition = 0

    private var lastCharId = ""
    var charId: String="0"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        if (sessionManager.getIsFirstRun())
            sessionManager.setIsFirstRun(false)

        progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle("Lütfen Bekleyin")
        progressDialog.setMessage("Yükleniyor...")
        progressDialog.setCancelable(false) // blocks UI interaction
        progressDialog.show()




        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        fetchCharacters(charId)
        initRecyclerViews()


        viewModel.getObserverLiveData().observe(viewLifecycleOwner, object : Observer<SingilurCharacter> {
            override fun onChanged(t: SingilurCharacter?) {

                if (t != null) {
                    characterAdapter.setLists(t)

                }
                if (lastCharId != charId) {
                    fetchCharacters(charId)
                    lastCharId = charId
                }
                lastCharId = "0"
                charId = "0"
            }

        })

        viewModel.getLocationData().observe(viewLifecycleOwner, object : Observer<com.ayberk.rickandmorty20.models.LocationX> {
            override fun onChanged(t: com.ayberk.rickandmorty20.models.LocationX?) {

                if (t != null) {
                    val btnLocation = view.findViewById<Button>(R.id.locaitonbtn)
                    arguments?.let {

                        val locationPosition = HomeFragmentArgs.fromBundle(it).locationId
                        locationList = t.results[locationPosition]

                        for (residentUrl in t.results[locationPosition].residents) {
                            val uri = Uri.parse(residentUrl)
                            val id = uri.lastPathSegment
                            charId += id + ","
                        }
                    }
                    locationAdapter.setLists(t.results)
                }
                progressDialog.hide()
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


    fun fetchCharacters(charId : String) {
        CoroutineScope(Dispatchers.Main).launch {

            val job1: Deferred<Unit> = async {
                viewModel.loadCharacterData(charId)

            }
            val job2: Deferred<Unit> = async {
                viewModel.loadLocationData("")

            }
            job1.await()
            job2.await()

        }
    }
}
