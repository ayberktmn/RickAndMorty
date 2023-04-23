package com.ayberk.rickandmorty20

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.ayberk.rickandmorty20.databinding.FragmentDetailsBinding
import com.ayberk.rickandmorty20.viewmodel.CharacterDetailsViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CharacterDetailsViewModel by viewModels()
    private lateinit var navController: NavController
    private val argument: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        navController = Navigation.findNavController(requireActivity(),id)

            binding.txtName.text = argument.args.name
            binding.txtGender.text = argument.args.gender
            binding.txtStatus.text = argument.args.status
            binding.txtOrigin.text = argument.args.origin
            binding.txtSpecy.text = argument.args.species
            binding.txtLocation.text = argument.args.location
            val dateTime = argument.args.created
            val time = dateTime.substring(11, 19)
            binding.txtCreadet.text = argument.args.created.take(10) + ", " + time
            binding.txtEpisodes.text = argument.args.episode
            .map { it.split("/")[5] }
            .joinToString(separator = " ")
             var into = Glide.with(binding.imageChrtrs)
            .load(argument.args.image)
            .into(binding.imageChrtrs)

        binding.backbtn.setOnClickListener {
            navController.popBackStack()
        }
        return view
    }
}