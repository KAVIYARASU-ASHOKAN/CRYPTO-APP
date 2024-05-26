package com.iyke.crypto_tracker.screens

import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.application.CryptoViewModel
import com.example.crypto.BottomNavigation
import com.example.crypto.R
import com.iyke.crypto_tracker.common.ListColumn
import com.iyke.crypto_tracker.common.ListRowItem
import com.iyke.crypto_tracker.model.Constants.Companion.SHOTNAME
import com.iyke.crypto_tracker.model.Data.list
import com.iyke.crypto_tracker.ui.theme.Black
import com.iyke.crypto_tracker.ui.theme.CryptoTrackerTheme


@Preview(showBackground = true)
@Composable
fun MyView() {
    CryptoTrackerTheme {
        //HomeScreen()
    }
}

@Composable
fun HomeScreen(navController : NavController, viewModel : CryptoViewModel) {

    BackHandler(enabled = true) {
        // back press handle changes
    }

    val cryptoList by viewModel.cryptoList.collectAsState()

    viewModel.context(LocalContext.current)

    val coinNames : MutableList<String> = mutableListOf()

    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) {
        it
        //navController.navigate(BottomNavItem.Home.screen_route)

        Column(
            modifier = Modifier
                .background(color = Black)
                .verticalScroll(rememberScrollState())
                .wrapContentSize(Alignment.TopStart)
                .padding(5.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {


                }

                Column(
                    Modifier.padding(10.dp)
                ) {


                }

            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "  Portfolio", fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.bodySmall

                )

            }
            val context = LocalContext.current
            LazyRow(modifier = Modifier.fillMaxWidth(1F)) {
                cryptoList?.data?.let { it1 ->
                    items(items = it1.coins) { item ->
                        ListRowItem(item) {
                            coinNames.add(item.name)
                            CryptoDetailScreen(item.name,
                                onButtonClick = {

                                }, onBackArrowPressed = {
                                    navController.popBackStack()

                                })
                            val intent = Intent(context, CryptoDetailScreen::class.java)
                            intent.putExtra(SHOTNAME, item.symbol)
                            context.startActivity(intent)
                        }
                    }
                }
            }

            Text(
                "Market trend", fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Start,
                fontSize = 15.sp,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(10.dp)
            )

            cryptoList?.data?.coins?.forEachIndexed { index, portfolioCoins ->
                ListColumn(item = portfolioCoins)
            }
            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}