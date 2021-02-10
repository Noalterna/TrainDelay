package com.example.traindelay.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.traindelay.R
import com.example.traindelay.model.Route
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class RouteAdapter: RecyclerView.Adapter<RouteAdapter.RouteHolder>() {
    private var routes = listOf<Route>()

    class RouteHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val arrival: TextView
        val delay: TextView
        val trainRoute: TextView
        init {
            arrival = itemView.findViewById(R.id.arrivalHour)
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
        holder.trainRoute.text = current.name // TODO(remember to change name to route)
        val sdf = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z")
        try {
            val date = sdf.parse(current.arrival)
            holder.arrival.text = date?.time.toString()
        }
        catch (e: Exception) {e.printStackTrace()}


        setTrainDelay(holder, current.delay)
    }
    override fun getItemCount(): Int {
        return routes.size
    }

    fun setRouteCards(routeCards: List<Route>){
        this.routes = routeCards
        notifyDataSetChanged()
    }
    fun setTrainDelay(holder: RouteHolder, delay: String){
        if (delay.toInt() == 0) holder.delay.text = ("(${delay} min)")
        else holder.delay.text = ("+(${delay} min)")
    }
}
