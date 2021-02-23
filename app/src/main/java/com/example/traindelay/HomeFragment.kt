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
import com.example.traindelay.utils.Status
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
        val listOfStations = mutableListOf<String>()
        val repository = Repository()
        val viewModelFactory = HomeViewModelFactory(repository)
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        homeViewModel.getStations()
        homeViewModel.stations.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    it.data?.let { stations ->
                        stations.forEach {
                            listOfStations.add(it.name)
                        }}
                    ArrayAdapter( requireActivity(), android.R.layout.simple_dropdown_item_1line,
                        listOfStations)
                        .also { adapter ->
                            binding.startStation.setAdapter(adapter)
                            binding.endStation.setAdapter(adapter)
                        }
                }
                Status.ERROR -> {

                    Toast.makeText(activity,"Coś poszło nie tak", Toast.LENGTH_LONG).show()
                    Log.e("Failure", it.msg.toString())
                }
            }
        })

        binding.layoutContainer.setOnClickListener{
            it.hideKeyboard()}

        binding.reverseButton.setOnClickListener{
            reverseTextViews() }

        binding.searchBtn.setOnClickListener{
            it.hideKeyboard()
            val isInputCorrect = verifyStationName(listOfStations)
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
    private fun verifyStationName(list: List<String>): Boolean{
        val from = binding.startStation.text.toString()
        val to = binding.endStation.text.toString()

        if(list.contains(from) && list.contains(to)) return true
        return false
    }
    private fun reverseTextViews(){
        val tmp = binding.startStation.text
        binding.startStation.text = binding.endStation.text
        binding.endStation.text = tmp
    }

}