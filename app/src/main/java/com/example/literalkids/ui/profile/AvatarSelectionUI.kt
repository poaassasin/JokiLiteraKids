package com.example.literalkids.ui.profile//package com.example.literalkids.ui.profile
//
//import android.widget.Toast
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.grid.items
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.filled.Check
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.window.Dialog
//import androidx.compose.ui.zIndex
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import com.example.literalkids.R
//import com.example.literalkids.viewmodel.AvatarOption
//import com.example.literalkids.viewmodel.ProfileViewModel
//
//@Composable
//fun AvatarSelectionUI(
//    navController: NavController,
//) {
//    val context = LocalContext.current
//    val profileViewModel: ProfileViewModel = viewModel()
//    val uiState by profileViewModel.uiState.collectAsState()
//
//    val findAvatarByUrl: (Int) -> AvatarOption? = { url ->
//        uiState.avatarOptions.find { it.imageUrl == url }
//    }
//
//    val currentActionAvatar = uiState.selectedAvatarForPurchase ?:
//    findAvatarByUrl(uiState.childData.avatarUrl) ?:
//    uiState.avatarOptions.first()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.background) // Gunakan background dari tema
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(140.dp)
//                .background(
//                    brush = Brush.verticalGradient(
//                        colors = listOf(
//                            MaterialTheme.colorScheme.primary,
//                            MaterialTheme.colorScheme.secondary
//                        )
//                    )
//                )
//        ) {
//            IconButton(
//                onClick = { navController.popBackStack() },
//                modifier = Modifier
//                    .align(Alignment.CenterStart)
//                    .padding(start = 16.dp)
//            ) {
//                Icon(
//                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                    contentDescription = "Kembali",
//                    tint = MaterialTheme.colorScheme.onPrimary
//                )
//            }
//
//            Text(
//                text = "Kembali",
//                color = MaterialTheme.colorScheme.onPrimary,
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Medium,
//                modifier = Modifier
//                    .align(Alignment.CenterStart)
//                    .padding(start = 56.dp)
//            )
//        }
//
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 24.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Image(
//                painter = painterResource(id = uiState.selectedAvatar),
//                contentDescription = "Selected Avatar",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .size(120.dp)
//                    .clip(CircleShape)
//                    .background(MaterialTheme.colorScheme.surface)
//            )
//        }
//
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 16.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Box(
//                modifier = Modifier
//                    .clip(RoundedCornerShape(20.dp))
//                    .background(
//                        brush = Brush.horizontalGradient(
//                            colors = listOf(
//                                MaterialTheme.colorScheme.primary,
//                                MaterialTheme.colorScheme.secondary
//                            )
//                        )
//                    )
//                    .padding(horizontal = 16.dp, vertical = 8.dp)
//            ) {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.coin),
//                        contentDescription = "Coin",
//                        modifier = Modifier.size(24.dp)
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text(
//                        text = "${uiState.childData.coins} koin",
//                        color = MaterialTheme.colorScheme.onPrimary,
//                        fontWeight = FontWeight.Bold
//                    )
//                }
//            }
//        }
//
//        Text(
//            text = "Pilihan Karakter",
//            fontWeight = FontWeight.Bold,
//            fontSize = 20.sp,
//            color = MaterialTheme.colorScheme.onBackground,
//            modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
//        )
//
//        val displayedAvatars = if (uiState.showOnlyOwned) {
//            uiState.avatarOptions.filter { avatar -> avatar.imageUrl in uiState.childData.ownedAvatars }
//        } else {
//            uiState.avatarOptions
//        }
//
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(3),
//            contentPadding = PaddingValues(16.dp),
//            horizontalArrangement = Arrangement.spacedBy(8.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp),
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            items(displayedAvatars) { avatar ->
//                val isSelected = uiState.selectedAvatar == avatar.imageUrl
//                val isOwned = avatar.imageUrl in uiState.childData.ownedAvatars
//
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    modifier = Modifier
//                        .clip(RoundedCornerShape(16.dp))
//                        .background(MaterialTheme.colorScheme.surface)
//                        .border(
//                            width = if (isSelected) 2.dp else 1.dp,
//                            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
//                            shape = RoundedCornerShape(16.dp)
//                        )
//                        .clickable {
//                            if (isOwned) {
//                                profileViewModel.updateAvatar(avatar.imageUrl)
//                                Toast.makeText(context, "Avatar berhasil diperbarui", Toast.LENGTH_SHORT).show()
//                            } else {
//                                profileViewModel.setSelectedAvatarForPurchase(avatar)
//                                profileViewModel.setShowPurchaseDialog(true)
//                            }
//                        }
//                        .padding(8.dp)
//                        .height(140.dp)
//                ) {
//                    Box(
//                        contentAlignment = Alignment.TopEnd,
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        Image(
//                            painter = painterResource(id = avatar.imageUrl),
//                            contentDescription = avatar.name,
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier
//                                .size(60.dp)
//                                .clip(CircleShape)
//                                .align(Alignment.Center)
//                        )
//
//                        if (isSelected) {
//                            Box(
//                                modifier = Modifier
//                                    .size(24.dp)
//                                    .clip(CircleShape)
//                                    .background(MaterialTheme.colorScheme.primary)
//                                    .padding(4.dp),
//                                contentAlignment = Alignment.Center
//                            ) {
//                                Icon(
//                                    imageVector = Icons.Default.Check,
//                                    contentDescription = "Selected",
//                                    tint = MaterialTheme.colorScheme.onPrimary,
//                                    modifier = Modifier.size(16.dp)
//                                )
//                            }
//                        } else if (isOwned) {
//                            Box(
//                                modifier = Modifier
//                                    .size(24.dp)
//                                    .clip(CircleShape)
//                                    .background(Color(0xFF43A047))
//                                    .padding(4.dp),
//                                contentAlignment = Alignment.Center
//                            ) {
//                                Icon(
//                                    imageVector = Icons.Default.Check,
//                                    contentDescription = "Owned",
//                                    tint = MaterialTheme.colorScheme.onPrimary,
//                                    modifier = Modifier.size(16.dp)
//                                )
//                            }
//                        }
//                    }
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    Text(
//                        text = avatar.name,
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Medium,
//                        color = MaterialTheme.colorScheme.onSurface,
//                        maxLines = 1,
//                    )
//
//                    Spacer(modifier = Modifier.height(4.dp))
//
//                    Box(
//                        modifier = Modifier
//                            .height(32.dp)
//                            .fillMaxWidth(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        if (avatar.price > 0 && !isOwned) {
//                            Row(
//                                verticalAlignment = Alignment.CenterVertically,
//                                modifier = Modifier.clip(RoundedCornerShape(12.dp))
//                                    .padding(horizontal = 8.dp, vertical = 4.dp)
//                            ) {
//                                Image(
//                                    painter = painterResource(id = R.drawable.coin),
//                                    contentDescription = "Coin",
//                                    modifier = Modifier.size(24.dp)
//                                )
//                                Spacer(modifier = Modifier.width(4.dp))
//                                Text(
//                                    text = "${avatar.price} Koin",
//                                    fontSize = 12.sp,
//                                    color = MaterialTheme.colorScheme.onSurface
//                                )
//                            }
//                        } else {
//                            Spacer(modifier = Modifier.height(1.dp))
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    if (uiState.showPurchaseDialog && uiState.selectedAvatarForPurchase != null) {
//        PurchaseDialog(
//            avatarOption = uiState.selectedAvatarForPurchase!!,
//            onDismiss = {
//                profileViewModel.setShowPurchaseDialog(false)
//            },
//            onConfirmPurchase = {
//                profileViewModel.purchaseAvatar(
//                    uiState.selectedAvatarForPurchase!!.imageUrl,
//                    uiState.selectedAvatarForPurchase!!.price
//                )
//                profileViewModel.setShowPurchaseDialog(false)
//            }
//        )
//    }
//
//    if (uiState.purchaseSuccess) {
//        SuccessDialog(
//            showDialog = true,
//            avatarOption = currentActionAvatar,
//            message = "Avatar berhasil dibeli!",
//            onDismiss = {
//                profileViewModel.resetPurchaseStatus()
//                profileViewModel.updateAvatar(currentActionAvatar.imageUrl)
//            }
//        )
//    }
//
//    if (uiState.purchaseError != null) {
//        ErrorDialog(
//            showDialog = true,
//            avatarOption = currentActionAvatar,
//            message = uiState.purchaseError ?: "Gagal membeli avatar. Silakan coba lagi.",
//            onDismiss = { profileViewModel.resetPurchaseStatus() }
//        )
//    }
//
//    if (uiState.isLoading) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.Black.copy(alpha = 0.5f)),
//            contentAlignment = Alignment.Center
//        ) {
//            CircularProgressIndicator(
//                color = MaterialTheme.colorScheme.primary
//            )
//        }
//    }
//}
//
//@Composable
//fun ErrorDialog(
//    showDialog: Boolean,
//    avatarOption: AvatarOption,
//    message: String = "Gagal membeli avatar. Silakan coba lagi.",
//    onDismiss: () -> Unit
//) {
//    if (showDialog) {
//        Dialog(onDismissRequest = onDismiss) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.robot_crying),
//                    contentDescription = "Robot",
//                    modifier = Modifier
//                        .size(160.dp)
//                        .offset(y = (-45).dp, x = (-10).dp)
//                        .align(Alignment.TopCenter)
//                        .zIndex(2f)
//                )
//
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 70.dp),
//                    shape = RoundedCornerShape(16.dp),
//                    colors = CardDefaults.cardColors(
//                        containerColor = MaterialTheme.colorScheme.surface
//                    ),
//                    elevation = CardDefaults.cardElevation(
//                        defaultElevation = 8.dp
//                    )
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp),
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Text(
//                            text = "Gagal",
//                            color = MaterialTheme.colorScheme.error,
//                            fontSize = 24.sp,
//                            fontWeight = FontWeight.Bold,
//                            textAlign = TextAlign.Center,
//                            modifier = Modifier.padding(vertical = 16.dp)
//                        )
//
//                        OutlinedCard(
//                            modifier = Modifier
//                                .padding(16.dp)
//                                .width(150.dp),
//                            shape = RoundedCornerShape(16.dp),
//                            colors = CardDefaults.cardColors(
//                                containerColor = MaterialTheme.colorScheme.surface
//                            ),
//                        ) {
//                            Column(
//                                modifier = Modifier
//                                    .padding(16.dp)
//                                    .fillMaxWidth(),
//                                horizontalAlignment = Alignment.CenterHorizontally,
//                                verticalArrangement = Arrangement.Center
//                            ) {
//                                Image(
//                                    painter = painterResource(id = avatarOption.imageUrl),
//                                    contentDescription = avatarOption.name,
//                                    contentScale = ContentScale.Crop,
//                                    modifier = Modifier
//                                        .size(80.dp)
//                                        .clip(RoundedCornerShape(8.dp))
//                                )
//
//                                Spacer(modifier = Modifier.height(8.dp))
//
//                                Text(
//                                    text = avatarOption.name,
//                                    fontSize = 18.sp,
//                                    fontWeight = FontWeight.Medium,
//                                    color = MaterialTheme.colorScheme.onSurface,
//                                    textAlign = TextAlign.Center,
//                                    modifier = Modifier.padding(horizontal = 8.dp)
//                                )
//
//                                Row(
//                                    verticalAlignment = Alignment.CenterVertically,
//                                    horizontalArrangement = Arrangement.Center
//                                ) {
//                                    Image(
//                                        painter = painterResource(id = R.drawable.coin),
//                                        contentDescription = "Coin",
//                                        modifier = Modifier.size(24.dp)
//                                    )
//                                    Spacer(modifier = Modifier.width(4.dp))
//                                    Text(
//                                        text = "${avatarOption.price} Koin",
//                                        fontSize = 16.sp,
//                                        color = MaterialTheme.colorScheme.onSurface
//                                    )
//                                }
//                            }
//                        }
//
//                        Text(
//                            text = message,
//                            color = MaterialTheme.colorScheme.onSurface,
//                            fontSize = 16.sp,
//                            textAlign = TextAlign.Center,
//                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
//                        )
//
//                        Button(
//                            onClick = onDismiss,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(48.dp)
//                                .padding(horizontal = 32.dp),
//                            shape = RoundedCornerShape(24.dp),
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = MaterialTheme.colorScheme.error,
//                                contentColor = MaterialTheme.colorScheme.onError
//                            )
//                        ) {
//                            Text(
//                                text = "OK",
//                                fontSize = 16.sp,
//                                fontWeight = FontWeight.Medium
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun PurchaseDialog(
//    avatarOption: AvatarOption,
//    onDismiss: () -> Unit,
//    onConfirmPurchase: () -> Unit
//) {
//    Dialog(onDismissRequest = onDismiss) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.robot_teddy),
//                contentDescription = "Robot",
//                modifier = Modifier
//                    .size(160.dp)
//                    .offset(y = (-50).dp)
//                    .align(Alignment.TopCenter)
//                    .zIndex(2f)
//            )
//
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 70.dp),
//                shape = RoundedCornerShape(16.dp),
//                colors = CardDefaults.cardColors(
//                    containerColor = MaterialTheme.colorScheme.surface
//                ),
//                elevation = CardDefaults.cardElevation(
//                    defaultElevation = 8.dp
//                )
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(
//                        text = "Beli Item",
//                        color = MaterialTheme.colorScheme.onSurface,
//                        fontSize = 24.sp,
//                        fontWeight = FontWeight.Bold,
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier.padding(vertical = 16.dp)
//                    )
//
//                    OutlinedCard(
//                        modifier = Modifier
//                            .padding(16.dp)
//                            .width(150.dp),
//                        shape = RoundedCornerShape(16.dp),
//                        colors = CardDefaults.cardColors(
//                            containerColor = MaterialTheme.colorScheme.surface
//                        ),
//                    ) {
//                        Column(
//                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
//                            horizontalAlignment = Alignment.CenterHorizontally,
//                            verticalArrangement = Arrangement.Center
//                        ) {
//                            Image(
//                                painter = painterResource(id = avatarOption.imageUrl),
//                                contentDescription = avatarOption.name,
//                                contentScale = ContentScale.Crop,
//                                modifier = Modifier
//                                    .size(80.dp)
//                                    .clip(RoundedCornerShape(8.dp))
//                            )
//
//                            Spacer(modifier = Modifier.height(8.dp))
//
//                            Text(
//                                text = avatarOption.name,
//                                fontSize = 18.sp,
//                                fontWeight = FontWeight.Medium,
//                                color = MaterialTheme.colorScheme.onSurface,
//                                textAlign = TextAlign.Center
//                            )
//
//                            Spacer(modifier = Modifier.height(8.dp))
//
//                            Row(
//                                verticalAlignment = Alignment.CenterVertically,
//                                horizontalArrangement = Arrangement.Center
//                            ) {
//                                Image(
//                                    painter = painterResource(id = R.drawable.coin),
//                                    contentDescription = "Coin",
//                                    modifier = Modifier.size(24.dp)
//                                )
//                                Spacer(modifier = Modifier.width(4.dp))
//                                Text(
//                                    text = "${avatarOption.price} Koin",
//                                    fontSize = 16.sp,
//                                    color = MaterialTheme.colorScheme.onSurface
//                                )
//                            }
//                        }
//                    }
//
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(top = 16.dp, bottom = 8.dp),
//                        horizontalArrangement = Arrangement.spacedBy(8.dp)
//                    ) {
//                        Button(
//                            onClick = onDismiss,
//                            modifier = Modifier
//                                .weight(1f)
//                                .height(48.dp),
//                            shape = RoundedCornerShape(24.dp),
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
//                                contentColor = MaterialTheme.colorScheme.primary
//                            )
//                        ) {
//                            Text(
//                                text = "Batal",
//                                fontSize = 16.sp,
//                                fontWeight = FontWeight.Medium
//                            )
//                        }
//
//                        Button(
//                            onClick = onConfirmPurchase,
//                            modifier = Modifier
//                                .weight(1f)
//                                .height(48.dp),
//                            shape = RoundedCornerShape(24.dp),
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = MaterialTheme.colorScheme.primary,
//                                contentColor = MaterialTheme.colorScheme.onPrimary
//                            )
//                        ) {
//                            Text(
//                                text = "Beli",
//                                fontSize = 16.sp,
//                                fontWeight = FontWeight.Medium
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun SuccessDialog(
//    showDialog: Boolean,
//    avatarOption: AvatarOption,
//    message: String = "Avatar berhasil dibeli!",
//    onDismiss: () -> Unit
//) {
//    if (showDialog) {
//        Dialog(onDismissRequest = onDismiss) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.robot_money),
//                    contentDescription = "Robot",
//                    modifier = Modifier
//                        .size(160.dp)
//                        .offset(y = (-40).dp)
//                        .align(Alignment.TopCenter)
//                        .zIndex(2f)
//                )
//
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 70.dp),
//                    shape = RoundedCornerShape(16.dp),
//                    colors = CardDefaults.cardColors(
//                        containerColor = MaterialTheme.colorScheme.surface
//                    ),
//                    elevation = CardDefaults.cardElevation(
//                        defaultElevation = 8.dp
//                    )
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp),
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Text(
//                            text = "Berhasil!",
//                            color = MaterialTheme.colorScheme.onSurface,
//                            fontSize = 24.sp,
//                            fontWeight = FontWeight.Bold,
//                            textAlign = TextAlign.Center,
//                            modifier = Modifier.padding(vertical = 16.dp)
//                        )
//
//                        OutlinedCard(
//                            modifier = Modifier
//                                .padding(16.dp)
//                                .width(150.dp),
//                            shape = RoundedCornerShape(16.dp),
//                            colors = CardDefaults.cardColors(
//                                containerColor = MaterialTheme.colorScheme.surface
//                            ),
//                        ) {
//                            Column(
//                                modifier = Modifier
//                                    .padding(16.dp)
//                                    .fillMaxWidth(),
//                                horizontalAlignment = Alignment.CenterHorizontally,
//                                verticalArrangement = Arrangement.Center
//                            ) {
//                                Image(
//                                    painter = painterResource(id = avatarOption.imageUrl),
//                                    contentDescription = avatarOption.name,
//                                    contentScale = ContentScale.Crop,
//                                    modifier = Modifier
//                                        .size(80.dp)
//                                        .clip(RoundedCornerShape(8.dp))
//                                )
//
//                                Spacer(modifier = Modifier.height(8.dp))
//
//                                Text(
//                                    text = avatarOption.name,
//                                    fontSize = 18.sp,
//                                    fontWeight = FontWeight.Medium,
//                                    color = MaterialTheme.colorScheme.onSurface,
//                                    textAlign = TextAlign.Center,
//                                    modifier = Modifier.padding(horizontal = 8.dp)
//                                )
//
//                                Row(
//                                    verticalAlignment = Alignment.CenterVertically,
//                                    horizontalArrangement = Arrangement.Center
//                                ) {
//                                    Image(
//                                        painter = painterResource(id = R.drawable.coin),
//                                        contentDescription = "Coin",
//                                        modifier = Modifier.size(24.dp)
//                                    )
//                                    Spacer(modifier = Modifier.width(4.dp))
//                                    Text(
//                                        text = "${avatarOption.price} Koin",
//                                        fontSize = 16.sp,
//                                        color = MaterialTheme.colorScheme.onSurface
//                                    )
//                                }
//                            }
//                        }
//
//                        Text(
//                            text = message,
//                            color = MaterialTheme.colorScheme.onSurface,
//                            fontSize = 16.sp,
//                            textAlign = TextAlign.Center,
//                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
//                        )
//
//                        Button(
//                            onClick = onDismiss,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(48.dp)
//                                .padding(horizontal = 32.dp),
//                            shape = RoundedCornerShape(24.dp),
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = MaterialTheme.colorScheme.primary,
//                                contentColor = MaterialTheme.colorScheme.onPrimary
//                            )
//                        ) {
//                            Text(
//                                text = "OK",
//                                fontSize = 16.sp,
//                                fontWeight = FontWeight.Medium
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}