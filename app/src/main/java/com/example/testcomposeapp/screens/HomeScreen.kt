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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testcomposeapp.R
import com.example.testcomposeapp.data.Product
import com.example.testcomposeapp.data.User
import com.example.testcomposeapp.navigation.NavRoute
import com.example.testcomposeapp.ui.theme.BlackBrown
import com.example.testcomposeapp.ui.theme.DefaultRed
import com.example.testcomposeapp.ui.theme.GrayText2
import com.example.testcomposeapp.ui.theme.White
import com.example.testcomposeapp.ui.theme.inter
import com.example.testcomposeapp.ui.theme.lobster
import com.example.testcomposeapp.ui.theme.poppins
import com.example.testcomposeapp.ui.theme.roboto
import com.example.testcomposeapp.utils.base64ToBitmap
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun HomeScreen(navController: NavHostController) {

    val fs = Firebase.firestore
    val products = remember {
        mutableStateOf(emptyList<Product>())
    }

    Log.d("MyLog", "home")

    fs.collection("products").get().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            products.value = task.result.toObjects(Product::class.java)
            Log.d("MyLog", "Products get OK")
        } else {
            Log.d("MyLog", "${task.exception?.message}")
        }
    }

    val user = remember {
        mutableStateOf(User())
    }

    val auth = Firebase.auth
    val avatarBase64: String = ""

    val authUser = auth.currentUser
    if (authUser != null) {
        fs.collection("users").document(authUser.uid).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                user.value = task.result.toObject(User::class.java)!!
                Log.d("MyLog", "User get OK")
            } else {
                Log.d("MyLog", "User get BAD, ${task.exception?.message}")
            }
        }
    }


    var selectedCategory by remember {
        mutableStateOf("All")
    }

    val categories = listOf("All", "Combos", "Sliders", "Classic")


    val filteredProducts = if (selectedCategory == "All") {
        products.value
    } else {
        products.value.filter { it.category == selectedCategory }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White),
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(70.dp)
                    .fillMaxWidth()
                    .background(DefaultRed),
                actions = {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(DefaultRed)
                            .padding(top = 17.dp)
                            .padding(horizontal = 30.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        )
                        {
                            Icon(
                                painter = painterResource(R.drawable.ic_home),
                                contentDescription = "HomeIcon",
                                tint = White,
                                modifier = Modifier
                                    .clickable {

                                    }
                            )
                            Icon(
                                painter = painterResource(R.drawable.ic_dot),
                                contentDescription = "DotIcon",
                                tint = White,
                                modifier = Modifier
                                    .padding(top = 8.dp)
                            )
                        }

                        Icon(
                            painter = painterResource(R.drawable.ic_user),
                            contentDescription = "UserIcon",
                            tint = White,
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(NavRoute.UserProfileScreen.route)
                                }
                        )
                        Icon(
                            painter = painterResource(R.drawable.ic_shopping_cart),
                            contentDescription = "CartIcon",
                            tint = White,
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(NavRoute.PaymentScreen.route)
                                }
                        )
                        Icon(
                            painter = painterResource(R.drawable.ic_heart),
                            contentDescription = "HeartIcon",
                            tint = White,
                            modifier = Modifier
                                .clickable {

                                }
                        )
                    }
                }
            )

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White)
                .padding(innerPadding)
                .padding(top = 20.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 19.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Foodgo",
                    fontFamily = lobster,
                    fontSize = 45.sp,
                    color = BlackBrown,
                )

                base64ToBitmap(user.value.imageBase64)?.let {
                    Image(
                        bitmap = it,
                        contentDescription = "img2",
                        modifier = Modifier
                            .size(height = 60.dp, width = 60.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

            }
            Text(
                text = "Order your favourite food!",
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                color = GrayText2,
                modifier = Modifier
                    .padding(horizontal = 19.dp)
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .padding(top = 50.dp)
                    .padding(horizontal = 19.dp)
                    .height(50.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                itemsIndexed(categories) { _, item ->
                    if (item == selectedCategory) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .background(DefaultRed, shape = RoundedCornerShape(20.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = item,
                                fontFamily = inter,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                color = White,
                                modifier = Modifier
                                    .padding(horizontal = 27.dp)
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .background(Color.Transparent, shape = RoundedCornerShape(20.dp))
                                .clickable(onClick = {
                                    selectedCategory = item
                                }),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = item,
                                fontFamily = inter,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                color = GrayText2,
                                modifier = Modifier
                                    .padding(horizontal = 27.dp)
                            )
                        }
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp)
                    .padding(horizontal = 19.dp),
                verticalArrangement = Arrangement.spacedBy(31.dp)

            ) {
                items(filteredProducts.chunked(2)) { pair ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(White),
                        horizontalArrangement = Arrangement.spacedBy(22.dp),
                    ) {
                        pair.forEach { product ->
                            Card(
                                modifier = Modifier
                                    .background(White)
                                    .weight(1f)
                                    .height(225.dp),
                                shape = RoundedCornerShape(20.dp),
                                elevation = CardDefaults.elevatedCardElevation(2.dp),
                                onClick = {
                                    navController.navigate("product_screen/${product.uid}")
                                }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(White)
                                        .padding(horizontal = 11.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Image(
                                        bitmap = base64ToBitmap(product.imageBase64)!!,
                                        contentDescription = "img",
                                        modifier = Modifier
                                            .size(120.dp)
                                    )

                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalAlignment = Alignment.Start,

                                        ) {
                                        Text(
                                            text = product.name,
                                            fontFamily = roboto,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 16.sp,
                                            color = BlackBrown,
                                            modifier = Modifier
                                                .padding(top = 9.dp)
                                        )
                                        Text(
                                            text = product.subName,
                                            fontFamily = roboto,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 16.sp,
                                            color = BlackBrown,
                                        )
                                    }

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 9.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painter = painterResource(R.drawable.ic_star),
                                                contentDescription = "img"
                                            )
                                            Text(
                                                text = product.rating,
                                                fontFamily = roboto,
                                                fontWeight = FontWeight.SemiBold,
                                                fontSize = 16.sp,
                                                color = BlackBrown,
                                                modifier = Modifier
                                                    .padding(start = 5.dp)
                                            )
                                        }
                                        Text(
                                            text = "$${product.price}",
                                            fontFamily = roboto,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 16.sp,
                                            color = BlackBrown,
                                        )
                                    }
                                }
                            }
                            if (pair.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }

                }

            }

        }
    }
}






