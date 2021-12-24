package com.fvaldiviesok.accellmobileapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.fvaldiviesok.accellmobileapp.databinding.ActivityAddTourBinding
import com.fvaldiviesok.accellmobileapp.databinding.ActivityWeatherBinding
import com.fvaldiviesok.accellmobileapp.model.TourModel

class AddTourActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTourBinding
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTourBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Boton para navegar devuelta al home page
        val homeBtn = findViewById<ImageView>(R.id.addTourHomeBtn)
        val addtour = findViewById<Button>(R.id.btnAgregarTour)
        val viewTour = findViewById<Button>(R.id.btnViewTours)
        val updateTour = findViewById<Button>(R.id.btnEditarTour)
        val deleteTour = findViewById<Button>(R.id.btnEliminarTour)

        homeBtn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        addtour.setOnClickListener {
            insert(it)
        }

        viewTour.setOnClickListener{
            read(it)
        }

        updateTour.setOnClickListener{
            update(it)
        }

        deleteTour.setOnClickListener{
            delete(it)
        }

    }
    private fun insert(view: View) {
        val txtId = findViewById<TextView>(R.id.tourID)
        val id = txtId.text.toString()

        val txtName = findViewById<TextView>(R.id.tourName)
        val name = txtName.text.toString()

        val txtDescription = findViewById<TextView>(R.id.tourDescription)
        val description = txtDescription.text.toString()

        val txtEmail = findViewById<TextView>(R.id.tourEmail)
        val email = txtEmail.text.toString()

        val txtPhone = findViewById<TextView>(R.id.tourPhone)
        val phone = txtPhone.text.toString()

        val databaseHandler = DatabaseHandler(this)

        //el .trim quita los espacios en blanco ( 03 03 04 ), .trim(030304)

        if (id.trim() != "" && name.trim() != "" && description.trim() != "" && phone.trim() != "" && email.trim() != "") {
            val status = databaseHandler.insert(
                TourModel(Integer.parseInt(id), name, description, phone, email
                )
            )
            if (status > -1) { // el metodo insert retonar un long; si status es mayor a -1, ya que es boolean, haga lo siguiente
                Toast.makeText(this, "Tour created", Toast.LENGTH_LONG).show()
                txtId.text = ""
                txtName.text = ""
                txtDescription.text = ""
                txtEmail.text = ""
                txtPhone.text = ""
            }
        } else {
            Toast.makeText(
                this,
                "The ID, Name, Email or Phone fields cannot be blank",
                Toast.LENGTH_LONG
            ).show()
        }

    }

    private fun read(view: View) {
        val databaseHandler = DatabaseHandler(this)
        val tourList: List<TourModel> = databaseHandler.read()
        val tourArrayId = Array<String>(tourList.size){"0"}// agarre el tamano del tour.List y inicialice los valores en 0 y null, dependiendo del tipo de dato.
        val tourArrayName = Array<String>(tourList.size){"null"}
        val tourArrayDescription = Array<String>(tourList.size){"null"}
        val tourArrayPhone = Array<String>(tourList.size){"null"}
        val tourArrayEmail = Array<String>(tourList.size){"null"}


        var index = 0;

        for (tour in tourList) {
            tourArrayId[index] = tour.tourId.toString()
            tourArrayName[index] = tour.tourName
            tourArrayDescription[index] = tour.tourDescription
            tourArrayPhone[index] = tour.telContacto
            tourArrayEmail[index] = tour.emailContacto
            index ++
        }

        val myListAdapter = MyListAdapter(this, tourArrayId, tourArrayName,tourArrayDescription, tourArrayPhone ,tourArrayEmail)//creo un adaptador que va a unir la informacion que yo traje con la interfaz del custom_list
        listView = findViewById(R.id.listViewTours) //cuando el custom_list tiene toda la informacion este se lo pasa al listView
        listView.adapter = myListAdapter

    }

    private fun update(view: View) {
        val dialogBuilder = AlertDialog.Builder(this) //construye los dialogos que son tipo pop-ups(notificaciones)// parecido a JOptionPane
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.update, null)
        dialogBuilder.setView(dialogView) //el setView setea lo que este en los parentesis como pantalla activa

        //valores de los textView
        val edtId = dialogView.findViewById<EditText>(R.id.updateId)
        val edtName = dialogView.findViewById<EditText>(R.id.updateName)
        val edtDescription = dialogView.findViewById<EditText>(R.id.updateDescription)
        val edtPhone = dialogView.findViewById<EditText>(R.id.updatePhone)
        val edtEmail = dialogView.findViewById<EditText>(R.id.updateEmail)


        dialogBuilder.setTitle("Update Employee")//setTitle es una propiedad de muchas de dialogBuilder.
        dialogBuilder.setMessage("Please, enter the Tour data")
        dialogBuilder.setPositiveButton("Update", DialogInterface.OnClickListener{ _, _ -> //una vez que el usuario precione Update haga lo siguiente
            val updateId = edtId.text.toString()
            val updateName = edtName.text.toString()
            val updateDescription = edtDescription.text.toString()
            val updatePhone = edtPhone.text.toString()
            val updateEmail = edtEmail.text.toString()


            val databaseHandler = DatabaseHandler(this)
            if (updateId.trim() != "" && updateName.trim() != "" && updateDescription.trim() != "" && updateEmail.trim() != "" && updatePhone != "") {
                val status = databaseHandler.update(TourModel(Integer.parseInt(updateId), updateName, updateDescription ,updateEmail, updatePhone))
                if( status > -1) {
                    Toast.makeText(this, "Tour updated successful", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "The ID, Name, Email or Phone fields cannot be blank", Toast.LENGTH_LONG).show()
            }
        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener{ dialog, which ->
//            //pass
        })
        val b = dialogBuilder.create()
        b.show()
    }

    private fun delete(view: View) {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.delete, null)
        dialogBuilder.setView(dialogView)

        val dltId = dialogView.findViewById<EditText>(R.id.deleteUser)

        dialogBuilder.setTitle("Delete Tour")
        dialogBuilder.setMessage("Please, enter the User ID")
        dialogBuilder.setPositiveButton("Delete", DialogInterface.OnClickListener{ _, _ ->
            val deleteId = dltId.text.toString()

            val databaseHandler = DatabaseHandler(this)
            if (deleteId.trim() != "" ) {
                val status = databaseHandler.delete(TourModel(Integer.parseInt(deleteId), "", "", "", ""))
                if( status > -1) {
                    Toast.makeText(this, "Tour deleted successful", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "The ID field cannot be blank", Toast.LENGTH_LONG).show()
            }
        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener{ _, _ ->
            //pass
        })
        val b = dialogBuilder.create()
        b.show()
    }
}

