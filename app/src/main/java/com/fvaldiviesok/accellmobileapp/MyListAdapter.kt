package com.fvaldiviesok.accellmobileapp


import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MyListAdapter(private val context: Activity, private val id: Array<String>, private val name: Array<String> ,private val description: Array<String>, private val phone: Array<String>, private val email: Array<String>)
    : ArrayAdapter<String>(context, R.layout.custom_list, description) {
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true)

        val idText = rowView.findViewById<TextView>(R.id.textViewId)
        val nameText = rowView.findViewById<TextView>(R.id.textViewName)
        val descriptionText = rowView.findViewById<TextView>(R.id.textViewDescription)
        val phoneText = rowView.findViewById<TextView>(R.id.textViewPhone)
        val emailText = rowView.findViewById<TextView>(R.id.textViewEmail)

        idText.text = "ID: ${id[position]}"
        nameText.text = "Name: ${name[position]}"
        descriptionText.text = "Description: ${description[position]}"
        phoneText.text = "Email: ${phone[position]}"
        emailText.text = "Phone: ${email[position]}"

        return rowView
    }
}