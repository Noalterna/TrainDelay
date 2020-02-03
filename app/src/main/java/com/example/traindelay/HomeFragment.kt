package com.example.traindelay
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

//        auto-complete of stations
        val startStation: AutoCompleteTextView = view.findViewById(R.id.start_station)
        val endStation: AutoCompleteTextView = view.findViewById(R.id.end_station)
        val stations: Array<out String> = resources.getStringArray(R.array.stations)
        ArrayAdapter<String>(requireActivity() , android.R.layout.simple_list_item_1, stations).also { adapter ->
            startStation.setAdapter(adapter)
        }
        ArrayAdapter<String>(
            requireActivity() ,
            android.R.layout.simple_list_item_1,
            stations
        ).also { adapter -> endStation.setAdapter(adapter) }
        return view
    }
}