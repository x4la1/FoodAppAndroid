package com.example.testcomposeapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testcomposeapp.ui.theme.BlackBrown
import com.example.testcomposeapp.ui.theme.DefaultRed
import com.example.testcomposeapp.ui.theme.GrayBg
import com.example.testcomposeapp.ui.theme.GrayText
import com.example.testcomposeapp.ui.theme.White
import com.example.testcomposeapp.ui.theme.roboto

@Composable
fun LoginScreen(navController: NavHostController) {

    var emailState by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var passwordState by remember {
        mutableStateOf(TextFieldValue(""))
    }

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
                .fillMaxHeight(0.7f)
                .background(color = White, shape = RoundedCornerShape(35.dp)),
        ) {
//            OutlinedTextField(value = emailState, onValueChange = {
//                emailState = it
//            })

            OutlinedTextField(
                value = passwordState,
                onValueChange = {
                    passwordState = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(top = 40.dp)
                    .background(Color.White),
                label = {
                    Text(
                        text = "Name",
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
    }

}