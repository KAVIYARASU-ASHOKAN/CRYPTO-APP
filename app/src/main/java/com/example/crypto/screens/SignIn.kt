package com.iyke.crypto_tracker.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.application.CryptoViewModel
import com.example.crypto.R
import com.example.crypto.userDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.delay
import com.google.firebase.firestore.FirebaseFirestore
import com.iyke.crypto_trackersealed.BottomNavItem

@Composable
fun AppContent(auth: FirebaseAuth, navController: NavController) {
    AuthOrMainScreen(auth, navController)
}

@Composable
fun AuthOrMainScreen(auth: FirebaseAuth, navController: NavController) {
    var user by remember { mutableStateOf(auth.currentUser) }

    if(user != null || user == null) {
        AuthScreen(
            onSignedIn = { signedInUser ->
                user = signedInUser
            }, auth, navController
        )
    }

}

@Composable
fun AuthScreen(onSignedIn: (FirebaseUser) -> Unit, auth: FirebaseAuth, navController: NavController) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var isSignIn by remember { mutableStateOf(true) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    // State variables for error message
    var myErrorMessage by remember { mutableStateOf<String?>(null) }
    var isEmailValid by remember { mutableStateOf(true) } // Track email validation

    val cryptoViewModel : CryptoViewModel = viewModel()

    // Load your image as ImageBitmap (replace R.drawable.your_image with your actual image resource)
    val imagePainter: Painter = painterResource(id = R.drawable.bg)

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = imagePainter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Create a transparent card with rounded corners
        ElevatedCard(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.25f))
                .padding(25.dp)
                .clip(RoundedCornerShape(16.dp)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // First Name TextField
                if (!isSignIn) {
                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        label = {
                            Text("Name")
                        },
                        leadingIcon = {
                            Icon(Icons.Default.AccountBox, contentDescription = null)
                        },
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                }
                // Email TextField
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = email,
                    onValueChange = {
                        email = it
                        // Validate email format
                        isEmailValid = isValidEmail(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    label = {
                        Text("Email")
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = null)
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email
                    ),
                    visualTransformation = if (isSignIn) VisualTransformation.None else VisualTransformation.None,
                    isError = !isEmailValid, // Show error if email is not valid
                    singleLine = true,
                )

                // Show error message if email is not valid
                if (!isEmailValid) {
                    Text(
                        text = "Invalid email format",
                        color = Color.Red,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                // Password TextField
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    label = {
                        Text("Password")
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = null)
                    },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password
                    ),
                    trailingIcon = {
                        IconButton(
                            onClick = { isPasswordVisible = !isPasswordVisible }
                        ) {
                            val icon = if (isPasswordVisible) Icons.Default.Lock else Icons.Default.Search
                            Icon(
                                imageVector = icon,
                                contentDescription = "Toggle Password Visibility"
                            )
                        }
                    }
                )

                // ... (other content)
                Spacer(modifier = Modifier.height(16.dp))

                // Error Message
                if (myErrorMessage != null) {
                    Text(
                        text = myErrorMessage!!,
                        color = Color.Red,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Sign In/Sign Up Buttons
                Button(
                    onClick = {
                        if (isSignIn) {
                            signIn(cryptoViewModel, context, auth, email, password,
                                onSignedIn = { signedInUser ->
                                    onSignedIn(signedInUser)
                                },
                                onSignInError = { errorMessage ->
                                    // Show toast message on sign-in error
                                    myErrorMessage = errorMessage
                                }, navController = navController
                            )
                        } else {
                            signUp(context, auth, email, password, firstName, lastName,navController = navController, onSignedIn = {
                                    signedInUser ->
                                onSignedIn(signedInUser)
                            })
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(8.dp),
                ) {
                    Text(
                        text = if (isSignIn) "Sign In" else "Sign Up",
                        fontSize = 18.sp,
                    )
                }


                // Clickable Text
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(8.dp),
                ) {
                    ClickableText(
                        text = AnnotatedString(buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.Blue)) {
                                append(if (isSignIn) "Don't have an account? Sign Up" else "Already have an account? Sign In")
                            }
                        }.toString()),
                        onClick = {
                            myErrorMessage = null
                            email = ""
                            password = ""
                            isSignIn = !isSignIn
                        },
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }

        }
    }
}
// Function to handle sign-in errors
private fun onSignInError(errorMessage: String) {
    // Handle the sign-in error as needed
    // For now, we'll print the error message
    println("Sign-in error: $errorMessage")
}

private fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    return email.matches(emailRegex.toRegex())
}

@Composable
fun MainScreen(user: FirebaseUser, onSignOut: () -> Unit, navController: NavController) {
    val userProfile = remember { mutableStateOf<User?>(null) }

    // Fetch user profile from Firestore
    LaunchedEffect(user.uid) {
        val firestore = FirebaseFirestore.getInstance()
        val userDocRef = firestore.collection("users").document(user.uid)

        userDocRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val firstName = document.getString("firstName")
                    val lastName = document.getString("lastName")

                    userProfile.value = User(firstName, lastName, user.email ?: "")
                } else {
                    // Handle the case where the document doesn't exist
                }
            }
            .addOnFailureListener { e ->
                // Handle failure

            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        userProfile.value?.let {
            Text("Welcome, ${it.firstName} ${it.lastName}!")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onSignOut()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Sign Out")
        }
    }
}



private fun signIn(
    cryptoViewModel: CryptoViewModel,
    context: Context,
    auth: FirebaseAuth,
    email: String,
    password: String,
    onSignedIn: (FirebaseUser) -> Unit,
    onSignInError: (String) -> Unit,
    navController: NavController// Callback for sign-in error
) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser

                // retriuve data from firestore
                //using uid from auth.currentuser
                Log.e("user Details",user.toString())
                userDetails.user = user

                user?.uid?.let {
                    Log.e("user Details", "inside fetchTotal $it")

                    cryptoViewModel.fetchTotal(it) }

                Toast.makeText(context,"Sign In SuccessFull", Toast.LENGTH_SHORT).show()
                navController.navigate(BottomNavItem.Home.screen_route)
            } else {
                // Handle sign-in failure
                onSignInError("Invalid email or password")
            }
        }
}


private fun signUp(
    context: Context,
    auth: FirebaseAuth,
    email: String,
    password: String,
    firstName: String,
    lastName: String,
    onSignedIn: (FirebaseUser) -> Unit,
    navController: NavController
) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser

                // Create a user profile in Firestore
                val userProfile = hashMapOf(
                    "firstName" to firstName,
                    "lastName" to lastName,
                    "email" to email
                )

                val firestore = FirebaseFirestore.getInstance()
                firestore.collection("users")
                    .document(user!!.uid)
                    .set(userProfile)
                    .addOnSuccessListener {
                        Toast.makeText(context,"Sign Up SuccessFull", Toast.LENGTH_SHORT).show()

                        onSignedIn(user)
                        navController.navigate("signIn")
                    }
                    .addOnFailureListener {
                        //handle exception

                    }
            } else {
                // Handle sign-up failure

            }
        }
}


@Preview(showBackground = true)
@Composable
fun PreviewAuthOrMainScreen() {
    //AuthOrMainScreen(com.google.firebase.Firebase.auth)
}



data class User(
    val firstName: String?,
    val lastName: String?,
    val email: String



)
