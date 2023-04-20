package com.ayberk.rickandmorty20

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ayberk.rickandmorty20.databinding.FragmentDetailsBinding
import com.ayberk.rickandmorty20.models.AnaCharacter.SingilurCharacter
import com.ayberk.rickandmorty20.models.AnaCharacter.SingilurCharacterItem
import com.ayberk.rickandmorty20.viewmodel.CharacterDetailsViewModel
import com.ayberk.rickandmorty20.viewmodel.HomePageViewModel


import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CharacterDetailsViewModel by viewModels()
    lateinit var characterDetailList : SingilurCharacterItem
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        navController = Navigation.findNavController(requireActivity(),id)



        binding.backbtn.setOnClickListener {
            navController.popBackStack()
        }
        var last=""
        viewModel.getObserverLiveData().observe(viewLifecycleOwner, object : Observer<SingilurCharacter> {

            override fun onChanged(t: SingilurCharacter?) {

                if (t != null) {

                    arguments?.let {

                        val position = DetailsFragmentArgs.fromBundle(it).characterPosition

                        characterDetailList = t[position]

                            binding.txtStatus.text = characterDetailList.status
                        binding.txtGender.text = characterDetailList.gender
                        binding.txtSpecy.text = characterDetailList.species
                        binding.txtName.text = characterDetailList.name
                        binding.txtOrigin.text = characterDetailList.origin.name
                        binding.txtLocation.text = characterDetailList.location.name
                        val dateTime = characterDetailList.created
                        val time = dateTime.substring(11, 19)
                        binding.txtCreadet.text = characterDetailList.created.take(10) + ", " + time
                        binding.txtEpisodes.text = characterDetailList.episode
                            .map { it.split("/")[5] }
                            .joinToString(separator = " ")
                        var into = Glide.with(binding.imageChrtrs)
                            .load(characterDetailList.image)
                            .into(binding.imageChrtrs)

                    }
                }

            }
        })

        return view
    }

    fun fetchCharacters(id:Int){

        CoroutineScope(Dispatchers.Main).launch {

            val job1 : Deferred<Unit> = async {
                viewModel.loadCharacterData(id.toString())
            }
            job1.await()
        }
    }

}