package com.iyke.crypto_tracker.common

import ai.constructn.aaas.cryptoapplication.Coin
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.application.CryptoViewModel
import com.example.crypto.R
import com.example.crypto.model.CoinData
import com.example.crypto.model.localTransactionData
import com.granite.compose.ui.DynamicForm.AutoSuggestTextBox
import com.iyke.crypto_tracker.model.Constants
import com.iyke.crypto_tracker.ui.theme.Typography
import com.iyke.crypto_tracker.model.Data.list
import com.iyke.crypto_tracker.model.PortfolioCoins
import com.iyke.crypto_tracker.model.Transaction
import com.iyke.crypto_tracker.ui.theme.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun CommonText(
    text: String,
    color: Color = Color.Black,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    function: () -> Unit
) {
    Text(
        text = text,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        modifier = Modifier.clickable {
            function()
        }
    )
}

@Composable
fun ValuesItem(
    currency: PortfolioCoins,
    priceModifier: Modifier = Modifier
        .padding(top = 20.dp),
    changesModifier: Modifier = Modifier
        .padding(top = 5.dp),
    currencyPriceStyle: TextStyle = Typography.headlineSmall,
    currencyChangesStyle: TextStyle = Typography.headlineSmall
) {
    var changeColor by remember {
        mutableStateOf(
            if(currency.changeType == "I") {
                Green
            } else {
                Red
            }
        )
    }

    var changeOperator by remember {
        mutableStateOf(
            if(currency.changeType == "I") {
                "+"
            } else {
                "-"
            }
        )
    }

    Text(
        text = "${currency.currentPrice}",
        style = currencyPriceStyle,
        modifier = priceModifier,
        color = White
    )

    Text(
        text = "$changeOperator${currency.changes}%",
        style = currencyChangesStyle,
        color = changeColor,
        modifier = changesModifier
    )
}


@Composable
fun SetPriceAlertSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Constants.PADDING_SIDE_VALUE.dp) ,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(Constants.PADDING_SIDE_VALUE.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.notification_color),
                contentDescription = "Price Alert Icon"
            )

            SetPriceAlertTextColumn()

            Icon(
                tint = Color.White,
                painter = painterResource(id = R.drawable.right_arrow),
                contentDescription = null
            )
        }
    }
}

@Composable
fun SetPriceAlertTextColumn() {
    Column() {
        Text(
            text = "Set Price Alert",
            style = Typography.headlineSmall,
            color = White
        )
        Text(
            text = "Get notified when your coins are moving",
            style = Typography.bodySmall,
            color = White
        )
    }
}
@Composable
fun CommonLoginButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        PinkColor, blue
                    )
                ),
                RoundedCornerShape(20.dp)
            )
            .height(58.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        onClick = { onClick() }
    ) {
        Text(text = text, fontSize = 20.sp, color = Color.White)
    }
}

@Composable
fun CommonGoogleButton(
    text: String
) {
    Button(
        onClick = { },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .background(Color.White, RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .height(58.dp)
            .border(3.dp, Color.Black, RoundedCornerShape(25.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color.White, RoundedCornerShape(20.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Google Logo",
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(18.dp))
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.W400
            )
        }
    }
}

@Composable
fun CurrencyItem(
    currency: PortfolioCoins
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = currency.coinLogo),
            contentDescription = currency.coinName,
            modifier = Modifier
                .size(25.dp)
        )

        Column(
            modifier = Modifier
                .padding(start = Constants.PADDING_SIDE_VALUE.dp)
        ) {
            Text(
                text = currency.coinName,
                style = Typography.headlineMedium,
                color = White
            )

            Text(
                text = currency.currencyCode,
                style = Typography.bodyMedium,
                color = White
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTextField(
    text: String,
    placeholder: String,
    isPasswordTextField: Boolean,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = text,
        onValueChange = { onValueChange(it) },
        label = { Text(text = placeholder, color = LightGrayColor) },
        maxLines = 1,
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = PinkColor,
            cursorColor = Color.Black
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = if (isPasswordTextField) PasswordVisualTransformation()
        else VisualTransformation.None
    )
}

@Composable
fun ListRowItem(item: Coin, onClick: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .width(200.dp)
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color.Gray.copy(0.2f), blue.copy(0.1f)
                    )
                ),
                RoundedCornerShape(20.dp)
            )
            .padding(15.dp)
            .height(210.dp)
            .clickable { }
    ) {
//        Image(
//            painter = painterResource(id = ),
//            contentDescription = "user icon",
//            modifier = Modifier
//                .size(70.dp)
//                .align(Alignment.BottomStart)
//                .padding(10.dp)
//        )

        AsyncImage(
            model = item.iconUrl,
            contentDescription = "user icon",
            modifier = Modifier
                .size(70.dp)
                .align(Alignment.BottomStart)
                .padding(1.dp)
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(10.dp)
        ) {
            Text(
                text = item.name,
                color = Color.White,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = item.symbol,
                color = Color.White,
                fontSize = 12.sp
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_keyboard_arrow_drop_up_24),
                contentDescription = "user icon",
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = item.change,
                color = if(item.rank > 0) Color.Green else Color.Red,
                fontSize = 10.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(10.dp)
        ) {
            Text(
                text = item.symbol,
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.End)
            )

            Spacer(modifier = Modifier.height(2.dp))

            val price = item.price.split(".")
            Text(
                text = "$" + price.get(0) + price.get(1).substring(0,2),
                color = Color.White,
                fontSize = 16.sp,
            )
        }
    }
}

// home screen list small
@Composable
fun ListColumn(item: Coin) {
    val price = item.price.split(".")
    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                AsyncImage(
                    model =item.iconUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .border(2.dp, Color.Gray, CircleShape)
                        .size(70.dp)
                        .padding(15.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column(Modifier.align(Alignment.CenterVertically)) {
                    Text(
                        text = item.name,
                        color = Color.White,
                        fontSize = 16.sp,
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = item.symbol,
                        color = Color.White,
                        fontSize = 13.sp,
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "$" + price.get(0) +"."+ price.get(1).substring(0,2),
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.align(Alignment.End)
                )
                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "$" + price.get(0) +"."+ price.get(1).substring(0,2)  +"("+item.rank+")",
                    color = if(item.rank > 0) Color.Green else Color.Red,
                    fontSize = 13.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }

    }
}

@Composable
fun ListColumn1(item: CoinData) {
   // val price = item.price.split(".")

    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
            ) {

                Spacer(modifier = Modifier.width(10.dp))

                Column(Modifier.align(Alignment.CenterVertically)) {
                    Text(
                        text = item.coinName,
                        color = Color.White,
                        fontSize = 16.sp,
                    )
//                    Text(
//                        text = item.symbol,
//                        color = Color.White,
//                        fontSize = 13.sp,
//                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = item.Quantity.toString(),
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.align(Alignment.End)
                )
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = item.coinPrice.toString(),
                    color = Color.White,
                    fontSize = 13.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }

        Divider(color = Color.White, thickness = 0.9.dp, modifier = Modifier
            .padding(5.dp)
            .align(Alignment.BottomCenter))

    }
}


@Composable
fun CryptoSelection() {
    var coinName: String by remember { mutableStateOf(list[0].coinName) }
    var coinLogo: Int by remember { mutableStateOf(list[0].coinLogo) }
    var expanded by remember { mutableStateOf(false) }
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
        Row(
            Modifier
                .clickable {
                    expanded = !expanded
                }
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) { // Anchor view

            Image(
                painter = painterResource(id = coinLogo),
                contentDescription = "user icon",
            )
            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = coinName,
                fontSize = 18.sp,
                color = White,
                modifier = Modifier.padding(end = 8.dp)
            ) // Country name label
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "",
                tint = Color.White
            )

            //
            DropdownMenu(expanded = expanded, onDismissRequest = {
                expanded = false
            }) {
                list.forEach { country ->
                    DropdownMenuItem(onClick = {
                        expanded = false
                        coinName = country.coinName
                        coinLogo = country.coinLogo
                    },
                        text = { country.coinName })

                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CombinedTab(cryptromodel: CryptoViewModel) {

    val cryptoList by cryptromodel.cryptoList.collectAsState()

    val localCoinData by cryptromodel.localCoinData.collectAsState()

    val coinNames : MutableList<String> = mutableListOf()

    val tabData = listOf(
        "Coins",
    )
    val pagerState = rememberPagerState(){
        tabData.size
    }
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()
    Column {
        TabRow(containerColor = Black,
            selectedTabIndex = tabIndex,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[tabIndex]), color = White
                )
            }
        ) {
            tabData.forEachIndexed { index, pair ->
                Tab(selected = tabIndex == index, onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }, text = {
                    Text(text = pair)
                }, selectedContentColor = White)
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { index ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (index) {
                    0 -> {
                        LazyColumn(modifier = Modifier.fillMaxWidth(1F)) {
                            localCoinData?.let {
                                items(items = it) { item ->
                                    coinNames.add(item.coinName)
                                    ListColumn1(item)
                                }
                            }
                        }
                    }
                    1 -> {
                        empty("No opened positions Yet")
                    }
                    2 -> {
                        empty("No NFts yet")
                    }
                }
            }
        }
    }
}

@Composable
 fun TransactionItem(transaction: localTransactionData,onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = Constants.PADDING_SIDE_VALUE.dp)
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        TransactionDescriptionSection(transaction)

        TransactionAmountSection(transaction)
    }
}

@Composable
private fun TransactionAmountSection(
    transaction: localTransactionData,
) {
    val amountColor = if (transaction.type == 2) {
        com.iyke.crypto_tracker.ui.theme.Red
    } else {
        Green
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "${ transaction.Quantity.toFloat()}",
            style = Typography.headlineMedium,
            color = amountColor
        )

        Icon(
            tint = White,
            painter = painterResource(id = R.drawable.right_arrow),
            contentDescription = null,
            modifier = Modifier
                .clipToBounds()
                .padding(start = (Constants.PADDING_SIDE_VALUE * 1.5).dp)
        )
    }
}

@Composable
private fun TransactionDescriptionSection(transaction: localTransactionData) {

    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            tint = White,
            painter = painterResource(id = R.drawable.transaction),
            contentDescription = "Transaction image",
            modifier = Modifier
                .padding(end = (Constants.PADDING_SIDE_VALUE * 1.5).dp)
        )

        Column {
            Text(
                text = if(transaction.type == 1) "Bought ${transaction.coinName}" else "Sold ${transaction.coinName}",
                style = Typography.bodyMedium,
                color = White.copy(0.70f)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = transaction.dateUTC,
                style = Typography.bodySmall,
                color = White.copy(0.30f)
            )
        }
    }
}

@Composable
fun empty(text: String){
    Column {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_emoji_flags_24),
            tint = White,
            contentDescription = null,
            modifier = Modifier
                .border(2.dp, Color.Gray, CircleShape)   // add a border (optional)
                .padding(15.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyLarge

        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyAndSell(TransType: TransactionType , suggestions : List<String>, isDialogBoxOpened: (Boolean) -> Unit, map : HashMap<String,String>, onSubmit : (TransactionData) -> Unit) {

    Log.e("Buy", suggestions.toString())

    var amount by remember {
        mutableStateOf("")
    }
    var selectedCoinName by remember {
        mutableStateOf("")
    }

    val type = if (TransType.name == "Buy") Green else Red

    Dialog(
        onDismissRequest = { isDialogBoxOpened(false) },
        properties = DialogProperties()
    ) {

        Box(
            modifier = Modifier
                .padding(20.dp)
                .height(500.dp)
                .background(Color.DarkGray),
            contentAlignment = Alignment.Center,
        ) {
            Column {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.padding(10.dp))

                    Text(
                        text = TransType.name,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = type,
                        fontWeight = FontWeight(500)
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(
                        text = "Select Coin :",
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = White,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.padding(5.dp))

                    AutoSuggestTextBox(
                        onValueChange = { selectedCoinName = it },
                        label = "Coin",
                        suggestions = suggestions
                    ) {

                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(
                        text = if(TransType.name == "Buy") "Amount:" else "Quantity:",
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = White,
                        textAlign = TextAlign.Center,
                    )

                    OutlinedTextField(
                        value = amount,
                        onValueChange = {
                            amount = it
                        },
                        label = { "Amount" },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        textStyle = LocalTextStyle.current.copy(),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
                    )

                }
                Column {
                    Divider(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth(),
                        thickness = 0.8.dp,
                        color = Color.Black
                    )
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = TransType.name,
                            color = type,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    isDialogBoxOpened(false)
                                    onSubmit(
                                        TransactionData(
                                            coinName = selectedCoinName,
                                            amount = amount
                                        )
                                    )
                                },
                            textAlign = TextAlign.Center,
                        )
                    }

                }
            }
        }
    }
}





enum class TransactionType {
    Buy,
    Sell
}

data class TransactionData(
    val coinName : String,
    val amount : String
)





