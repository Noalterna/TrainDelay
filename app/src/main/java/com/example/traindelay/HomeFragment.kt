package com.example.traindelay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.traindelay.databinding.FragmentHomeBinding
import com.example.traindelay.repository.Repository
import com.example.traindelay.utils.Helper
import com.example.traindelay.utils.Resource

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var listOfStations: MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        listOfStations = mutableListOf()

        setupViewModel()
        homeViewModelObserver()

        binding.layoutContainer.setOnClickListener { Helper.hideKeyboard(it) }

        binding.reverseButton.setOnClickListener { reverseTextViews() }

        binding.searchBtn.setOnClickListener {
            Helper.hideKeyboard(it)
            setupSearchBtn()
        }
    }

    private fun setupSearchBtn() {
        if(verifyStationName(listOfStations)){
            val directions = HomeFragmentDirections.actionHomeFragmentToRouteFragment(
                binding.startStation.text.toString(),
                binding.endStation.text.toString())
            findNavController().navigate(directions)
        }
        else Toast.makeText(activity, R.string.chooseStationFromList, Toast.LENGTH_SHORT).show()
    }

    private fun setupViewModel() {
        val repository = Repository()
        val viewModelFactory = HomeViewModelFactory(repository)
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        homeViewModel.getStations()
    }

    private fun homeViewModelObserver(){
        homeViewModel.stations.observe(viewLifecycleOwner, {
            when(it){
                is Resource.Success -> {
                    binding.homeError.visibility = View.GONE
                    it.data.let { stations ->
                        listOfStations.clear()
                        stations.forEach { station -> listOfStations.add(station.name) }
                    }
                    ArrayAdapter(requireActivity(), android.R.layout.simple_dropdown_item_1line,
                        listOfStations)
                        .also { adapter ->
                            binding.startStation.setAdapter(adapter)
                            binding.endStation.setAdapter(adapter) }
                }
                is Resource.Error -> {
                    Helper.hideKeyboard(binding.layoutContainer)
                    Helper.showErrorMsg(it.error, binding.homeError)
                }
            }
        })
    }
    private fun verifyStationName(list: List<String>): Boolean {
        val from = binding.startStation.text.toString()
        val to = binding.endStation.text.toString()
        if(list.contains(from) && list.contains(to)) return true
        return false
    }
    private fun reverseTextViews() {
        val tmp = binding.startStation.text
        binding.startStation.text = binding.endStation.text
        binding.endStation.text = tmp
    }

}