package com.example.testcomposeapp.screens

import android.util.Log
import android.util.Log.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testcomposeapp.navigation.NavRoute
import com.example.testcomposeapp.ui.theme.BlackBrown
import com.example.testcomposeapp.ui.theme.DefaultRed
import com.example.testcomposeapp.ui.theme.GrayBg
import com.example.testcomposeapp.ui.theme.GrayText
import com.example.testcomposeapp.ui.theme.White
import com.example.testcomposeapp.ui.theme.roboto
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


@Composable
fun LoginScreen(navController: NavHostController) {

    val auth = Firebase.auth

    var isLoading by remember {
        mutableStateOf(false)
    }

    var emailState by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var passwordState by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = GrayBg),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.36f)
                .background(color = White, shape = RoundedCornerShape(35.dp)),
        ) {
            OutlinedTextField(
                value = emailState,
                onValueChange = {
                    emailState = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(top = 10.dp)
                    .background(Color.White),
                label = {
                    Text(
                        text = "Email",
                        color = GrayText,
                        fontFamily = roboto,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    )
                },
                textStyle = TextStyle(
                    fontFamily = roboto,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = BlackBrown
                ),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GrayText,
                    unfocusedBorderColor = GrayText,
                    cursorColor = GrayText
                ),
                shape = RoundedCornerShape(20.dp)


            )

            OutlinedTextField(
                value = passwordState,
                onValueChange = {
                    passwordState = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White),
                label = {
                    Text(
                        text = "Password",
                        color = GrayText,
                        fontFamily = roboto,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    )
                },
                textStyle = TextStyle(
                    fontFamily = roboto,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = BlackBrown
                ),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GrayText,
                    unfocusedBorderColor = GrayText,
                    cursorColor = GrayText
                ),
                shape = RoundedCornerShape(20.dp),
                visualTransformation = PasswordVisualTransformation(),


                )

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom

            ) {
                Button(
                    onClick = {
                        isLoading = true
                        if (emailState.text.isNotEmpty() && passwordState.text.length >= 6) {
                            signUp(
                                auth,
                                emailState.text,
                                passwordState.text,
                                navController
                            )
                        }
                        isLoading = false

                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(70.dp)
                        .border(
                            width = 3.dp,
                            color = DefaultRed,
                            shape = RoundedCornerShape(20.dp)
                        ),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = White,
                        containerColor = White
                    ),
                    enabled = !isLoading
                ) {
                    Text(
                        text = "Sign Up",
                        fontFamily = roboto,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = DefaultRed
                    )
                }

                Spacer(
                    modifier = Modifier
                        .width(10.dp)
                )

                Button(
                    onClick = {
                        isLoading = true
                        if (emailState.text.isNotEmpty() && passwordState.text.length >= 6) {
                            signIn(
                                auth,
                                emailState.text,
                                passwordState.text,
                                navController
                            )
                        }
                        isLoading = false
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(70.dp)
                        .border(
                            width = 3.dp,
                            color = DefaultRed,
                            shape = RoundedCornerShape(20.dp)
                        ),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = White,
                        containerColor = DefaultRed
                    ),
                    enabled = !isLoading
                ) {
                    Text(
                        text = "Sign In",
                        fontFamily = roboto,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = White
                    )
                }
            }
        }
    }
}


private fun signUp(
    auth: FirebaseAuth,
    email: String,
    password: String,
    navController: NavHostController
) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("MyLog", "Sign Up Successful")
                navController.navigate(route = NavRoute.HomeScreen.route)
            } else {
                Log.d("MyLog", "Sign Up Error, ${it.exception?.message}")

            }
        }
}

private fun signIn(
    auth: FirebaseAuth,
    email: String,
    password: String,
    navController: NavHostController
) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("MyLog", "Sign In Successful")
                navController.navigate(route = NavRoute.HomeScreen.route)
            } else {
                Log.d("MyLog", "Sign In Error, ${it.exception?.message}")
            }
        }
}












