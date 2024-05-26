package com.iyke.crypto_tracker.screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.application.CryptoViewModel
import com.example.crypto.BottomNavigation
import com.example.crypto.R
import com.example.crypto.model.CoinData
import com.example.crypto.userDetails
import com.iyke.crypto_tracker.common.BuyAndSell
import com.iyke.crypto_tracker.common.CombinedTab
import com.iyke.crypto_tracker.common.TransactionData
import com.iyke.crypto_tracker.common.TransactionType
import com.iyke.crypto_tracker.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Preview(showBackground = true)
@Composable
fun WalletPreview() {
    //WalletScreen()
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun WalletScreen(navController: NavController, cryptromodel: CryptoViewModel) {


    val cryptoList by cryptromodel.cryptoList.collectAsState()

    val localCoinData by cryptromodel.localCoinData.collectAsState()

    val totamt by cryptromodel.totalAmount.collectAsState()

    val balanceAmt by cryptromodel.balanceAmount.collectAsState()

    val coinNames: MutableList<String> = mutableListOf()

    var coinNamesPrice: HashMap<String, String> = HashMap()


    var clickedOnce by remember {
        mutableStateOf(true)
    }

    var balAmt by remember {
        mutableFloatStateOf(1000F)
    }

    val context = LocalContext.current

    if (clickedOnce) {

        cryptoList?.data?.coins?.forEach {
            coinNames.add(it.name)
            coinNamesPrice[it.name] = it.price
        }

        cryptromodel.context(context)

    }


    var buyClicked by remember {
        mutableStateOf(false)
    }

    var sellClicked by remember {
        mutableStateOf(false)
    }

    var TransactionData by remember {
        mutableStateOf<TransactionData?>(null)
    }

    var coinData by remember {
        mutableStateOf<CoinData?>(null)
    }

    var transactionType by remember {
        mutableStateOf(TransactionType.Buy)
    }

    var viewModelBalAmt = cryptromodel._balanceAmount

    //db-call


    Log.e(
        "Transaction",
        TransactionData.toString() + "  " + transactionType + "amt:" + coinData.toString()
    )

    val formatter = SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.getDefault())


    if (buyClicked) {
        Log.e(
            "Buy", coinNames.toString()
        )

        BuyAndSell(
            TransType = TransactionType.Buy,
            suggestions = coinNames,
            isDialogBoxOpened = { buyClicked = false },
            map = coinNamesPrice,
        ) {

            TransactionData = it
            transactionType = TransactionType.Buy
            val coins =
                (TransactionData?.amount?.toFloat())?.div(
                    (coinNamesPrice.get(TransactionData?.coinName)?.toFloat()!!)
                )

            balAmt = TransactionData?.amount?.toFloat()?.let { it1 -> 1000F.minus(it1) }!!


            val localCheckBal = totamt?.toFloat()?.let { it1 -> balanceAmt.minus(it1) }
//check error for 0F
            val localAmt : Float = TransactionData?.amount?.toFloat() ?: 0F

            if (localCheckBal != null) {
                if (coins != null && localCheckBal >= localAmt) {

                    //viewModelBalAmt.value = bal.toString()
                    coinData = TransactionData?.amount?.let { it1 ->
                        cryptromodel.amountUpdate(it1.toFloat())
                        TransactionData?.coinName?.let { it2 ->
                            userDetails.user?.let { it3 ->
                                coinNamesPrice.get(TransactionData?.coinName)?.toFloat()?.let { it4 ->
                                    CoinData(
                                        coinPrice = it1.toFloat(),
                                        marketValue = it4,
                                        coinName = it2,
                                        Quantity = coins.toFloat(),
                                        userId = "it1",
                                        type = 1,
                                        dateUTC = formatter.format(Date()).toString(),
                                        uid = it3.uid
                                    )
                                }
                            }
                        }
                    }
                    CoroutineScope(Dispatchers.IO).launch {

                        coinData?.let { it1 -> cryptromodel.insert(it1) }
                        cryptromodel.insertTotal()

                    }
                } else{
                    Toast.makeText(
                        context,
                        "Insufficient Balance",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            Log.e("Buy", coinData.toString())


            buyClicked = false


        }
    }





    if (sellClicked) {

        BuyAndSell(
            TransType = TransactionType.Sell,
            suggestions = coinNames,
            isDialogBoxOpened = { sellClicked = false },
            map = coinNamesPrice,
        ) {
            TransactionData = it
            transactionType = TransactionType.Sell


            val localCoin = localCoinData?.filter {
                Log.e("sell", "${it.coinName},${TransactionData!!.coinName}")
                it.coinName == TransactionData!!.coinName
            }

//            if (localCoin != null) {
//                if(localCoin.isNotEmpty()){
//                    val marketPrice =
//                        localCoin?.get(0)?.coinPrice?.let { it1 ->
//                            TransactionData?.amount?.toFloat()?.times(
//                                it1
//                            )
//                        }
//                }
//            }
//
//
//
//            Log.e("Sell", "Market Price : $marketPrice")

            balAmt = TransactionData?.amount?.toFloat()?.let { it1 -> 1000F.plus(it1) }!!


            if (localCoin?.isEmpty() == false) {

               // viewModelBalAmt.value = bal
                if (localCoin.get(0).Quantity >= TransactionData!!.amount.toFloat()) {

                    val quantity = localCoin.get(0).Quantity - TransactionData!!.amount.toFloat()

                    Log.e("Sell", " Inside LocalCoin - Quantity : $quantity")


                    val price = quantity * coinNamesPrice.get(TransactionData?.coinName)?.toFloat()!!

                    Log.e("Sell", " Inside LocalCoin - Not EMpty - Price : $price")


                    cryptromodel.amountUpdate(price)

                    coinData = userDetails.user?.let { it1 ->
                        TransactionData?.coinName?.let { it2 ->
                            coinNamesPrice.get(TransactionData?.coinName)?.toFloat()?.let { it3 ->
                                TransactionData?.amount?.toFloat()?.let { it4 ->

                                    CoinData(
                                        coinPrice = price,
                                        marketValue = it3,
                                        coinName = it2,
                                        Quantity = quantity,
                                        userId = it1.uid,
                                        type = 2,
                                        dateUTC = formatter.format(Date()).toString(),
                                        uid = it1.uid
                                    )
                                }
                            }
                        }
                    }

                    Log.e("Sell", coinData.toString())
                    CoroutineScope(Dispatchers.IO).launch {

                        coinData?.let { it1 -> cryptromodel.sell(it1) }

                    }
                }else {
                    Toast.makeText(context, "Not enough coins", Toast.LENGTH_SHORT).show()
                }
        }else {
            Toast.makeText(context, "No Coin Found", Toast.LENGTH_SHORT).show()
            }


        sellClicked = false

    }

}



Scaffold(
bottomBar = { BottomNavigation(navController = navController) }
) {

    //navController.navigate(BottomNavItem.Home.screen_route)

    Column(
        modifier = Modifier
            .background(color = Black)
            .fillMaxHeight()
            .padding(it)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {


        }

        Spacer(modifier = Modifier.height(25.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(color = Green, shape = RoundedCornerShape(15))
                .padding(25.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Invested : Rs $totamt",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        modifier = Modifier
                            .wrapContentWidth()
                            .align(Alignment.CenterVertically),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                    )
                    Icon(
                        imageVector = Icons.Filled.List,
                        contentDescription = "",
                        tint = Color.White
                    )

                }

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Bal Amt : Rs ${totamt?.toFloat()?.let { it1 -> balanceAmt.minus(it1) }}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier
                        .wrapContentWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {


                }

                Spacer(
                    modifier = Modifier
                        .height(30.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.Bottom
                ) {

                    Box(
                        modifier = Modifier.clickable {
                            buyClicked = true
                        }
                    ) {
                        Column(

                        ) {

                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = null,
                                modifier = Modifier
                                    .background(Color.Black, CircleShape)
                                    .padding(15.dp),
                                tint = Color.White

                            )
                            Spacer(modifier = Modifier.height(7.dp))

                            Text(
                                text = "Buy",
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .clickable {},
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                    Box(
                        modifier = Modifier.clickable {
                            sellClicked = true
                        }
                    ) {
                        Column {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_call_made_24),
                                contentDescription = null,
                                modifier = Modifier
                                    .background(
                                        blue,
                                        CircleShape
                                    )   // add a border (optional)
                                    .padding(15.dp),
                                tint = Color.White

                            )
                            Spacer(modifier = Modifier.height(7.dp))
                            Text(
                                text = "Sell",
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .clickable {

                                    },
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))
        CombinedTab(cryptromodel = cryptromodel)
    }
}
}



