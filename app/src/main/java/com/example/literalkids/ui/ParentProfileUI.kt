package com.example.literalkids.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.literalkids.R

@Composable
fun ParentProfileUI(
    navController: NavController,
    onBackClick: () -> Unit = { navController.popBackStack() },
){
    val context = LocalContext.current

    var state by remember {
        mutableStateOf(
            UserData(
                id = "user12",
                fullName = "Adinda Febyola",
                username = "febydinda",
                level = 38,
                birthDate = "1995-02-20",
                avatarUrl = R.drawable.parent_avatar,
                phoneNumber = "082198765432",
                occupation = "Ibu Rumah Tangga",
                relationship = "Ibu",
                type = "parent",
                isLoading = false
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF5AD8FF),
                                    Color(0xFFDE99FF)
                                )
                            )
                        )
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }

                    Text(
                        text = "Profil Orang Tua",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Box(
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.parent_avatar),
                            contentDescription = "Avatar",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF5AD8FF))
                            .align(Alignment.BottomEnd),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit),
                            contentDescription = "Edit",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Text(
                    text = state.fullName,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0A617A),
                    modifier = Modifier.padding(top = 8.dp)
                )

                Text(
                    text = "@${state.username}",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = "Nama Lengkap",
                    fontSize = 16.sp,
                    color = Color(0xFF0A617A),
                    modifier = Modifier.padding(bottom = 8.dp, top = 16.dp)
                )

                OutlinedTextField(
                    value = state.fullName,
                    onValueChange = { newValue ->
                        state = state.copy(fullName = newValue)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Masukkan nama lengkap") },
                    shape = RoundedCornerShape(20.dp),
                )

                Text(
                    text = "No.Hp",
                    fontSize = 16.sp,
                    color = Color(0xFF0A617A),
                    modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
                )

                OutlinedTextField(
                    value = state.phoneNumber,
                    onValueChange = { newValue ->
                        state = state.copy(phoneNumber = newValue)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Masukkan nomor handphone") },
                    shape = RoundedCornerShape(20.dp),
                )

                Text(
                    text = "Pekerjaan",
                    fontSize = 16.sp,
                    color = Color(0xFF0A617A),
                    modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
                )

                OccupationSelector(
                    currentValue = state.occupation,
                    onValueSelected = { newValue ->
                        state = state.copy(occupation = newValue)
                    }
                )

                Text(
                    text = "Hubungan dengan Anak",
                    fontSize = 16.sp,
                    color = Color(0xFF0A617A),
                    modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
                )

                RelationshipSelector(
                    currentValue = state.relationship,
                    onValueSelected = { newValue ->
                        state = state.copy(relationship = newValue)
                    }
                )

                Text(
                    text = "Tanggal Lahir",
                    fontSize = 16.sp,
                    color = Color(0xFF0A617A),
                    modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
                )

                DatePicker(
                    currentValue = state.birthDate,
                    onDateSelected = { newValue ->
                        state = state.copy(birthDate = newValue)
                    }
                )

                Button(
                    onClick = {
                        Toast.makeText(context, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5AD8FF)
                    ),
                    enabled = !state.isLoading
                ) {
                    Text(
                        text = "Perbarui Profil",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
            }
        }
    }
}

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

@Composable
fun RelationshipSelector(
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
        placeholder = "Pilih hubungan dengan anak",
        dialogTitle = "Pilih hubungan dengan anak",
        cancelButtonText = "Batal"
    )
}