package com.example.literalkids.ui

import androidx.compose.runtime.Composable

@Composable
fun RelationshipSelector(
    currentValue: String,
    onValueSelected: (String) -> Unit
) {
    val commonRelationships = listOf(
        "Ibu",
        "Ayah",
        "Kakak",
        "Paman",
        "Tante",
        "Kakek",
        "Nenek",
        "Wali",
        "Lainnya"
    )

    GenericSelector(
        currentValue = currentValue,
        options = commonRelationships,
        onValueSelected = onValueSelected,
        placeholder = "Pilih hubungan dengan anak",
        dialogTitle = "Pilih hubungan dengan anak",
        cancelButtonText = "Batal"
    )
}