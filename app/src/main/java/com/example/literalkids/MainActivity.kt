package com.example.literalkids

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.literalkids.navigation.Screen
import com.example.literalkids.ui.AvatarSelectionUI
import com.example.literalkids.ui.ChildProfileUI
import com.example.literalkids.ui.GenreSearchUI
import com.example.literalkids.ui.LeaderboardUI
import com.example.literalkids.ui.LibraryScreen
import com.example.literalkids.ui.LoginUI
import com.example.literalkids.ui.ParentProfileUI
import com.example.literalkids.ui.ProfileUI
import com.example.literalkids.ui.RegisterUI
import com.example.literalkids.ui.SearchUI
import com.example.literalkids.ui.SubscriptionUI

class MainActivity : AppCompatActivity() {
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

    NavHost(
        navController = navController,
        startDestination = Screen.Genre.route
    ) {

        // Halaman Login
        composable(Screen.Login.route) {
            LoginUI(navController = navController)
        }

        // Halaman Register
        composable(Screen.Register.route) {
            RegisterUI(navController = navController)
        }

        // Halaman Subscription
        composable(Screen.Subscription.route) {
            SubscriptionUI(navController = navController)
        }

        composable(Screen.Profile.route) {
            ProfileUI(navController = navController)
        }

        composable(Screen.ParentProfile.route) {
            ParentProfileUI(navController = navController)
        }

        composable(Screen.ChildProfile.route) {
            ChildProfileUI(navController = navController)
        }

        composable(Screen.AvatarSelection.route) {
            AvatarSelectionUI(navController = navController)
        }

        composable(Screen.Leaderboard.route) {
            LeaderboardUI(navController = navController)
        }
        composable(Screen.Perpustakaan.route) {
            LibraryScreen(navController = navController)
        }
        composable(Screen.Search.route) {
            SearchUI(navController = navController)
        }
        composable(Screen.Genre.route) {
            GenreSearchUI(navController = navController)
        }
    }
}