package com.iyke.crypto_tracker.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.application.CryptoViewModel
import com.example.crypto.BottomNavigation
import com.iyke.crypto_tracker.common.ListColumn1
import com.iyke.crypto_tracker.common.TransactionItem
import com.iyke.crypto_tracker.model.Constants
import com.iyke.crypto_tracker.model.Data
import com.iyke.crypto_tracker.model.Data.transactionList
import com.iyke.crypto_tracker.model.Transaction
import com.iyke.crypto_tracker.ui.theme.Black
import com.iyke.crypto_tracker.ui.theme.Typography
import com.iyke.crypto_tracker.ui.theme.White

@Preview
@Composable
fun value() {
    //Transaction()
}

@Composable
fun Transaction(navController: NavController,cryptoModel : CryptoViewModel) {

    val localTransactionData by cryptoModel.localTransactionData.collectAsState()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) {

        //navController.navigate(BottomNavItem.Home.screen_route)

        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            color = Black
        ) {
            Card(
                modifier = Modifier
                    .padding(Constants.PADDING_SIDE_VALUE.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                shape = MaterialTheme.shapes.medium,
                ) {
                Column(
                    modifier = Modifier
                        .padding(
                            top = Constants.PADDING_SIDE_VALUE.dp,
                            start = Constants.PADDING_SIDE_VALUE.dp,
                            end = Constants.PADDING_SIDE_VALUE.dp
                        )
                ) {
                    Text(
                        text = "Transaction History",
                        style = Typography.bodyMedium,
                        color = White
                    )


                    val context = LocalContext.current
                    localTransactionData?.forEachIndexed { index, transaction ->
                        TransactionItem(transaction) {
                            val intent = Intent(context, TransactionDetails::class.java)
                            intent.putExtra(Constants.Id, transaction.id)
                            context.startActivity(intent)
                        }
                        Divider(
                            modifier = Modifier
                                .padding(
                                    top = Constants.PADDING_SIDE_VALUE.dp,
                                    bottom = if (transactionList.size - 1 > index) {
                                        Constants.PADDING_SIDE_VALUE.dp
                                    } else {
                                        0.dp
                                    }
                                )
                        )
                    }


                }
            }
        }
    }
}