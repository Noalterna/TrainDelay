package com.example.traindelay
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.traindelay.databinding.FragmentConnectionsBinding

class ConnectionsFragment :Fragment() {
    private lateinit var binding: FragmentConnectionsBinding
    private val args: ConnectionsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_connections, container, false)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentConnectionsBinding.bind(view)

        binding.startStationTextView.text = args.startStationName
        binding.endStationTextView.text = args.endStationName
    }
}
