package com.example.literalkids

import SyncWorker
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.literalkids.navigation.Screen
import com.example.literalkids.ui.ArticleDetailScreen
import com.example.literalkids.ui.ParentActivityScreen
//import com.example.literalkids.ui.profile.AvatarSelectionUI
import com.example.literalkids.ui.bacaBuku.BacaCeritaScreenUI
import com.example.literalkids.ui.profile.ChildProfileUI
import com.example.literalkids.ui.search.GenreSearchUI
import com.example.literalkids.ui.bacaBuku.HomeBacaCerita
import com.example.literalkids.ui.leaderboard.LeaderboardUI
import com.example.literalkids.ui.perpustakaan.LibraryScreen
import com.example.literalkids.ui.auth.LoginUI
import com.example.literalkids.ui.profile.ParentProfileUI
import com.example.literalkids.ui.profile.ProfileUI
import com.example.literalkids.ui.quiz.QuizResultScreen
import com.example.literalkids.ui.quiz.QuizScreen
import com.example.literalkids.ui.auth.RegisterUI
import com.example.literalkids.ui.search.SearchUI
import com.example.literalkids.ui.subscription.SubscriptionUI
import com.example.literalkids.ui.homepage.HomepageUI
import com.example.literalkids.ui.boardingPage.OnboardingScreen
import com.example.literalkids.ui.theme.LiteralkidsTheme
import com.example.literalkids.viewmodel.ProfileViewModel
import com.example.literalkids.data.local.TokenManager // <-- Tambahkan Import
import com.example.literalkids.data.network.ApiClient // <-- Tambahkan Import
import com.example.literalkids.data.repository.AuthRepository // <-- Tambahkan Import
import com.example.literalkids.viewmodel.LoginViewModel // <-- Tambahkan Import
import com.example.literalkids.viewmodel.LoginViewModelFactory // <-- Tambahkan Import
import androidx.compose.ui.platform.LocalContext // <-- Tambahkan Import
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.literalkids.data.local.LiterakidsDatabase
import com.example.literalkids.data.repository.StoryRepository
import com.example.literalkids.viewmodel.HomepageViewModel
import com.example.literalkids.viewmodel.HomepageViewModelFactory
import com.example.literalkids.viewmodel.OnboardingViewModel
import com.example.literalkids.viewmodel.OnboardingViewModelFactory
import com.example.literalkids.viewmodel.ProfileViewModelFactory
import com.example.literalkids.viewmodel.RegisterViewModel
import com.example.literalkids.viewmodel.RegisterViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainNavigation()
        }
    }
}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    val context = LocalContext.current

    val database = LiterakidsDatabase.getDatabase(context)
    val userDao = database.userDao()

    // 2. Buat semua objek yang dibutuhkan, cukup sekali di sini
    val apiService = ApiClient.getApiService(context) // Sesuaikan jika ApiClient Anda butuh context
    val tokenManager = TokenManager(context)
    val authRepository = AuthRepository(apiService, tokenManager)

    val storyRepository = StoryRepository(apiService, userDao)
    val homepageViewModelFactory = HomepageViewModelFactory(storyRepository)

    // 3. Buat Factory yang akan digunakan untuk membuat ViewModel
    val loginViewModelFactory = LoginViewModelFactory(authRepository)
    val registerViewModelFactory = RegisterViewModelFactory(authRepository)
    val onboardingViewModelFactory = OnboardingViewModelFactory(authRepository)
    val profileViewModelFactory = ProfileViewModelFactory(authRepository)

    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED) // Hanya berjalan saat ada koneksi
        .build()

    val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance(context).enqueueUniqueWork(
        "sync_user_data",
        ExistingWorkPolicy.KEEP, // Jangan jalankan jika sudah ada yang berjalan
        syncRequest
    )

    LiteralkidsTheme(darkTheme = false) {
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route
        ) {
            composable(
                route = Screen.StoryDetail.route,
                arguments = listOf(navArgument("storyId") { type = NavType.IntType })
            ) { backStackEntry ->
                // Ambil argumen storyId dari rute
                val storyId = backStackEntry.arguments?.getInt("storyId")

                // Untuk sekarang, kita tampilkan halaman placeholder
                // Nanti Anda bisa ganti dengan UI detail cerita Anda yang sebenarnya
                if (storyId != null) {
                    StoryDetailPagePlaceholder(storyId = storyId, navController = navController)
                } else {
                    // Handle kasus jika ID tidak ditemukan (seharusnya tidak terjadi)
                    Text("Error: Story ID tidak ditemukan!")
                }
            }

            // Halaman Login
            composable(Screen.Login.route) {
                val loginViewModel: LoginViewModel = viewModel(factory = loginViewModelFactory)
                LoginUI(navController = navController, viewModel = loginViewModel)
            }

            // Halaman Register
            composable(Screen.Register.route) {
                val registerViewModel: RegisterViewModel = viewModel(factory = registerViewModelFactory)
                RegisterUI(navController = navController, viewModel = registerViewModel)
            }

            /*
            ==========================================================================
            PUNYA ALVIA DINI NUR LATHIFAH
            ==========================================================================
             */
            // Halaman OnBoarding1
            composable(Screen.OnBoarding1.route) {
                val onboardingViewModel: OnboardingViewModel = viewModel(factory = onboardingViewModelFactory)
                OnboardingScreen(navController = navController, viewModel = onboardingViewModel)
            }

            // Halaman Homepage
            composable(Screen.Homepage.route) {
                val homepageViewModel: HomepageViewModel = viewModel(factory = homepageViewModelFactory)
                HomepageUI(navController = navController, viewModel = homepageViewModel)
            }
            /*
            ==========================================================================
            PUNYA ALVIA DINI NUR LATHIFAH
            ==========================================================================
             */

            // Halaman Subscription
            composable(Screen.Subscription.route) {
                SubscriptionUI(navController = navController)
            }

            // Halaman Perpus
            composable(Screen.Perpustakaan.route) {
                LibraryScreen(navController = navController)
            }

            // Halaman Search
            composable(Screen.Search.route) {
                SearchUI(navController = navController)
            }

            // Halaman Genre
            composable(Screen.GenreSelector.route) {
                GenreSearchUI(navController = navController)
            }

            // Halaman Profile
            composable(Screen.Profile.route) {
                val profileViewModel: ProfileViewModel = viewModel(factory = profileViewModelFactory)
                ProfileUI(navController = navController, viewModel = profileViewModel)
            }

            // Halaman Parent Profile
            composable(Screen.ParentProfile.route) {
                ParentProfileUI(navController = navController)
            }

            // Halaman Child Profile
            composable(Screen.ChildProfile.route) {
                ChildProfileUI(navController = navController)
            }

            // Halaman Avatar Selection
//            composable(Screen.AvatarSelection.route) {
//                AvatarSelectionUI(navController = navController)
//            }

            // Halaman Leaderboard
            composable(Screen.Leaderboard.route) {
                LeaderboardUI(navController = navController)
            }

            // Halaman Home Baca Cerita
            composable(Screen.HomeBacaCerita.route) {
                HomeBacaCerita(navController = navController)
            }

            // Halaman Baca Cerita
            composable(Screen.BacaCeritaScreen.route) {
                BacaCeritaScreenUI(navController = navController)
            }

            // Halaman Quiz
            composable(Screen.Quiz.route) {
                QuizScreen(navController = navController)
            }

            // Halaman Quiz Result
            composable(Screen.QuizResult.route) {
                QuizResultScreen(navController = navController)
            }

            composable(Screen.ParentActivityScreen.route) {
                ParentActivityScreen(navController = navController)
            }

            composable(Screen.ArticleDetail.route) {
                ArticleDetailScreen(navController = navController)
            }

        }
    }
}

@Composable
fun StoryDetailPagePlaceholder(storyId: Int, navController: NavController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Ini adalah Halaman Detail Cerita", fontSize = 20.sp)
            Text(text = "ID Cerita yang Diklik: $storyId", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.popBackStack() }) {
                Text("Kembali")
            }
        }
    }
}