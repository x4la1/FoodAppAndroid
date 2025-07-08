package com.example.testcomposeapp.data

data class Order(
    var list: List<OrderUnit> = listOf(),
    var date: String = "",
    var time: String = "",
)
