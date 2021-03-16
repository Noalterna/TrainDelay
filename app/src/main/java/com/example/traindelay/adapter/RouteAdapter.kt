package com.example.traindelay.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.VisibleForTesting
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.traindelay.R
import com.example.traindelay.model.Route
import java.time.LocalTime
import java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME

class RouteAdapter: RecyclerView.Adapter<RouteAdapter.RouteHolder>() {
    companion object {
        private val OPERATORS = mapOf(
            "Koleje Mazowieckie" to Operator(R.string.KM, R.color.colorKM),
            "SKM Warszawa" to Operator(R.string.SKMW, R.color.colorSKMWA),
            "PKP Intercity" to Operator(R.string.IC, R.color.colorIC),
            "Przewozy Regionalne" to Operator(R.string.POLREGIO, R.color.colorPOLREGIO),
            "Koleje Śląskie" to Operator(R.string.KŚ, R.color.colorKŚ),
            "Koleje Dolnośląskie" to Operator(R.string.KD, R.color.colorKD),
            "Arriva" to Operator(R.string.Arriva, R.color.colorArriva),
            "Koleje Wielkopolskie" to Operator(R.string.KW, R.color.colorKW),
            "Łódzka Kolej Aglomeracyjna" to Operator(R.string.ŁKA, R.color.colorŁKA),
            "PKP SKM w Trójmieście Sp. z o.o." to Operator(R.string.SKMT, R.color.colorSKMT)
        )
    }
    private data class Operator(val shortName: Int, val color: Int)
    private var routes = listOf<Route>()

    class RouteHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val departure: TextView
        val delay: TextView
        val trainRoute: TextView
        val operator: TextView
        init {
            departure = itemView.findViewById(R.id.departureHour)
            delay = itemView.findViewById(R.id.delayInMinutes)
            operator = itemView.findViewById(R.id.operatorIcon)
            trainRoute = itemView.findViewById(R.id.trainRoute)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.route_item, parent, false)
        return RouteHolder(view)
    }

    override fun onBindViewHolder(holder: RouteHolder, position: Int) {
        val current: Route = routes[position]
        holder.trainRoute.text = (current.relation[0] + " - " + current.relation[1])
        holder.departure.text = formatDateToHour(current.departure).toString()
        setTrainDelay(holder, current.delay)
        setOperator(current.operator, holder.operator)
    }

    override fun getItemCount() = routes.size

    fun setRouteCards(routeCards: List<Route>){
        val sorted = routeCards.sortedBy {
            formatDateToHour(it.departure)?.plusMinutes(it.delay.toLong())}
        this.routes = sorted
        notifyDataSetChanged()
    }
    private fun setTrainDelay(holder: RouteHolder, delay: String){
        if (delay.toInt() == 0) {
            holder.delay.text = ("$delay min")
            holder.delay.setTextColor(ContextCompat.getColor(holder.delay.context, R.color.colorGreyedOut))
        }
        else {
            holder.delay.text = ("+ $delay min")
            holder.delay.setTextColor(ContextCompat.getColor(holder.delay.context, R.color.colorAccent))
        }
    }
    private fun formatDateToHour(dateFromJson: String):LocalTime?{
        return LocalTime.parse(dateFromJson, RFC_1123_DATE_TIME)
    }
    private fun setOperator(operator: String, operatorHolder: TextView ){
        val operatorValue = OPERATORS.getValue(operator)
        operatorHolder.setText(operatorValue.shortName)
        operatorHolder.setBackgroundResource(operatorValue.color)
    }
}

