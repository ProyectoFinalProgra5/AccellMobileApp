package com.fvaldiviesok.accellmobileapp
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.fvaldiviesok.accellmobileapp.model.TourModel

class DatabaseHandler(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        //es un objeto que contiene un conjunto de constantes, como un Struct
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "AccelMobileTourApp"
        private val TABLE_TOUR = "TourTable"
        private val KEY_ID = "id"
        private val KEY_NAME = "name"
        private val KEY_DESCRIPTION = "descripcion"
        private val KEY_PHONE = "phone"
        private val KEY_EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_USER = ("CREATE TABLE " + TABLE_TOUR +
                "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT, " + KEY_DESCRIPTION + " TEXT, " + KEY_PHONE + " TEXT, " + KEY_EMAIL + " TEXT " + ")")
        println(CREATE_TABLE_USER)
        db?.execSQL(CREATE_TABLE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_TOUR) // !!, le dice que va a ver algo en la db
        onCreate(db)// elimina tabla y la vuelve a crear con nuevos datos
    }

    fun insert(tour: TourModel): Long {
        //siempre que yo escriba en DB, se utiliza "writableDatabase", y this, se refiere a la clase || Abre Conn
        val db = this.writableDatabase
        //ContentValues(), Creates an empty set of values(Array Map) using the default initial size
        val contentValues =
            ContentValues() //content Values es utiliza mapas(La Interface Map (java. ... Map) en Java, nos permite representar una estructura de datos para
        // almacenar pares "clave/valor"; de tal manera que para una clave solamente tenemos un valor.)

        contentValues.put(KEY_ID, tour.tourId)
        contentValues.put(KEY_NAME, tour.tourName)
        contentValues.put(KEY_DESCRIPTION, tour.tourDescription)
        contentValues.put(KEY_PHONE, tour.telContacto)
        contentValues.put(KEY_EMAIL, tour.emailContacto)

        println(contentValues)

        //Inserto los datos
        val success = db.insert(TABLE_TOUR, null, contentValues)
        //buena practica, cerrar conexion a DB
        db.close()

        return success
    }

    @SuppressLint("Range")
    fun read(): List<TourModel> {
        val tourList: ArrayList<TourModel> = ArrayList<TourModel>()
        val selectQuery = "SELECT * FROM $TABLE_TOUR"
        val db = this.readableDatabase
        var cursor: Cursor? = null;
        try {
            cursor = db.rawQuery(selectQuery, null) // selectionArgs, es para filtrar la busqueda
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var tourId: Int //tiene que hacer match con la interfaz
        var tourName: String
        var tourDescription: String
        var telContacto: String
        var emailContacto: String
        if (cursor.moveToFirst()) {
            do {
                tourId = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                tourName = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                tourDescription = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))
                telContacto = cursor.getString(cursor.getColumnIndex(KEY_PHONE))
                emailContacto = cursor.getString(cursor.getColumnIndex(KEY_EMAIL))
                val tour = TourModel(
                    tourId = tourId,
                    tourName = tourName,
                    tourDescription = tourDescription,
                    telContacto = telContacto,
                    emailContacto = emailContacto
                )
                tourList.add(tour)
            } while (cursor.moveToNext())
        }
        return tourList
    }


    fun update(tour: TourModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, tour.tourId)
        contentValues.put(KEY_NAME, tour.tourName)
        contentValues.put(KEY_DESCRIPTION, tour.tourDescription)
        contentValues.put(KEY_PHONE, tour.telContacto)
        contentValues.put(KEY_EMAIL, tour.emailContacto)


        val success = db.update(TABLE_TOUR, contentValues, "id=" + tour.tourId, null)
        db.close()
        return success
    }

    fun delete(tour: TourModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, tour.tourId)
        val success = db.delete(TABLE_TOUR, "id=" + tour.tourId, null)
        db.close()
        return success
    }
}