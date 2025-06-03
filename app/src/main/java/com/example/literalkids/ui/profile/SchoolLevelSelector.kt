package com.example.literalkids.ui.profile

import androidx.compose.runtime.Composable

@Composable
fun SchoolLevelSelector(
    currentValue: String,
    onValueSelected: (String) -> Unit
) {
    val schoolLevels = listOf("TK", "SD", "SMP", "SMA")

    GenericSelector(
        currentValue = currentValue,
        options = schoolLevels,
        onValueSelected = onValueSelected,
        placeholder = "Pilih jenjang sekolah",
        dialogTitle = "Pilih Jenjang Sekolah",
        cancelButtonText = "Batal"
    )
}