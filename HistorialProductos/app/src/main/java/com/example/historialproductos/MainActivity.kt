package com.example.historialproductos

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var productDB: RoomSingleton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        // Delete all previous data -> Just for testing
        //applicationContext.deleteDatabase("roomDatabase")
        // Initialize the room database
        productDB = RoomSingleton.getInstance(applicationContext)

        // Test insert data into table
        doAsync {
            // Delete all data in tables (integer keys store previous data) -> Just for testing
            //productDB.clearAllTables()
            if (productDB.typeDao().getAll().isEmpty()) { // The type values must be populated here
                // Dummy insert data into store table
                //productDB.storeDao().insert(Store(key = 0, name = "Merqueo", address = "Online")) // pkey = 1
                productDB.storeDao().insert(Store(key = 0, name = "Merqueo")) // pkey = 1
                //productDB.storeDao().insert(Store(key = 0,name = "Surtimayorista", address =  "Quirigua" )) // pkey = 2
                productDB.storeDao().insert(Store(key = 0, name = "Surtimayorista")) // pkey = 2
                // Dummy insert data into type table
                productDB.typeDao().insert(Type(key = 0, name = "Paquetes")) // pkey = 1
                productDB.typeDao().insert(Type(key = 0, name = "Lacteo")) // pkey = 2
                productDB.typeDao().insert(Type(key = 0, name = "Aseo")) // pkey = 2
                // Dummy insert data into brand table
                productDB.brandDao().insert(Brand(key = 0, name = "Rica")) // pkey = 1
                productDB.brandDao().insert(Brand(key = 0, name = "Delactosa")) // pkey = 2
                // Dummy insert data into product table
                productDB.productDao().insert(
                    Product(
                        key = 0,
                        name = "Jamón",
                        keyType = 2,
                        keyBrand = 1,
                        unit = 24,
                        weight = 500.0,
                        characteristics = "Jamon rica ideal"
                    )
                ) // pkey = 1
                productDB.productDao().insert(
                    Product(
                        key = 0,
                        name = "Sixpack Leche",
                        keyType = 1,
                        keyBrand = 2,
                        unit = 6,
                        weight = 11000.0,
                        characteristics = "Paquete de seis bolsas"
                    )
                ) // pkey = 2
                productDB.productDao().insert(
                    Product(
                        key = 0,
                        name = "Jamón2",
                        keyType = 2,
                        keyBrand = 1,
                        unit = 24,
                        weight = 500.0,
                        characteristics = "Jamon rica ideal"
                    )
                ) // pkey = 3
                productDB.productDao().insert(
                    Product(
                        key = 0,
                        name = "Sixpack Leche2",
                        keyType = 1,
                        keyBrand = 2,
                        unit = 6,
                        weight = 11000.0,
                        characteristics = "Paquete de seis bolsas"
                    )
                ) // pkey = 4
                productDB.productDao().insert(
                    Product(
                        key = 0,
                        name = "Jamón3",
                        keyType = 2,
                        keyBrand = 1,
                        unit = 24,
                        weight = 500.0,
                        characteristics = "Jamon rica ideal"
                    )
                ) // pkey = 5
                productDB.productDao().insert(
                    Product(
                        key = 0,
                        name = "Sixpack Leche3",
                        keyType = 1,
                        keyBrand = 2,
                        unit = 6,
                        weight = 11000.0,
                        characteristics = "Paquete de seis bolsas"
                    )
                ) // pkey = 6
                // Dummy insert data into priceDate table
                // Set dummy date
                //var dummyD = LocalDate.now()
                var dummyDay =
                    SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime())
                productDB.priceDateDao().insert(
                    PriceDate(
                        key = 0,
                        keyProduct = 1,
                        keyStore = 1,
                        price = 15000.0,
                        discount = 0.0,
                        priceReduced = 0.0,
                        date = dummyDay
                    )
                )
                var i = 0
                while (i < 20) { // dummy fill table
                    productDB.priceDateDao().insert(
                        PriceDate(
                            key = 0,
                            keyProduct = 1,
                            keyStore = 1,
                            price = 15000.0,
                            discount = 0.0,
                            priceReduced = 0.0,
                            date = dummyDay
                        )
                    )
                    i++
                }
                productDB.priceDateDao().insert(
                    PriceDate(
                        key = 0,
                        keyProduct = 2,
                        keyStore = 2,
                        price = 20000.0,
                        discount = 0.0,
                        priceReduced = 0.0,
                        date = dummyDay
                    )
                )
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}