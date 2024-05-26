package com.application
import ai.constructn.aaas.cryptoapplication.ApiResponse
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.crypto.MainActivity
import com.example.crypto.model.CoinData
import com.example.crypto.model.Total
import com.example.crypto.model.localTransactionData
import com.example.crypto.userDetails
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class CryptoViewModel : ViewModel() {

    private val _cryptoList = MutableStateFlow<ApiResponse?>(null)
    val cryptoList: StateFlow<ApiResponse?> = _cryptoList


    private val _cryptoListsuccess = MutableStateFlow(false)
    val cryptoListsuccess: StateFlow<Boolean> = _cryptoListsuccess

    private val _localCoinData = MutableStateFlow<List<CoinData>?>(null)
    val localCoinData: StateFlow<List<CoinData>?> = _localCoinData

    private val _localLocalTransactionData = MutableStateFlow<List<localTransactionData>?>(null)
    val localTransactionData: StateFlow<List<localTransactionData>?> = _localLocalTransactionData

    private val _totalAmount = MutableStateFlow<String?>("")
    val totalAmount: StateFlow<String?> = _totalAmount

    val _balanceAmount = MutableStateFlow<Float>(1000F)
    val balanceAmount: StateFlow<Float> = _balanceAmount

    var db : MainActivity.AppDatabase? = null




    var Context = MutableStateFlow<Context?>(null)


   init {
       fetchCryptoData()
       retrive()
   }

    fun context(context: Context) {
        Log.e("CryptoViewModel","Inside contextttt")

        Context.value = context

        db = Context.let {
            Log.e("CryptoViewModel","Inside db")
            it.value?.applicationContext?.let { it1 ->
                Room.databaseBuilder(
                    it1, MainActivity.AppDatabase::class.java, "Crypto_db").fallbackToDestructiveMigration().build()

            }
        }

        CoroutineScope(Dispatchers.IO).launch {

            val totamt = userDetails.user?.uid?.let { db?.coinDao?.getTotalAmount(it) }

            _totalAmount.value = totamt.toString()

            insertTotal()
        }

    }

  @SuppressLint("SuspiciousIndentation")
  fun fetchCryptoData() {
      viewModelScope.launch {
          Log.d("syed", "fetchCryptoDataviewmodel")
          val Retrofit = Retrofit.Builder()
              .addConverterFactory(GsonConverterFactory.create())
              .baseUrl("https://api.coinranking.com/v2/")
              .build()

          val service = Retrofit.create(apicalls::class.java)

          val response = service.getCryptoData()
          if (response.isSuccessful) {
//              val data = response.body()
              _cryptoList.value=response.body()
                  Log.d("syed", response.body().toString())

          }else{
              val errorBody = response.errorBody()?.string()

              Log.e("syed", errorBody ?: "Unknown error")
          }

          // Fetch data from API and update _cryptoList
      }
  }

    fun fetchTotal(uid : String) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.e("fetchTotal","fetch inside")
            val data = db?.coinDao?.balanceAmount(uid)

            if(data != null){
                Log.e("fetchTotal","$data")

                _totalAmount.value = data.totalAmount.toString()
            }
            else {
                Log.e("fetchTotal","else $data")

                val newData = Total(uid = uid, totalAmount = 1000F)

                db?.coinDao?.insertTotalAmount(newData)
                //_totalAmount.value = 1000.toString()
            }
        }
    }

    fun insert(data : CoinData) {

        Log.e("CryptoViewModel","Inside insert function")

        CoroutineScope(Dispatchers.IO).launch {

            val coinData = db?.coinDao?.getIndividualCoinData(data.coinName)


            if(coinData != null) {

                val newQuantity = coinData.Quantity.plus(data.Quantity)

                Log.e("CryptoViewModel","new quantity : ${newQuantity}")


                val newPrice = newQuantity * data.marketValue

                Log.e("CryptoViewModel","new price : ${newPrice}")


                db?.coinDao?.updateCoinData(data.coinName, newQuantity, newPrice)
            }
            else {
                data.let { db?.coinDao?.insertCapture(it) }
            }

                val transData = localTransactionData(
                    coinPrice = data.coinPrice,
                    coinName = data.coinName,
                    Quantity = data.Quantity,
                    userId = data.userId,
                    type = 1,
                    dateUTC = data.dateUTC,
                    uid = data.uid,
                    marketValue = data.marketValue
                )


                transData.let { db?.coinDao?.insertTransactionData(it) }

                val transList = db?.coinDao?.getTransactionData(data.uid)
                _localLocalTransactionData.value = transList


            val coins = db?.coinDao?.getCoinDatas(data.uid)

            _localCoinData.value = coins

            val totamt = db?.coinDao?.getTotalAmount(data.uid)

            Log.e("CryptoViewModel","new Total : ${totamt}")


            _totalAmount.value = totamt.toString()

        }

    }

    fun insertTotal() {

        val data = userDetails.user?.uid?.let { db?.totalDao?.getCoinTotal(it) }

        if (data != null) {
            if(data.isNotEmpty()) {
                _balanceAmount.value = data.get(0).totalAmount
            } else {
                val total = userDetails.user?.uid?.let {
                    Total(
                        uid = it,
                        totalAmount = 1000F
                    )
                }
                if (total != null) {
                    db?.totalDao?.insertTotalAmount(total)
                }
            }
        }
    }

    fun sell(data : CoinData) {

        Log.e("CryptoViewModel","Inside sell function")


        CoroutineScope(Dispatchers.IO).launch {

            val individualData = db?.coinDao?.getIndividualCoinData(data.coinName)


//            Log.e("CryptoViewModel", remainingQuantity.toString())

            val transData = localTransactionData(
                coinPrice = data.coinPrice,
                coinName = data.coinName,
                Quantity = data.Quantity,
                userId = data.userId,
                type = 2,
                dateUTC = data.dateUTC,
                uid = data.uid,
                marketValue = data.marketValue

            )

//            data.Quantity = remainingQuantity?.toFloat()!!



            transData?.let { db?.coinDao?.insertTransactionData(it)}

            val transList = db?.coinDao?.getTransactionData(data.uid)
            _localLocalTransactionData.value = transList


            db?.coinDao?.updateCoinData(data.coinName, data.Quantity, data.coinPrice)

            val coins = db?.coinDao?.getCoinDatas(data.uid)

            _localCoinData.value = coins

            val amt = userDetails.user?.uid?.let { db?.coinDao?.getTotalAmount(it) }

            _totalAmount.value = amt.toString()



        }


    }

    fun amountUpdate(amt : Float){

        CoroutineScope(Dispatchers.IO).launch {

         userDetails.user?.uid?.let { db?.coinDao?.newTotalAmount(amt, it) }
        }


    }

    fun retrive() : List<CoinData>? {

        Log.e("CryptoViewModel","Inside insert function")

        var data : List<CoinData>? = null

        CoroutineScope(Dispatchers.IO).launch {

            data = userDetails.user?.uid?.let { db?.coinDao?.getCoinDatas(it) }

            _localCoinData.value = data

            val amt = userDetails.user?.uid?.let { db?.coinDao?.getTotalAmount(it) }

            _totalAmount.value = amt.toString()

           // _balanceAmount.value =
        }
        return data
    }
}



interface apicalls{
    @GET("coins")
    suspend fun getCryptoData():Response <ApiResponse>

}

fun resultToJson(loginResult: Response<ApiResponse>): String {
    return Gson().toJson(loginResult)
}

fun jsonToResult(json: String): ApiResponse {
    return Gson().fromJson(json, ApiResponse::class.java)
}