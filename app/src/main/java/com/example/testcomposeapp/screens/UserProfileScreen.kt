package com.example.testcomposeapp.screens

import android.graphics.ColorSpace
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.testcomposeapp.R
import com.example.testcomposeapp.data.User
import com.example.testcomposeapp.navigation.NavRoute
import com.example.testcomposeapp.ui.theme.BlackBrown
import com.example.testcomposeapp.ui.theme.DefaultRed
import com.example.testcomposeapp.ui.theme.GrayText
import com.example.testcomposeapp.ui.theme.Pink
import com.example.testcomposeapp.ui.theme.White
import com.example.testcomposeapp.ui.theme.roboto
import com.example.testcomposeapp.utils.base64ToBitmap
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun UserProfileScreen(navController: NavHostController) {
    val fs = Firebase.firestore
    val auth = Firebase.auth

    var isLoading by remember {
        mutableStateOf(false)
    }

    val user = remember {
        mutableStateOf(User())
    }

    val avatarBase64: String = ""

    var firstNameState by remember {
        mutableStateOf("")
    }

    var lastNameState by remember {
        mutableStateOf("")
    }

    var addressState by remember {
        mutableStateOf("")
    }

    val authUser = auth.currentUser

    LaunchedEffect(authUser?.uid) {
        if (authUser != null) {
            fs.collection("users").document(authUser.uid).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    user.value = task.result.toObject(User::class.java)!!
                    firstNameState = user.value.firstName
                    lastNameState = user.value.lastName
                    addressState = user.value.deliveryAddress
                    Log.d("MyLog", "User get OK")
                } else {
                    Log.d("MyLog", "User get BAD, ${task.exception?.message}")
                }
            }
        }
    }

    fun updateUserProfile() {
        val userData = mapOf(
            "firstName" to firstNameState,
            "lastName" to lastNameState,
            "deliveryAddress" to addressState
        )


        fs.collection("users").document(user.value.uid)
            .update(userData)
            .addOnSuccessListener {
                Log.d("MyLog", "Update OK")
            }
            .addOnFailureListener { e ->
                Log.w("MyLog", "Update BAD", e)
            }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Pink),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_left),
                contentDescription = "back_icon",
                tint = White,
                modifier = Modifier
                    .clickable {
                        navController.navigate(NavRoute.HomeScreen.route) {
                            popUpTo(NavRoute.HomeScreen.route) { inclusive = false }
                        }
                    }
                    .padding(15.dp)

            )
        }

        Image(
            bitmap = base64ToBitmap(user.value.imageBase64) ?: ImageBitmap(139, 139),
            contentDescription = "img2",
            modifier = Modifier
                .offset(y = (46).dp)
                .zIndex(2f)
                .size(139.dp, 139.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(White)
                .border(
                    width = 4.dp,
                    color = DefaultRed,
                    shape = RoundedCornerShape(28.dp)
                ),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f)
                .background(
                    color = White,
                    shape = RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
            ) {

                OutlinedTextField(
                    value = firstNameState,
                    onValueChange = {
                        firstNameState = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .padding(top = 10.dp)
                        .background(Color.White),
                    label = {
                        Text(
                            text = "First Name",
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
                    value = lastNameState,
                    onValueChange = {
                        lastNameState = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .padding(top = 10.dp)
                        .background(Color.White),
                    label = {
                        Text(
                            text = "Last Name",
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
                    value = addressState,
                    onValueChange = {
                        addressState = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .padding(top = 10.dp)
                        .background(Color.White),
                    label = {
                        Text(
                            text = "Delivery address",
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
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 20.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                            updateUserProfile()
                            isLoading = false
                        },
                        modifier = Modifier
                            .width(195.dp)
                            .height(70.dp)
                            .border(
                                width = 3.dp,
                                color = BlackBrown,
                                shape = RoundedCornerShape(20.dp)
                            ),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = White,
                            containerColor = BlackBrown
                        ),
                    ) {
                        Text(
                            text = "Edit Profile",
                            fontFamily = roboto,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            color = White
                        )
                        Icon(
                            painter = painterResource(R.drawable.ic_edit),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(start = 17.dp),
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .width(22.dp)
                    )

                    Button(
                        enabled = !isLoading,
                        onClick = {
                            isLoading = true
                            auth.signOut()
                            navController.navigate(NavRoute.StartScreen.route) {
                                popUpTo(NavRoute.StartScreen.route) { inclusive = false }
                            }
                            isLoading = false
                        },

                        modifier = Modifier
                            .width(175.dp)
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
                    ) {
                        Text(
                            text = "Log out",
                            fontFamily = roboto,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            color = DefaultRed
                        )
                        Icon(
                            painter = painterResource(R.drawable.ic_log_out),
                            tint = DefaultRed,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(start = 16.dp),
                        )
                    }


                }
            }
        }
    }
}