package com.example.traindelay.adapter
/*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.traindelay.R
import com.example.traindelay.model.Route

class RouteAdapter(private var routes: List<Route>):
        RecyclerView.Adapter<RouteAdapter.RouteHolder>() {

    class RouteHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val departure: TextView
        val delay: TextView
        val trainRoute: TextView
        init {
            departure = itemView.findViewById(R.id.departureHour)
            delay = itemView.findViewById(R.id.delayInMinutes)
            trainRoute = itemView.findViewById(R.id.trainRoute)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.route_item, parent, false)
        return RouteHolder(view)
    }

    override fun onBindViewHolder(holder: RouteHolder, position: Int) {
        val current: Route = routes.get(position)
        holder.departure.text = current.departure
        holder.trainRoute.text = current.name // TODO(remember to change name to route)
        holder.delay.text = current.delay

    }

    override fun getItemCount(): Int {
        return routes.size
    }

    fun setRoutes(routes: List<Route>){
        this.routes = routes
        notifyDataSetChanged()
    }

}*/
