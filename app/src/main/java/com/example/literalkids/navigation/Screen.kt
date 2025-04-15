package com.example.literalkids.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Subscription : Screen("Subscription")
    object Homepage : Screen("homepage")
}