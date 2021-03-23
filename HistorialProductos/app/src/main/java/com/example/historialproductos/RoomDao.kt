package com.example.historialproductos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy //???
import androidx.room.Query

// --- Data Access Object ---
@Dao
interface TypeDao {
    @Query("SELECT * FROM Type")
    fun getAll(): List<Type>

    @Query("SELECT name FROM Type")
    fun getNameAll(): List<String>

    @Query(
        "SELECT key FROM Type " +
                "WHERE name = :nameU"
    )
    fun getKeyByName(nameU: String?): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(type: Type)
}

@Dao
interface ProductDao {
    @Query("SELECT * FROM Product")
    fun getAll(): List<Product>

    @Query("SELECT name FROM Product")
    fun getNameAll(): List<String>

    @Query(
        "SELECT * FROM Product " +
                "WHERE key = :keyP"
    )
    fun getProductByKey(keyP: Int): Product

    @Query(
        "SELECT key FROM Product " +
                "WHERE name = :nameU AND keyBrand = :keyB AND keyType = :keyT"
    )
    fun getKeyIfProductExist(nameU: String, keyB: Int, keyT: Int): Int

    @Query(
        "SELECT key FROM Product " +
                "WHERE name = :nameU"
    )
    fun getKeyByName(nameU: String?): Int

    @Query(
        "SELECT key FROM Product " +
                "WHERE name = :nameU AND keyBrand = :keyB"
    )
    fun getKeyByNameBrand(nameU: String?, keyB: Int): Int

    @Query(
        "SELECT name FROM Product " +
                "WHERE key = :keyU"
    )
    fun findNameByKey(keyU: Int): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Product)
}

@Dao
interface BrandDao {
    @Query("SELECT * FROM Brand")
    fun getAll(): List<Brand>

    @Query("SELECT name FROM Brand")
    fun getNameAll(): List<String>

    @Query(
        "SELECT key FROM Brand " +
                "WHERE name = :nameU"
    )
    fun getKeyByName(nameU: String?): Int

    @Query(
        "SELECT name FROM Brand " +
                "WHERE key = :keyB"
    )
    fun getNameByKey(keyB: Int): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(brand: Brand)
}

@Dao
interface StoreDao {
    @Query("SELECT * FROM Store")
    fun getAll(): List<Store>

    @Query("SELECT name FROM Store")
    fun getNameAll(): List<String>

    @Query(
        "SELECT key FROM Store " +
                "WHERE name = :nameU"
    )
    fun getKeyByName(nameU: String?): Int

    @Query(
        "SELECT name FROM Store " +
                "WHERE key = :keyS"
    )
    fun getNameByKey(keyS: Int): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(store: Store)
}

@Dao
interface PriceDateDao {
    @Query("SELECT * FROM PriceDate")
    fun getAll(): List<PriceDate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(priceDate: PriceDate)

    /*@Query(
        "SELECT * FROM PriceDate " +
                "WHERE nameStore LIKE :nameS AND " +
                "keyProduct LIKE :keyP"
    )
    fun findByProductStore(nameS: String, keyP: Int): List<PriceDate>*/

    @Query(
        "SELECT * FROM PriceDate " +
                "WHERE keyProduct = :keyP"
    )
    fun getAllByProduct(keyP: Int): List<PriceDate>

    @Query(
        "SELECT * FROM PriceDate " +
                "WHERE keyProduct LIKE :keyP"
    )
    fun findByProduct(keyP: Int): List<PriceDate>
}