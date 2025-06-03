package com.example.literalkids.ui.profile

import androidx.compose.runtime.Composable

@Composable
fun OccupationSelector(
    currentValue: String,
    onValueSelected: (String) -> Unit
) {
    val commonOccupations = listOf(
        "Ibu Rumah Tangga",
        "Pegawai Negeri Sipil (PNS)",
        "Guru/Dosen",
        "Dokter",
        "Perawat/Bidan",
        "Pedagang/Wiraswasta",
        "Karyawan Swasta",
        "Petani",
        "Nelayan",
        "Buruh",
        "Pengacara",
        "Polisi",
        "TNI/Tentara",
        "Politisi",
        "Seniman",
        "Pekerja Sosial",
        "Penulis/Jurnalis",
        "Apoteker",
        "Akuntan",
        "Pengemudi",
        "Tukang/Teknisi",
        "Pensiunan",
        "Tidak Bekerja",
        "Lainnya"
    )

    GenericSelector(
        currentValue = currentValue,
        options = commonOccupations,
        onValueSelected = onValueSelected,
        placeholder = "Pilih pekerjaan",
        dialogTitle = "Pilih pekerjaan",
        cancelButtonText = "Batal"
    )
}