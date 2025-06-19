package com.example.literalkids.ui.profile

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.Calendar

@Composable
fun DatePicker(
    currentValue: String,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val colorScheme = MaterialTheme.colorScheme // Mengambil warna dari LiteralkidsTheme

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            onDateSelected("$dayOfMonth/${month + 1}/$year")
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).apply {
        // Kustomisasi latar belakang dialog agar sesuai tema biru-ungu
        datePicker.setBackgroundColor(android.graphics.Color.parseColor("#5AD8FF")) // Biru dari primary
    }

    OutlinedTextField(
        value = currentValue,
        onValueChange = { },
        placeholder = { Text("Pilih tanggal lahir", color = colorScheme.secondary) }, // Ungu
        modifier = Modifier
            .fillMaxWidth()
            .clickable { datePickerDialog.show() },
        shape = RoundedCornerShape(20.dp),
        readOnly = true,
        enabled = false,
        colors = OutlinedTextFieldDefaults.colors(
            disabledBorderColor = colorScheme.primary, // Biru dari tema
            disabledTextColor = colorScheme.onSurface, // Warna teks sesuai tema
            disabledPlaceholderColor = colorScheme.secondary, // Ungu dari tema
            disabledLabelColor = colorScheme.secondary // Ungu untuk label
        )
    )
}