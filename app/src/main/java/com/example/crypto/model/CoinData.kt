package com.example.crypto.model

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity
data class CoinData(
    @PrimaryKey(autoGenerate = true)
    val id : Int =0,
    val coinPrice : Float,
    val marketValue : Float,
    val coinName : String,
    var Quantity : Float,
    val userId : String,
    val type : Int,
    val dateUTC : String,
    val uid : String
)

@Entity
data class localTransactionData(
    @PrimaryKey(autoGenerate = true)
    val id : Int =0,
    val coinPrice : Float,
    val marketValue : Float,
    val coinName : String,
    var Quantity : Float,
    val userId : String,
    val type : Int,
    val dateUTC : String,
    val uid : String


)
@Entity
data class Total(
    @PrimaryKey(autoGenerate = true)
    val id : Int =1,
    val uid: String,
    val totalAmount : Float = 0F
)
@Dao
interface coinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCapture(coinData: CoinData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransactionData(coinData: localTransactionData)

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = Total::class)
    fun insertTotalAmount(data : Total)

    @Query("select * from CoinData where uid = :uid")
    fun getCoinDatas(uid : String) : List<CoinData>

    @Query("select * from localTransactionData where uid = :uid")
    fun getTransactionData(uid : String) : List<localTransactionData>

    @Query("select * from CoinData where coinName = :coinName")
    fun getIndividualCoinData(coinName : String) : CoinData

    @Query("UPDATE CoinData set  Quantity = :quantity, coinPrice = :coinPrice where coinName = :coinName ")
    fun updateCoinData(coinName : String, quantity : Float, coinPrice: Float)

    @Query("select * from Total where uid = :uid")
    fun balanceAmount(uid: String): Total

    @Query("UPDATE Total set totalAmount = totalAmount +  :amount where id = :uid")
    fun newTotalAmount(amount: Float, uid : String)

    @Query("select SUM(coinPrice) from CoinData where uid = :uid")
    fun getTotalAmount(uid : String) : Float

}

@Dao
interface TotalDao{
    @Query("select * from Total where uid = :uid")
    fun getCoinTotal(uid : String) : List<Total>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = Total::class)
    fun insertTotalAmount(data : Total)

    @Query("UPDATE Total set totalAmount = totalAmount +  :amount where id = :uid")
    fun newTotalAmount(amount: Float, uid : String)

}

