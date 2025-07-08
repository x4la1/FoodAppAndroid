package com.example.testcomposeapp.data

data class OrderUnit(
    val product: Product = Product(),
    var count: Int = 1,
)
