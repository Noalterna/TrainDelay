package com.example.traindelay.adapter


import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.contentcapture.ContentCaptureContext
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.example.traindelay.R
import com.example.traindelay.model.Route
import java.lang.Exception
import java.time.LocalTime
import java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME


class RouteAdapter: RecyclerView.Adapter<RouteAdapter.RouteHolder>() {
    private var routes = listOf<Route>()

    class RouteHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val departure: TextView
        val delay: TextView
        val trainRoute: TextView
        val operator: TextView
        init {
            departure = itemView.findViewById(R.id.departureHour)
            delay = itemView.findViewById(R.id.delayInMinutes)
            trainRoute = itemView.findViewById(R.id.trainRoute)
            operator = itemView.findViewById(R.id.carrierIcon)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.route_item, parent, false)
        return RouteHolder(view)
    }

    override fun onBindViewHolder(holder: RouteHolder, position: Int) {
        val current: Route = routes[position]
        holder.trainRoute.text = current.name
        holder.departure.text = formatDatetoHour(current.departure)
        setTrainDelay(holder, current.delay)
        setOperator(current.operator, holder.operator)
    }

    override fun getItemCount() = routes.size

    fun setRouteCards(routeCards: List<Route>){
        this.routes = routeCards
        notifyDataSetChanged()
    }

    private fun setTrainDelay(holder: RouteHolder, delay: String){
        if (delay.toInt() == 0) holder.delay.text = ("(${delay} min)")
        else holder.delay.text = ("+(${delay} min)")
    }
    private fun formatDatetoHour(dateFromJson: String):String?{
        val date = LocalTime.parse(dateFromJson, RFC_1123_DATE_TIME)
        return date.toString()
    }
    private fun setOperator(operator: String, operatorHolder: TextView ){
        if (operator == "Koleje Mazowieckie"){
            operatorHolder.setText(R.string.KM)
            operatorHolder.setBackgroundResource(R.color.colorKM)
        }
        else if(operator == "SKM Warszawa"){
            operatorHolder.setText(R.string.SKM)
            operatorHolder.setBackgroundResource(R.color.colorSKM)
        }
        else if (operator =="PKP Intercity"){
            operatorHolder.setText((R.string.IC))
            operatorHolder.setBackgroundResource(R.color.colorIC)
        }
    }
}
