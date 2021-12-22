package com.fvaldiviesok.accellmobileapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.fvaldiviesok.accellmobileapp.databinding.ActivityWeatherBinding
import org.json.JSONObject

class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        //binding permite hacerle un tipo de override al layout del "activity main" especificamente(si tuviera otro layout solo le cambio el nombre en el lateinit var)
        //para no hacer el proceso de UI mas expedito, y solo editar x elemento, solo con el id
        //osea vincular el codigo con la parte visual directamente
        //binding.idCityName.setText("Slinky")

        setContentView(binding.root)
    }

    fun onSearchClick(view: View) {
        performRequest(binding.idCityName.text.toString())
    }

    fun performRequest(cityName : String){
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val apiurl = "https://api.openweathermap.org/data/2.5/weather?q=${cityName}&appid=205c0acce705a3e59b1ccb96d3f16702"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, apiurl,
            { response ->
                /* Display the first 500 characters of the response string.*/
                /*textView.text = "Response is: ${response.substring(0, 500)}"*/

                val responseJson = JSONObject(response) //JSONObject(response), basicamente el objeto(en formato Json) que se llamo atraves del response,
                // metalo en la variable "responseJson", y asi poder manipular datos o atributos especificos dentro del objeto llamado
                var city = responseJson.getString("name")
                var mainJson = responseJson.getJSONObject("main")

                val temp = mainJson.getString("temp")
                val feels_like = mainJson.getString("feels_like")
                val temp_min = mainJson.getString("temp_min")
                val temp_max = mainJson.getString("temp_max")
                val pressure = mainJson.getString("pressure")
                val humidity = mainJson.getString("humidity")

                Toast.makeText(this,
                    "City: $city , " +
                            "Temp: $temp," +
                            "Feels like: $feels_like , " +
                            "Min Temperature: $temp_min," +
                            "Max Temperature: $temp_max," +
                            "Pressure: $pressure," +
                            "Humidity: $humidity", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener {
                binding.idCityName.setText("That didn't work!")// textView.text = "That didn't work!"
            })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}