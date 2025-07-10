package com.example.testcomposeapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.testcomposeapp.data.OrderUnit
import com.example.testcomposeapp.data.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OrderViewModel : ViewModel() {
    private val _orderItems = MutableStateFlow<List<OrderUnit>>(emptyList())
    val orderItems: StateFlow<List<OrderUnit>> = _orderItems.asStateFlow()

    fun addToOrder(product: Product) {
        val currentOrder = _orderItems.value.toMutableList()
        val existingItem = currentOrder.find { it.product.uid == product.uid }
        if (existingItem != null) {
            if (existingItem.count < 10) {
                existingItem.count += 1
                Log.d("MyLog", "Product +1")
            }
        } else {
            currentOrder.add(OrderUnit(product))
        }
        _orderItems.value = currentOrder
    }

    fun decFromOrder(productUid: String) {
        val currentOrder = _orderItems.value.toMutableList()
        val existingItem = currentOrder.find { it.product.uid == productUid }
        if (existingItem != null && existingItem.count == 1) {
            removeFromOrder(productUid)
        } else if (existingItem != null && existingItem.count > 1) {
            existingItem.count -= 1
            Log.d("MyLog", "Product -1")
            _orderItems.value = currentOrder
        }
    }

    fun getProductCount(productUid: String): Int {
        return _orderItems.value.find { it.product.uid == productUid }?.count ?: 0
    }


    fun removeFromOrder(productUid: String) {
        val currentOrder = _orderItems.value.toMutableList()
        currentOrder.removeAll { it.product.uid == productUid }
        Log.d("MyLog", "Remove from order")
        _orderItems.value = currentOrder
    }

    fun clearOrder() {
        _orderItems.value = emptyList()
    }

    fun getTotalPrice(): Double {
        return _orderItems.value.sumOf { orderUnit ->
            val price = orderUnit.product.price.toDoubleOrNull() ?: 0.0
            price * orderUnit.count
        }
    }
}