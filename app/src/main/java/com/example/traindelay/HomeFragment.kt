package com.example.traindelay

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.traindelay.databinding.FragmentHomeBinding
import com.example.traindelay.repository.Repository
import java.lang.Exception

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

        // API
        val listOfStations2 = mutableListOf<String>()
        val repository = Repository()
        val viewModelFactory = HomeViewModelFactory(repository)
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        homeViewModel.getStations()
        homeViewModel._response.observe(viewLifecycleOwner, Observer { response ->
            val listOfStations = mutableListOf<String>()
            response.forEach {
                listOfStations.add(it.name)
                listOfStations2.add(it.name)
            }
            ArrayAdapter( requireActivity(), android.R.layout.simple_dropdown_item_1line,
                listOfStations)
                .also { adapter ->
                    binding.startStation.setAdapter(adapter)
                    binding.endStation.setAdapter(adapter)
                }
            Log.d("RESPONSE: ", response[0].name)
        })

        binding.layoutContainer.setOnClickListener{
            it.hideKeyboard()
        }

        binding.reverseButton.setOnClickListener{
            val tmp = binding.startStation.text
            binding.startStation.text = binding.endStation.text
            binding.endStation.text = tmp
        }

        binding.searchBtn.setOnClickListener{
            it.hideKeyboard()
            val isInputCorrect = verifyStationName(
                binding.startStation.text.toString(),
                binding.endStation.text.toString(),
                listOfStations2)
            Log.e("lista", listOfStations2.size.toString())
            if(isInputCorrect){
                val directions = HomeFragmentDirections.actionHomeFragmentToRouteFragment(
                    binding.startStation.text.toString(),
                    binding.endStation.text.toString())
                findNavController().navigate(directions)
            }
            else Toast.makeText(activity, "Wybierz stację z listy", Toast.LENGTH_SHORT).show()
        }
        return view
    }

    fun View.hideKeyboard(){
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken,0)
    }
    fun verifyStationName(from: String, to: String, list: List<String>): Boolean{
        if(list.contains(from) && list.contains(to)) return true
        return false
    }
}