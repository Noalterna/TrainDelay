package com.example.traindelay
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_connections.*

class ConnectionsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_connections, container, false)
        val startStationTxTView: TextView = view.findViewById(R.id.startStationTextView)

        startStationTxTView.text = arguments?.getString("inputStartStation")

        return view
    }
}
