package com.example.literalkids.ui

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.literalkids.R

data class AvatarOption(
    val id: Int,
    val name: String,
    val price: Int,
    val imageUrl: Int
)

@Composable
fun AvatarSelectionUI(
    navController: NavController,
) {

    val context = LocalContext.current

    var uiState by remember {
        mutableStateOf(
            UserData(
                id = "user11",
                fullName = "Levi Annora",
                username = "leviannora",
                level = 7,
                currentXp = 90,
                maxXp = 100,
                age = 5,
                gender = "perempuan",
                schoolLevel = "TK",
                birthDate = "7/4/2025",
                avatarUrl = R.drawable.default_avatar,
                coins = 450,
                type = "child",
                ownedAvatars = listOf(
                    R.drawable.default_avatar
                ),
                isLoading = false
            )
        )
    }

    val avatarOptions = listOf(
        AvatarOption(1, "Penyihir", 200, R.drawable.default_avatar),
        AvatarOption(2, "Dinosaurus", 200, R.drawable.avatar_dino),
        AvatarOption(3, "Kuda Poni", 200, R.drawable.avatar_poni),
        AvatarOption(4, "Bunga", 200, R.drawable.avatar_bunga),
        AvatarOption(5, "Katak", 200, R.drawable.avatar_katak),
        AvatarOption(6, "Ksatria", 250, R.drawable.avatar_ksatria)
    )

    var selectedAvatar by remember(uiState.avatarUrl) { mutableIntStateOf(uiState.avatarUrl) }
    var showOnlyOwned by remember { mutableStateOf(false) }
    var showPurchaseDialog by remember { mutableStateOf(false) }
    var selectedAvatarForPurchase by remember { mutableStateOf<AvatarOption?>(null) }
    var purchaseSuccess by remember { mutableStateOf(false) }
    var purchaseError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val findAvatarByUrl: (Int) -> AvatarOption? = { url ->
        avatarOptions.find { it.imageUrl == url }
    }

    val currentActionAvatar = selectedAvatarForPurchase ?:
    findAvatarByUrl(uiState.avatarUrl) ?:
    avatarOptions.first()

    fun updateAvatar(imageUrl: Int) {
        uiState = uiState.copy(avatarUrl = imageUrl)
        selectedAvatar = imageUrl
        Toast.makeText(context, "Avatar berhasil diperbarui", Toast.LENGTH_SHORT).show()
    }

    fun purchaseAvatar(imageUrl: Int, price: Int) {
        isLoading = true

        Handler(Looper.getMainLooper()).postDelayed({
            isLoading = false

            if (uiState.coins >= price) {
                // Update state with new owned avatar and reduced coins
                val newOwnedAvatars = uiState.ownedAvatars.toMutableList()
                if (!newOwnedAvatars.contains(imageUrl)) {
                    newOwnedAvatars.add(imageUrl)
                }

                uiState = uiState.copy(
                    ownedAvatars = newOwnedAvatars,
                    coins = uiState.coins - price
                )

                purchaseSuccess = true
            } else {
                purchaseError = "Koin tidak cukup untuk membeli avatar ini."
            }
        }, 1000)
    }

    fun resetPurchaseStatus() {
        purchaseSuccess = false
        purchaseError = null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
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
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Kembali",
                    tint = Color.White
                )
            }

            Text(
                text = "Kembali",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 56.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = selectedAvatar),
                contentDescription = "Selected Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF5AD8FF),
                                Color(0xFFDE99FF)
                            )
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.coin),
                        contentDescription = "Coin",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${uiState.coins} koin",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Text(
            text = "Pilihan Karakter",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color(0xFF1D7193),
            modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
        )

        val displayedAvatars = if (showOnlyOwned) {
            avatarOptions.filter { avatar -> avatar.imageUrl in uiState.ownedAvatars }
        } else {
            avatarOptions
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(displayedAvatars) { avatar ->
                val isSelected = selectedAvatar == avatar.imageUrl

                val isOwned = avatar.imageUrl in uiState.ownedAvatars

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .border(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) Color(0xFF5DCCF8) else Color.LightGray,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable {
                            if (isOwned) {
                                selectedAvatar = avatar.imageUrl
                                updateAvatar(avatar.imageUrl)
                            } else {
                                selectedAvatarForPurchase = avatar
                                showPurchaseDialog = true
                            }
                        }
                        .padding(8.dp)
                        .height(140.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.TopEnd,
                        modifier = Modifier.weight(1f)
                    ) {
                        Image(
                            painter = painterResource(id = avatar.imageUrl),
                            contentDescription = avatar.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .align(Alignment.Center)
                        )

                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF5DCCF8))
                                    .padding(4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        } else if (isOwned) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF43A047))
                                    .padding(4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Owned",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = avatar.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF1D7193),
                        maxLines = 1,
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Box(
                        modifier = Modifier
                            .height(32.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (avatar.price > 0 && !isOwned) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clip(RoundedCornerShape(12.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.coin),
                                    contentDescription = "Coin",
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${avatar.price} Koin",
                                    fontSize = 12.sp,
                                    color = Color(0xFF1D7193)
                                )
                            }
                        } else {
                            Spacer(modifier = Modifier.height(1.dp))
                        }
                    }
                }
            }
        }
    }

    if (showPurchaseDialog && selectedAvatarForPurchase != null) {
        PurchaseDialog(
            avatarOption = selectedAvatarForPurchase!!,
            onDismiss = {
                showPurchaseDialog = false
            },
            onConfirmPurchase = {
                purchaseAvatar(
                    selectedAvatarForPurchase!!.imageUrl,
                    selectedAvatarForPurchase!!.price
                )

                showPurchaseDialog = false
            }
        )
    }

    if (purchaseSuccess) {
        SuccessDialog(
            showDialog = true,
            avatarOption = currentActionAvatar,
            message = "Avatar berhasil dibeli!",
            onDismiss = {
                resetPurchaseStatus()
                selectedAvatar = currentActionAvatar.imageUrl
            }
        )
    }

    if (purchaseError != null) {
        ErrorDialog(
            showDialog = true,
            avatarOption = currentActionAvatar,
            message = purchaseError ?: "Gagal membeli avatar. Silakan coba lagi.",
            onDismiss = { resetPurchaseStatus() }
        )
    }

    if (uiState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color(0xFF5DCCF8)
            )
        }
    }
}

@Composable
fun ErrorDialog(
    showDialog: Boolean,
    avatarOption: AvatarOption,
    message: String = "Gagal membeli avatar. Silakan coba lagi.",
    onDismiss: () -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.robot_crying),
                    contentDescription = "Robot",
                    modifier = Modifier
                        .size(160.dp)
                        .offset(y = (-45).dp, x = (-10).dp)
                        .align(Alignment.TopCenter)
                        .zIndex(2f)
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 70.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Gagal",
                            color = Color(0xFFE53935),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )

                        OutlinedCard(
                            modifier = Modifier
                                .padding(16.dp)
                                .width(150.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(id = avatarOption.imageUrl),
                                    contentDescription = avatarOption.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = avatarOption.name,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF1D7193),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.coin),
                                        contentDescription = "Coin",
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "${avatarOption.price} Koin",
                                        fontSize = 16.sp,
                                        color = Color(0xFF1D7193)
                                    )
                                }
                            }
                        }

                        Text(
                            text = message,
                            color = Color(0xFF5A5A5A),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                        )

                        Button(
                            onClick = onDismiss,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .padding(horizontal = 32.dp),
                            shape = RoundedCornerShape(24.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFE53935),
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "OK",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PurchaseDialog(
    avatarOption: AvatarOption,
    onDismiss: () -> Unit,
    onConfirmPurchase: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.robot_teddy),
                contentDescription = "Robot",
                modifier = Modifier
                    .size(160.dp)
                    .offset(y = (-50).dp)
                    .align(Alignment.TopCenter)
                    .zIndex(2f)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 70.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Beli Item",
                        color = Color(0xFF1D7193),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )

                    OutlinedCard(
                        modifier = Modifier
                            .padding(16.dp)
                            .width(150.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = avatarOption.imageUrl),
                                contentDescription = avatarOption.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = avatarOption.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF1D7193),
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.coin),
                                    contentDescription = "Coin",
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${avatarOption.price} Koin",
                                    fontSize = 16.sp,
                                    color = Color(0xFF1D7193)
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = onDismiss,
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            shape = RoundedCornerShape(24.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFE0F7FF),
                                contentColor = Color(0xFF5DCCF8)
                            )
                        ) {
                            Text(
                                text = "Batal",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Button(
                            onClick = onConfirmPurchase,
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            shape = RoundedCornerShape(24.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF5DCCF8),
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Beli",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SuccessDialog(
    showDialog: Boolean,
    avatarOption: AvatarOption,
    message: String = "Avatar berhasil dibeli!",
    onDismiss: () -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.robot_money),
                    contentDescription = "Robot",
                    modifier = Modifier
                        .size(160.dp)
                        .offset(y = (-40).dp)
                        .align(Alignment.TopCenter)
                        .zIndex(2f)
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 70.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Berhasil!",
                            color = Color(0xFF1D7193),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )

                        OutlinedCard(
                            modifier = Modifier
                                .padding(16.dp)
                                .width(150.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(id = avatarOption.imageUrl),
                                    contentDescription = avatarOption.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = avatarOption.name,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF1D7193),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.coin),
                                        contentDescription = "Coin",
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "${avatarOption.price} Koin",
                                        fontSize = 16.sp,
                                        color = Color(0xFF1D7193)
                                    )
                                }
                            }
                        }

                        Text(
                            text = message,
                            color = Color(0xFF5A5A5A),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                        )

                        Button(
                            onClick = onDismiss,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .padding(horizontal = 32.dp),
                            shape = RoundedCornerShape(24.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF5DCCF8),
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "OK",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}