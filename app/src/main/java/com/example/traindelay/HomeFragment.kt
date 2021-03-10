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
import com.example.traindelay.utils.Status

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.bind(view)

        val listOfStations = mutableListOf<String>()
        val repository = Repository()
        val viewModelFactory = HomeViewModelFactory(repository)
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        homeViewModel.getStations()
        homeViewModelObserver(listOfStations, view)

        binding.layoutContainer.setOnClickListener { Helper.hideKeyboard(it)}

        binding.reverseButton.setOnClickListener { reverseTextViews()}

        binding.searchBtn.setOnClickListener {
            Helper.hideKeyboard(it)
            if(verifyStationName(listOfStations)){
                val directions = HomeFragmentDirections.actionHomeFragmentToRouteFragment(
                    binding.startStation.text.toString(),
                    binding.endStation.text.toString())
                findNavController().navigate(directions)
            }
            else Toast.makeText(activity, "Wybierz stację z listy", Toast.LENGTH_SHORT).show()
        }
        return view
    }

    private fun homeViewModelObserver(listOfStations: MutableList<String>, view: View) : MutableList<String> {
        homeViewModel.stations.observe(viewLifecycleOwner, {
            when(it.status){
                Status.SUCCESS -> {
                    it.data?.let { stations ->
                        listOfStations.clear()
                        stations.forEach { station -> listOfStations.add(station.name)}
                    }
                    ArrayAdapter( requireActivity(), android.R.layout.simple_dropdown_item_1line,
                        listOfStations)
                        .also { adapter ->
                            binding.startStation.setAdapter(adapter)
                            binding.endStation.setAdapter(adapter)
                        }
                }
                Status.ERROR -> {
                    Helper.hideKeyboard(view)
                    Toast.makeText(activity,it.msg, Toast.LENGTH_LONG).show()
                }
            }
        })
        return listOfStations
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