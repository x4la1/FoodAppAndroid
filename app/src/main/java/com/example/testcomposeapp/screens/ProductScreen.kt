package com.example.testcomposeapp.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testcomposeapp.R
import com.example.testcomposeapp.data.Product
import com.example.testcomposeapp.navigation.NavRoute
import com.example.testcomposeapp.ui.theme.BlackBrown
import com.example.testcomposeapp.ui.theme.DefaultRed
import com.example.testcomposeapp.ui.theme.GrayText
import com.example.testcomposeapp.ui.theme.White
import com.example.testcomposeapp.ui.theme.inter
import com.example.testcomposeapp.ui.theme.lobster
import com.example.testcomposeapp.ui.theme.roboto
import com.example.testcomposeapp.utils.base64ToBitmap
import com.example.testcomposeapp.viewmodel.OrderViewModel
import com.google.android.play.core.integrity.p
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

@Composable
fun ProductScreen(
    navController: NavHostController,
    orderViewModel: OrderViewModel,
    productUid: String?
) {

    Log.d("MyLog", "NORM VRODE")
    val fs = Firebase.firestore
    val product = remember {
        mutableStateOf(Product())
    }

    val safeProductUid = productUid ?: "0"

    var productCount by remember {
        mutableIntStateOf(orderViewModel.getProductCount(safeProductUid))
    }

    var totalPrice by remember {
        mutableDoubleStateOf(orderViewModel.getTotalPrice())
    }

    val bitmap = remember {
        mutableStateOf<ImageBitmap?>(null)
    }

    LaunchedEffect(productUid) {
        fs.collection("products").document(safeProductUid).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                product.value = task.result.toObject(Product::class.java)!!
                try {
                    bitmap.value = base64ToBitmap(product.value.imageBase64)!!
                } catch (e: Exception) {
                    Log.d("MyLog", "${e.message}")
                }

            } else {
                Log.d("MyLog", "${task.exception?.message}")
            }
        }
    }

    val df = DecimalFormat("#.00", DecimalFormatSymbols(Locale.US))


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(19.dp)
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

        Image(
            bitmap = bitmap.value ?: ImageBitmap(width = 360, height = 360),
            contentDescription = "product_image",
            modifier = Modifier
                .size(360.dp)
        )

        Text(
            text = product.value.subName,
            fontFamily = roboto,
            fontSize = 25.sp,
            color = BlackBrown,
            fontWeight = FontWeight.SemiBold,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 9.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_star),
                contentDescription = "img"
            )
            Text(
                text = product.value.rating,
                fontFamily = roboto,
                fontSize = 15.sp,
                color = GrayText,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(start = 6.dp)
            )
        }

        Text(
            text = product.value.description,
            fontFamily = roboto,
            fontSize = 16.sp,
            color = GrayText,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .padding(top = 19.dp),
            lineHeight = 27.sp
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 29.dp),
            verticalArrangement = Arrangement.Bottom,

            ) {
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = "Portion",
                    fontFamily = roboto,
                    fontSize = 14.sp,
                    color = BlackBrown,
                    fontWeight = FontWeight.Medium,
                )
                Row(
                    modifier = Modifier
                        .padding(top = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(DefaultRed)
                            .clickable {
                                orderViewModel.decFromOrder(safeProductUid)
                                productCount = orderViewModel.getProductCount(safeProductUid)
                                totalPrice = orderViewModel.getTotalPrice()
                            },
                        contentAlignment = Alignment.Center,

                        ) {
                        Image(
                            painter = painterResource(R.drawable.ic_minus),
                            contentDescription = "",
                            modifier = Modifier
                                .size(12.dp),
                        )

                    }
                    Text(
                        text = "$productCount",
                        fontFamily = inter,
                        fontSize = 18.sp,
                        color = BlackBrown,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(horizontal = 14.dp)
                    )
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(DefaultRed)
                            .clickable {
                                orderViewModel.addToOrder(product.value)
                                productCount = orderViewModel.getProductCount(safeProductUid)
                                totalPrice = orderViewModel.getTotalPrice()
                            },
                        contentAlignment = Alignment.Center,

                        ) {
                        Image(
                            painter = painterResource(R.drawable.ic_plus),
                            contentDescription = "",
                            modifier = Modifier.size(12.dp),
                        )

                    }
                }
            }

            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Row(
                    modifier = Modifier
                        .background(DefaultRed, RoundedCornerShape(20.dp))
                        .width(104.dp)
                        .height(70.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$${
                            String.format(
                                java.util.Locale.US,
                                "%.2f",
                                totalPrice
                            )
                        }",
                        fontFamily = roboto,
                        fontSize = 22.sp,
                        color = White,
                        fontWeight = FontWeight.SemiBold,
                    )
                }

                Row(
                    modifier = Modifier
                        .background(BlackBrown, RoundedCornerShape(20.dp))
                        .height(70.dp)
                ) {
                    Text(
                        text = "ORDER NOW",
                        fontFamily = inter,
                        fontSize = 18.sp,
                        color = White,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(horizontal = 64.dp, vertical = 23.dp)
                            .clickable {
                                navController.navigate(NavRoute.PaymentScreen.route) {
                                    popUpTo(NavRoute.HomeScreen.route) { inclusive = false }
                                }
                            }
                    )
                }
            }
        }

    }

}
