package com.example.appaboutplants.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appaboutplants.OnItemClickListener
import com.example.appaboutplants.R
import com.example.appaboutplants.databinding.FragmentPlantListBinding
import com.example.appaboutplants.databinding.ItemPlantBinding
import com.example.appaboutplants.domain.PlantDeserializer
import com.example.appaboutplants.domain.PlantRepository
import com.example.appaboutplants.data.models.Plant
import com.example.appaboutplants.data.models.PlantApiService
import com.google.gson.GsonBuilder
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlantListFragment : Fragment(R.layout.fragment_plant_list) {

    private lateinit var binding: FragmentPlantListBinding
    private lateinit var viewModel: PlantListViewModel

    private val plantListAdapter = PlantListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlantListBinding.bind(view)
        viewModel = ViewModelProvider(this, PlantListViewModelFactory()).get(PlantListViewModel::class.java)
        setupRecyclerView()
        observeLatestPlants()
        fetchLatestPlants()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = plantListAdapter
        }

    }

    private fun observeLatestPlants() {
        viewModel.latestPlant.observe(viewLifecycleOwner) { books ->
            plantListAdapter.submitList(books)
        }
    }

    private fun fetchLatestPlants() {
        viewModel.fetchLatestPLants()
    }

    inner class PlantListAdapter : ListAdapter<Plant, PlantViewHolder>(DiffCallback) {

        private var onItemClickListener: OnItemClickListener? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plant, parent, false)
            return PlantViewHolder(view)
        }

        fun setOnItemClickListener(listener: OnItemClickListener) {
            onItemClickListener = listener
        }

        override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
            val plant = getItem(position)
            holder.bind(plant)

            holder.itemView.setOnClickListener {
                val plantId = getItem(position).id
                onItemClickListener?.onItemClick(plantId)
                //val action = ListFragmentDirections.actionListFragmentToDetailFragment(argKey = "arg_value")
                val action = PlantListFragmentDirections.actionListFragmentToDetailFragment(plantId = plantId)
                this@PlantListFragment.findNavController().navigate(action)
            }

        }
    }

    inner class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemPlantBinding.bind(itemView)

        fun bind(plant: Plant) {
            binding.title.text = plant.commonName
            binding.author.text = plant.scientificName
            Glide.with(binding.coverImage)
                .load(plant.coverImageUrl)
                .placeholder(R.drawable.preload_icon)
                .into(binding.coverImage)
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Plant>() {
        override fun areItemsTheSame(oldItem: Plant, newItem: Plant) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Plant, newItem: Plant) = oldItem == newItem
    }

    inner class PlantListViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val retrofit = Retrofit.Builder()
                .baseUrl("https://perenual.com/api/")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().apply {
                    registerTypeAdapter(Plant::class.java, PlantDeserializer())
                }.create()))
                .build()
            val plantApiService = retrofit.create(PlantApiService::class.java)
            val plantRepository = PlantRepository(plantApiService)
            return PlantListViewModel(plantRepository) as T
        }
    }
}
