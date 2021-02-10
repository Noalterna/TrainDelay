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
        val repository = Repository()
        val viewModelFactory = HomeViewModelFactory(repository)
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        homeViewModel.getStations()
        homeViewModel._response.observe(viewLifecycleOwner, Observer { response ->
            val listOfStations = mutableListOf<String>()
            response.forEach {
                listOfStations.add(it.name)
            }
            ArrayAdapter( requireActivity(), android.R.layout.simple_dropdown_item_1line,
                listOfStations)
                .also { adapter ->
                    binding.startStation.setAdapter(adapter)
                    binding.endStation.setAdapter(adapter)
                }
            Log.d("RESPONSE: ", response[0].name) //TODO: delete this
        })

        binding.searchBtn.setOnClickListener{
            val directions = HomeFragmentDirections.actionHomeFragmentToRouteFragment(
                binding.startStation.text.toString(),
                binding.endStation.text.toString())
            it.hideKeyboard()
            findNavController().navigate(directions)

        }

        return view
    }
    fun View.hideKeyboard(){
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken,0)

    }
}