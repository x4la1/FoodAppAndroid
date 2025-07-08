package com.example.testcomposeapp.screens

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.testcomposeapp.R
import com.example.testcomposeapp.navigation.NavRoute
import com.example.testcomposeapp.ui.theme.DefaultRed
import com.example.testcomposeapp.ui.theme.GrayText
import com.example.testcomposeapp.ui.theme.PinkGradient
import com.example.testcomposeapp.ui.theme.White
import com.example.testcomposeapp.ui.theme.lobster
import com.example.testcomposeapp.ui.theme.roboto
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.nio.file.WatchEvent

@Composable
fun StartScreen(navController: NavHostController) {

    val auth = Firebase.auth
    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            PinkGradient,
            DefaultRed
        ),
        start = Offset(0f, 0f),
        end = Offset(300f, 1300f)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradientBrush)
            .padding(0.dp)
            .padding(top = 260.dp),

        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally


    ) {
        Text(
            text = "Foodgo",
            fontFamily = lobster,
            fontSize = 60.sp,
            color = Color.White,
        )
        Spacer(Modifier.size(height = 150.dp, width = 10.dp))
        Button(
            onClick = {
                if (auth.currentUser != null) {
                    navController.navigate(route = NavRoute.HomeScreen.route)
                } else {
                    navController.navigate(route = NavRoute.LoginScreen.route)
                }
            },
            modifier = Modifier
                .width(200.dp)
                .height(70.dp)
                .border(width = 3.dp, color = White, shape = RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = White,
                containerColor = DefaultRed
            ),
        )
        {
            Text(
                text = "Start",
                fontFamily = roboto,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = White
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomStart
        ) {
            Image(
                painter = painterResource(R.drawable.image_2),
                contentDescription = "img2",
                modifier = Modifier
                    .size(height = 246.dp, width = 288.dp)
                    .padding(end = 112.dp, top = 30.dp)
            )
            Image(
                painter = painterResource(R.drawable.image_1),
                contentDescription = "img1",
                modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(start = 100.dp)
                    .size(202.dp)
                    .offset(y = 20.dp)


            )

            //Image()
        }

//
    }

}