package com.example.traindelay
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traindelay.adapter.RouteAdapter
import com.example.traindelay.databinding.FragmentRouteBinding
import com.example.traindelay.model.Route
import com.example.traindelay.repository.Repository
import com.example.traindelay.utils.Status
import org.w3c.dom.Text

class RouteFragment :Fragment() {
    private lateinit var binding: FragmentRouteBinding
    private val args: RouteFragmentArgs by navArgs()
    private lateinit var routeViewModel: RouteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_route, container, false)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRouteBinding.bind(view)
        binding.startStationTextView.text = args.startStationName
        binding.endStationTextView.text = args.endStationName

        val recyclerView: RecyclerView = binding.RoutesRecyclerView

        recyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        recyclerView.setHasFixedSize(true)
        val adapter = RouteAdapter()
        recyclerView.adapter = adapter

        //Api call
        val repository = Repository()
        val viewModelFactory = RouteViewModelFactory(repository)
        routeViewModel = ViewModelProvider(this, viewModelFactory).get(RouteViewModel::class.java)
        routeViewModel.getRoutes(args.startStationName,args.endStationName)
        routeViewModel.listOfRoutes.observe(viewLifecycleOwner, Observer {
                when(it.status){
                    Status.SUCCESS -> {
                        it.data?.let { routes -> setupData(routes, adapter)}
                    }
                    Status.ERROR ->{
                        Toast.makeText(activity, it.msg, Toast.LENGTH_LONG).show()
                    }
                }
        })
    }
        private fun setupData(routes: List<Route>, adapter: RouteAdapter){
            if(routes.isEmpty()){
                binding.RoutesRecyclerView.visibility = View.GONE
                binding.emptyView.visibility = View.VISIBLE
            }
            else{
                adapter.setRouteCards(routes)
                binding.emptyView.visibility = View.GONE
                binding.RoutesRecyclerView.visibility = View.VISIBLE
            }
        }
}
