package com.example.traindelay.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView

class Helper {
    companion object {
        private val ERRORS = mapOf(
            ErrorEntity.Network to "Brak połączenia",
            ErrorEntity.NotFound to "Nie znaleziono strony.",
            ErrorEntity.ServiceUnavailable to "Przeciążenie serwisu lub konserwacja.",
            ErrorEntity.Unknown to "Coś poszło nie tak."
        )
        fun showErrorMsg(error: ErrorEntity, textView: TextView) {
            textView.visibility = View.VISIBLE
            textView.text = ERRORS.getValue(error)
        }
        fun hideKeyboard(view: View){
            val inputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken,0)
        }
    }
}