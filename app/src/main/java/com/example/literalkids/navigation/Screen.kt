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
    object Perpustakaan : Screen ("perpustakaan")
    object GenreSelector : Screen("genre/{genre}") {
        fun createRoute(genre: String) = "genre/$genre"
    }

    object StoryDetail : Screen("storyDetail/{storyId}") {
        // Fungsi helper untuk membuat rute dengan ID yang spesifik
        fun createRoute(storyId: Int) = "storyDetail/$storyId"
    }

    object Search : Screen ("search")
    object Quiz : Screen ("book_quiz")
    object HomeBacaCerita : Screen ("home_baca")
    object BacaCeritaScreen : Screen ("baca_cerita_screen")
    object QuizResult : Screen ("quiz_result")
    object OnBoarding1 : Screen("OnBoarding1")
    object ParentActivityScreen : Screen ("parent_activity_screen")
    object ArticleDetail : Screen ("article_detail")
    object ArticleDetail2 : Screen("article_detail_2")



}