package com.example.historialproductos

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import android.util.Log
import java.io.File
import java.io.IOException

@Database(
    entities = arrayOf(
        Type::class,
        Product::class,
        Brand::class,
        Store::class,
        PriceDate::class
    ),
    version = 1
)
abstract class RoomSingleton : RoomDatabase () {
    abstract fun typeDao(): TypeDao
    abstract fun productDao(): ProductDao
    abstract fun brandDao(): BrandDao
    abstract fun storeDao(): StoreDao
    abstract fun priceDateDao(): PriceDateDao

    companion object{
        private var INSTANCE: RoomSingleton? = null
        fun getInstance(context:Context) : RoomSingleton{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    RoomSingleton::class.java,
                    "roomDatabase").build()
            }
            return INSTANCE as RoomSingleton
        }
        fun importDB(context: Context, sourceDatabase: File): RoomSingleton{
            INSTANCE = null // force delete all the database
            if(INSTANCE == null){
                try{
                    INSTANCE = Room.databaseBuilder(
                        context,
                        RoomSingleton::class.java,
                        "roomDatabase"
                    ).createFromFile(sourceDatabase)
                        .build()
                } catch (e: IOException){
                    Log.d("ImportDBRoom->", e.toString())
                }
                Log.d("ImportDBRoom->","I've created the new import DB")
            }
            return INSTANCE as RoomSingleton
        }
    }
}