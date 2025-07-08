package com.example.testcomposeapp.screens

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testcomposeapp.data.Product
import com.example.testcomposeapp.ui.theme.BlackBrown
import com.example.testcomposeapp.ui.theme.lobster
import com.example.testcomposeapp.viewmodel.OrderViewModel
import com.google.android.play.core.integrity.p
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun ProductScreen(
    navController: NavHostController,
    orderViewModel: OrderViewModel,
    productUid: String?
) {
    Log.d("MyLog", "NORM VRODE")
    val fs = Firebase.firestore
    var product: Product? by remember {
        mutableStateOf(Product())
    }

    val safeProductId = productUid ?: "0"


    fs.collection("products").document(safeProductId).get().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            product = task.result.toObject(Product::class.java)
        } else {
            Log.d("MyLog", "${task.exception?.message}")
        }
    }
    Text(
        text = "${product?.uid},${product?.name},${product?.subName},${product?.description}",
        fontFamily = lobster,
        fontSize = 20.sp,
        color = BlackBrown,
    )
}
