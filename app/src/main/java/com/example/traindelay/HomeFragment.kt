package com.example.traindelay
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
//import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.traindelay.model.Station
import com.example.traindelay.repository.Repository

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        val startStationAutoCompTV: AutoCompleteTextView = view.findViewById(R.id.start_station)
        val endStationAutoCompTV: AutoCompleteTextView = view.findViewById(R.id.end_station)

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
                    startStationAutoCompTV.setAdapter(adapter)
                    endStationAutoCompTV.setAdapter(adapter)
                }
            Log.d("RESPONSE: ", response[0].name) //TODO: delete this
        })

        val btnSearch = view.findViewById(R.id.search_btn) as ImageButton
        btnSearch.setOnClickListener{
            val directions = HomeFragmentDirections.actionHomeFragmentToRouteFragment(
                startStationAutoCompTV.text.toString(),
                endStationAutoCompTV.text.toString())
            findNavController().navigate(directions)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}