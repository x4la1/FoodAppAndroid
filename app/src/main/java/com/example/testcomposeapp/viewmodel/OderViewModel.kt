package com.example.testcomposeapp.viewmodel

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
            }
        } else {
            currentOrder.add(OrderUnit(product))
        }
        _orderItems.value = currentOrder
    }

    fun decFromOrder(product: Product) {
        val currentOrder = _orderItems.value.toMutableList()
        val existingItem = currentOrder.find { it.product.uid == product.uid }
        if (existingItem != null && existingItem.count > 0) {
            existingItem.count -= 1
        }

        _orderItems.value = currentOrder
    }

    fun removeFromOrder(productUid: String) {
        val currentOrder = _orderItems.value.toMutableList()
        currentOrder.removeAll { it.product.uid == productUid }
        _orderItems.value = currentOrder
    }

    fun clearOrder() {
        _orderItems.value = emptyList()
    }

    fun getTotalPrice(): Double {
        return _orderItems.value.sumOf { it.product.price.toDoubleOrNull() ?: (0.0 * it.count) }
    }
}