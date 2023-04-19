package com.ayberk.rickandmorty20

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ayberk.rickandmorty20.databinding.FragmentDetailsBinding
import com.ayberk.rickandmorty20.databinding.FragmentHomeBinding
import com.ayberk.rickandmorty20.models.Character
import com.ayberk.rickandmorty20.models.ResultX
import com.ayberk.rickandmortyy.viewmodel.HomePageViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomePageViewModel by viewModels()
    lateinit var characterDetailList : com.ayberk.rickandmorty20.models.Result

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        fetchCharacters()
        binding.backbtn.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
        var last=""
        viewModel.getObserverLiveData().observe(viewLifecycleOwner, object : Observer<Character> {

            override fun onChanged(t: Character?) {

                if (t != null) {

                    arguments?.let {

                        val position = DetailsFragmentArgs.fromBundle(it).characterPosition

                    characterDetailList = t.results[position]
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

    fun fetchCharacters(){

        CoroutineScope(Dispatchers.Main).launch {

            val job1 : Deferred<Unit> = async {
                viewModel.loadCharacterData("1")
            }
            job1.await()
        }
    }

}