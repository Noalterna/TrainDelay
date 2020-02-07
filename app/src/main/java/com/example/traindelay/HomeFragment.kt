package com.example.traindelay
import android.content.Context
import android.content.Intent
import android.hardware.input.InputManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.content_main.*

import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.newFixedThreadPoolContext
import javax.net.ssl.SSLContext

class HomeFragment : Fragment() {
    lateinit var sendStation: SendStation
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
        ArrayAdapter<String>( requireActivity(),
            android.R.layout.simple_list_item_1,
            stations)
            .also { adapter ->
            startStation.setAdapter(adapter)
        }
        ArrayAdapter<String>(
            requireActivity() ,
            android.R.layout.simple_list_item_1,
            stations
        ).also { adapter -> endStation.setAdapter(adapter) }

        val btnSearch = view.findViewById(R.id.search_btn) as ImageButton
        val inputsLayout: RelativeLayout = view.findViewById(R.id.stations_input_container)
        btnSearch.setOnClickListener{
            sendStation.sendData(startStation.text.toString().trim())
            val inputMethManager = this.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethManager.hideSoftInputFromWindow(inputsLayout.windowToken,0)
        }
        return view

    }
    interface SendStation{
        fun sendData(station: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            sendStation = activity as SendStation
        } catch (e: ClassCastException ){
            throw java.lang.ClassCastException("Error in retrieving data.")
        }
    }
}