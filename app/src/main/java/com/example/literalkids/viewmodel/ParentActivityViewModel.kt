package com.example.literalkids.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ParentActivityViewModel : ViewModel() {
    // State for dropdown menu selection
    var selected = mutableStateOf("Mingguan")
        private set

    // State for dropdown menu expanded flag
    var expanded = mutableStateOf(false)
        private set

    // Data for BarChart
    val data = listOf(0.2f, 0.4f, 0.6f, 0.4f, 0.5f, 0.9f, 0.0f)
    val labels = listOf("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu")

    // Logic for dropdown menu
    fun onDropdownMenuItemSelected(item: String) {
        selected.value = item
        expanded.value = false
    }

    // Function to toggle dropdown menu
    fun toggleDropdownMenu() {
        expanded.value = !expanded.value
    }
}