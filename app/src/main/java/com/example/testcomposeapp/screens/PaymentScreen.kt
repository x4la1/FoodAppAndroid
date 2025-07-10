package com.example.testcomposeapp.screens

import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testcomposeapp.R
import com.example.testcomposeapp.navigation.NavRoute
import com.example.testcomposeapp.ui.theme.Black
import com.example.testcomposeapp.ui.theme.BlackBrown
import com.example.testcomposeapp.ui.theme.DefaultRed
import com.example.testcomposeapp.ui.theme.GrayText
import com.example.testcomposeapp.ui.theme.GrayText3
import com.example.testcomposeapp.ui.theme.White
import com.example.testcomposeapp.ui.theme.inter
import com.example.testcomposeapp.ui.theme.poppins
import com.example.testcomposeapp.ui.theme.roboto
import com.example.testcomposeapp.viewmodel.OrderViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun PaymentScreen(navController: NavHostController, orderViewModel: OrderViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(12.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_back),
            contentDescription = "back_icon",
            tint = BlackBrown,
            modifier = Modifier
                .clickable {
                    navController.navigate(NavRoute.HomeScreen.route) {
                        popUpTo(NavRoute.HomeScreen.route) { inclusive = false }
                    }
                }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 7.dp)
                .padding(top = 31.dp)
        ) {
            Text(
                text = "Order summary",
                fontFamily = poppins,
                fontSize = 20.sp,
                color = BlackBrown,
                fontWeight = FontWeight.SemiBold,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Order",
                        fontFamily = roboto,
                        fontSize = 18.sp,
                        color = GrayText3,
                        fontWeight = FontWeight.Normal,
                    )
                    Text(
                        text = "$${
                            String.format(
                                java.util.Locale.US,
                                "%.2f",
                                orderViewModel.getTotalPrice()
                            )
                        }",
                        fontFamily = roboto,
                        fontSize = 18.sp,
                        color = GrayText3,
                        fontWeight = FontWeight.Normal,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Taxes",
                        fontFamily = roboto,
                        fontSize = 18.sp,
                        color = GrayText3,
                        fontWeight = FontWeight.Normal,
                    )
                    Text(
                        text = "$0.0",
                        fontFamily = roboto,
                        fontSize = 18.sp,
                        color = GrayText3,
                        fontWeight = FontWeight.Normal,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Delivery fees",
                        fontFamily = roboto,
                        fontSize = 18.sp,
                        color = GrayText3,
                        fontWeight = FontWeight.Normal,
                    )
                    Text(
                        text = "$1.5",
                        fontFamily = roboto,
                        fontSize = 18.sp,
                        color = GrayText3,
                        fontWeight = FontWeight.Normal,
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 39.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total:",
                        fontFamily = roboto,
                        fontSize = 18.sp,
                        color = BlackBrown,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = "$${
                            String.format(
                                java.util.Locale.US,
                                "%.2f",
                                orderViewModel.getTotalPrice() + 1.5
                            )
                        }",
                        fontFamily = roboto,
                        fontSize = 18.sp,
                        color = BlackBrown,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 26.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Estimated delivery time:",
                        fontFamily = roboto,
                        fontSize = 14.sp,
                        color = BlackBrown,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = "15 - 30mins",
                        fontFamily = roboto,
                        fontSize = 14.sp,
                        color = BlackBrown,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 20.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                ) {
                    Text(
                        text = "Total price",
                        fontFamily = roboto,
                        fontSize = 16.sp,
                        color = GrayText,
                        fontWeight = FontWeight.Normal,
                    )
                    Row() {
                        Text(
                            text = "$",
                            fontFamily = roboto,
                            fontSize = 32.sp,
                            color = DefaultRed,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            text = String.format(
                                java.util.Locale.US,
                                "%.2f",
                                orderViewModel.getTotalPrice() + 1.5
                            ),
                            fontFamily = roboto,
                            fontSize = 32.sp,
                            color = Black,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .background(BlackBrown, RoundedCornerShape(20.dp))
                        .height(70.dp)
                ) {
                    Text(
                        text = "Pay Now",
                        fontFamily = roboto,
                        fontSize = 18.sp,
                        color = White,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(horizontal = 69.dp, vertical = 24.dp)
                    )
                }
            }
        }
    }

}