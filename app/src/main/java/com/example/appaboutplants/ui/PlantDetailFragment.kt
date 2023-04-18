package com.example.appaboutplants.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.appaboutplants.R
import com.example.appaboutplants.databinding.FragmentPlantDetailBinding
import com.example.appaboutplants.domain.PlantRepository
import com.example.appaboutplants.models.PlantApiService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlantDetailFragment : Fragment(R.layout.fragment_plant_detail) {

    private lateinit var binding: FragmentPlantDetailBinding
    private lateinit var viewModel: PlantDetailViewModel
    var plantId = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlantDetailBinding.bind(view)
        viewModel = ViewModelProvider(this, PlantDetailViewModelFactory()).get(PlantDetailViewModel::class.java)
        arguments?.let {
            plantId = it.getInt("plantId")
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        observePlant(plantId)
    }

    private fun observePlant(plantId: Int) {
        lifecycleScope.launch {
            fetchPlant(plantId)
        }
        viewModel.plant.observe(viewLifecycleOwner) { plant ->
            if (plant != null) {
                binding.title.text = plant.commonName
                binding.author.text = plant.scientificName
                binding.description.text = plant.description
                binding.type.append(plant.type)
                binding.description.append(plant.description)
                binding.dimension.append(plant.dimension)
                binding.cycle.append(plant.cycle)
                binding.watering.append(plant.watering)
                Glide.with(binding.coverImage)
                    .load(plant.coverImageUrl)
                    .placeholder(R.drawable.preload_icon)
                    .into(binding.coverImage)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private suspend fun fetchPlant(plantId: Int) {
        viewModel.fetchPlantById(plantId)
    }

    inner class PlantDetailViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://perenual.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val plantApiService = retrofit.create(PlantApiService::class.java)
            val plantRepository = PlantRepository(plantApiService)

            return PlantDetailViewModel(plantRepository) as T
        }
    }
}