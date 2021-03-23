package com.example.historialproductos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.lang.IllegalStateException

// --- Data Entity ---
@Entity
data class Type(
    @PrimaryKey(autoGenerate = true) val key: Int,
    @ColumnInfo(name = "name") val name: String?
)

@Entity
data class Brand(
    @PrimaryKey(autoGenerate = true) val key: Int, // Set auto-increment
    @ColumnInfo(name = "name") val name: String?
)

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Type::class,
            parentColumns = arrayOf("key"),
            childColumns = arrayOf("keyType"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Brand::class,
            parentColumns = arrayOf("key"),
            childColumns = arrayOf("keyBrand"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Product(
    @PrimaryKey(autoGenerate = true) val key: Int,
    val keyType: Int, // Foreign key of Type
    val keyBrand: Int, // Foreign key of Brand
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "unit") val unit: Int,
    @ColumnInfo(name = "weight") val weight: Double,
    @ColumnInfo(name = "characteristics") val characteristics: String?
)

@Entity
data class Store(
    @PrimaryKey(autoGenerate = true) val key: Int,
    @ColumnInfo(name = "name") val name: String?,
    //@ColumnInfo(name = "address") val address: String?
)

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Product::class,
            parentColumns = arrayOf("key"),
            childColumns = arrayOf("keyProduct"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Store::class,
            parentColumns = arrayOf("key"),
            childColumns = arrayOf("keyStore"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PriceDate(
    @PrimaryKey(autoGenerate = true) val key: Int,
    val keyProduct: Int,
    val keyStore: Int,
    //@ColumnInfo(name = "nameStore") val nameStore: String?,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "discount") val discount: Double,
    @ColumnInfo(name = "priceReduced") val priceReduced: Double,
    @ColumnInfo(name = "date") val date: String?
)

