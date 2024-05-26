package com.iyke.crypto_tracker.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.application.CryptoViewModel
import com.example.crypto.BottomNavigation
import com.example.crypto.R
import com.example.crypto.userDetails
import com.iyke.crypto_tracker.model.Constants
import com.iyke.crypto_tracker.ui.theme.Black
import com.iyke.crypto_tracker.ui.theme.Typography
import com.iyke.crypto_tracker.ui.theme.White


@Composable
fun SettingsScreen(navController: NavController, cryptoModel : CryptoViewModel) {
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) {

        //navController.navigate(BottomNavItem.Home.screen_route)

        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(), color = Black
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 50.dp)
            ) {

                Spacer(modifier = Modifier.height((2 * Constants.PADDING_SIDE_VALUE).dp))

                UserInfoSection()

                SettingsColumn(navController)
            }
        }
    }
}

    @Composable
    private fun SettingsColumn(navController: NavController) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Constants.PADDING_SIDE_VALUE.dp)
        ) {

            SettingsItem(
                navController = navController,
                iconImageVector = Icons.Default.Lock,
                text = "Logout",

            )
        }
    }

    @Composable
    private fun SettingsItem(
        navController: NavController,
        iconImageVector: ImageVector,
        text: String
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate("Intro")
                }
        ) {
            IconTextRow(
                iconImageVector = iconImageVector,
                text = text
            )

            Icon(
                painter = painterResource(id = R.drawable.right_arrow),
                contentDescription = null,
                tint = White,
                modifier = Modifier
                    .clipToBounds()
                    .padding(horizontal = (Constants.PADDING_SIDE_VALUE * 1.5).dp)
                    .size(Constants.PADDING_SIDE_VALUE.dp)
            )
        }
    }


@Composable
private fun IconTextRow(
    iconImageVector: ImageVector,
    text: String
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = Constants.PADDING_SIDE_VALUE.dp)
    ) {
        Icon(
            imageVector = iconImageVector,
            contentDescription = text,
            tint = White,
            modifier = Modifier.size((2*Constants.PADDING_SIDE_VALUE).dp)
        )

        Spacer(modifier = Modifier.width(Constants.ELEVATION_VALUE.dp))

        Text(
            text = text,
            style = TextStyle(
                fontSize = 14.sp,
                color = Color.White,

                        lineHeight = 22.sp
            ),
        )
    }
}

@Composable
private fun UserInfoSection() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(Constants.PADDING_SIDE_VALUE.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

            Text(
                text = "kaviyarasu",
                style = Typography.bodyLarge,
                color = Color.White
            )

        }

        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Image",
            tint = White,
            modifier = Modifier.size((4 * Constants.PADDING_SIDE_VALUE).dp)
        )
}


@Preview
@Composable
fun SettingsScreenPreview() {
   // SettingsScreen()
}