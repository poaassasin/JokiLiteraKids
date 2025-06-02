package com.example.literalkids

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.literalkids.navigation.Screen
import com.example.literalkids.ui.AvatarSelectionUI
import com.example.literalkids.ui.BacaCeritaScreenUI
import com.example.literalkids.ui.ChildProfileUI
import com.example.literalkids.ui.GenreSearchUI
import com.example.literalkids.ui.HomeBacaCerita
import com.example.literalkids.ui.LeaderboardUI
import com.example.literalkids.ui.LibraryScreen
import com.example.literalkids.ui.LoginUI
import com.example.literalkids.ui.ParentProfileUI
import com.example.literalkids.ui.ProfileUI
import com.example.literalkids.ui.QuizResultScreen
import com.example.literalkids.ui.QuizScreen
import com.example.literalkids.ui.RegisterUI
import com.example.literalkids.ui.SearchUI
import com.example.literalkids.ui.SubscriptionUI
import com.example.literalkids.ui.HomepageUI
import com.example.literalkids.ui.OnboardingScreen
import com.example.literalkids.ui.theme.LiteralkidsTheme
import com.example.literalkids.viewmodel.ProfileViewModel

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
    val profileViewModel: ProfileViewModel = viewModel()
    val uiState by profileViewModel.uiState.collectAsState()

    LiteralkidsTheme(darkTheme = uiState.isDarkTheme) {
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route
        ) {
            // Halaman Login
            composable(Screen.Login.route) {
                LoginUI(navController = navController)
            }

            // Halaman Register
            composable(Screen.Register.route) {
                RegisterUI(navController = navController)
            }

            // Halaman OnBoarding1
            composable(Screen.OnBoarding1.route) {
                OnboardingScreen(navController = navController)
            }


            // Halaman Homepage
            composable(Screen.Homepage.route) {
                HomepageUI(navController = navController)
            }

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
                ProfileUI(navController = navController)
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
            composable(Screen.AvatarSelection.route) {
                AvatarSelectionUI(navController = navController)
            }

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
        }
    }
}