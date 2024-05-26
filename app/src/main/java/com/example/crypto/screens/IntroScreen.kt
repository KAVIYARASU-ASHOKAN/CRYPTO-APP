package com.iyke.crypto_tracker.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.crypto.R
import com.iyke.crypto_tracker.ui.theme.*
class IntroScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Screen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CryptoTrackerTheme {
        //Screen()
    }
}

@Composable
fun Screen(navController: NavController) {
    val context = LocalContext.current
    Column {
        Spacer(modifier = Modifier.height(100.dp))
        Image(
            painter = painterResource(id = R.drawable.btc),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillHeight
        )
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .background(color = Black, shape = RoundedCornerShape(0))
                    .align(Alignment.BottomCenter)
                    .padding(5.dp),
                ) {
                Spacer(modifier = Modifier.height(35.dp))

                Text(
                    text = "     The Future Crypto ",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 34.sp,
                    color = White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "                      Grow your portfolio !",
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    color = LightGrayColor,
                    textAlign = TextAlign.Center

                )
                Spacer(modifier = Modifier.height(55.dp))
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Button(
                        modifier = Modifier
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Teal200, blue
                                    )
                                ),
                                RoundedCornerShape(20.dp)
                            )
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        onClick = {
                                  navController.navigate("signIn")
                        },
                    ) {
                        Text(
                            text = "Get Started",
                            fontSize = 20.sp,
                            color = Color.White,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }
                Spacer(modifier = Modifier.height(45.dp))
            }
        }
    }
}
