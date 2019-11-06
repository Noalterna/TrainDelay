package com.example.traindelay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startStation = findViewById(R.id.start_station) as AutoCompleteTextView
        val endStation = findViewById(R.id.end_station) as AutoCompleteTextView
        val stations: Array<out String> = resources.getStringArray(R.array.stations)
        ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stations).also {adapter ->
            startStation.setAdapter(adapter)}
        ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,stations).also {adapter -> endStation.setAdapter(adapter)}
    }
}
