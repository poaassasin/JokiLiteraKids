package com.example.literalkids.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Subscription : Screen("Subscription")
    object Homepage : Screen("homepage")
    object Profile : Screen("profile")
    object ParentProfile : Screen("parent_profile")
    object ChildProfile : Screen("child_profile")
    object AvatarSelection : Screen("avatar_selection")
    object Leaderboard : Screen("leaderboard")
    object Quiz : Screen ("quiz1")
    object Quiz2 : Screen("quiz2")
    object Quiz3 : Screen("quiz3")
    object QuizResult : Screen ("quizresult")

}