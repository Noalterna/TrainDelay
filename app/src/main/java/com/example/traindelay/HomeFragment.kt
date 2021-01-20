package com.example.traindelay
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        val startStationAutoCompTV: AutoCompleteTextView = view.findViewById(R.id.start_station)
        val endStationAutoCompTV: AutoCompleteTextView = view.findViewById(R.id.end_station)
        val stations: Array<out String> = resources.getStringArray(R.array.stations)
        ArrayAdapter<String>( requireActivity(), android.R.layout.simple_dropdown_item_1line,
            stations)
            .also { adapter ->
                startStationAutoCompTV.setAdapter(adapter)
                endStationAutoCompTV.setAdapter(adapter)
        }
        val btnSearch = view.findViewById(R.id.search_btn) as ImageButton
        btnSearch.setOnClickListener{
            val directions = HomeFragmentDirections.actionHomeFragmentToConnectionsFragment(
                startStationAutoCompTV.text.toString(),
                endStationAutoCompTV.text.toString())
            findNavController().navigate(directions)
        }
        return view
    }
}