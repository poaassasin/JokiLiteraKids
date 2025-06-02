package com.example.literalkids.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.literalkids.R
import com.example.literalkids.viewmodel.ProfileViewModel

@Composable
fun ParentProfileUI(
    navController: NavController,
    onBackClick: () -> Unit = { navController.popBackStack() },
) {
    val context = LocalContext.current
    val profileViewModel: ProfileViewModel = viewModel()
    val uiState by profileViewModel.uiState.collectAsState()
    val parentData = uiState.parentData

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
                    text = parentData.fullName,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0A617A),
                    modifier = Modifier.padding(top = 8.dp)
                )

                Text(
                    text = "@${parentData.username}",
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
                    value = parentData.fullName,
                    onValueChange = { newValue ->
                        profileViewModel.updateParentProfile(
                            fullName = newValue,
                            phoneNumber = parentData.phoneNumber,
                            occupation = parentData.occupation,
                            relationship = parentData.relationship,
                            birthDate = parentData.birthDate
                        )
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
                    value = parentData.phoneNumber,
                    onValueChange = { newValue ->
                        profileViewModel.updateParentProfile(
                            fullName = parentData.fullName,
                            phoneNumber = newValue,
                            occupation = parentData.occupation,
                            relationship = parentData.relationship,
                            birthDate = parentData.birthDate
                        )
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
                    currentValue = parentData.occupation,
                    onValueSelected = { newValue ->
                        profileViewModel.updateParentProfile(
                            fullName = parentData.fullName,
                            phoneNumber = parentData.phoneNumber,
                            occupation = newValue,
                            relationship = parentData.relationship,
                            birthDate = parentData.birthDate
                        )
                    }
                )

                Text(
                    text = "Hubungan dengan Anak",
                    fontSize = 16.sp,
                    color = Color(0xFF0A617A),
                    modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
                )

                RelationshipSelector(
                    currentValue = parentData.relationship,
                    onValueSelected = { newValue ->
                        profileViewModel.updateParentProfile(
                            fullName = parentData.fullName,
                            phoneNumber = parentData.phoneNumber,
                            occupation = parentData.occupation,
                            relationship = newValue,
                            birthDate = parentData.birthDate
                        )
                    }
                )

                Text(
                    text = "Tanggal Lahir",
                    fontSize = 16.sp,
                    color = Color(0xFF0A617A),
                    modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
                )

                DatePicker(
                    currentValue = parentData.birthDate,
                    onDateSelected = { newValue ->
                        profileViewModel.updateParentProfile(
                            fullName = parentData.fullName,
                            phoneNumber = parentData.phoneNumber,
                            occupation = parentData.occupation,
                            relationship = parentData.relationship,
                            birthDate = newValue
                        )
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
                    enabled = !parentData.isLoading
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