package com.example.literalkids.data.repo

import com.example.literalkids.data.model.Story
import com.example.literalkids.data.model.User
import com.example.literalkids.R

class StoryRepository {
    fun getStories(): List<Story> {
        return listOf(
            Story(1, "Si Kancil dan Buaya", R.drawable.kancil_buaya, 0.8f, "Fabel", "4,2 Ribu Dibaca"),
            Story(2, "Buaya dan Kerbau", R.drawable.buaya_kerbau, 0.6f, "Fabel", "2,1 Ribu Dibaca"),
            Story(3, "Kancil Mencuri Ketimun", R.drawable.kancil_ketimun, 0.0f, "Komedi", "4,2 Ribu Dibaca"),
            Story(4, "Asal Usul Danau Toba", R.drawable.danau_toba, 0.0f, "Legenda", "2,1 Ribu Dibaca"),
            Story(5, "Timun Mas dan Buto Ijo", R.drawable.timun_mas, 0.0f, "Mitos", "1,8 Ribu Dibaca"),
            Story(6, "Petualangan Lutung", R.drawable.lutung, 0.0f, "Fantasi", "6,8 Ribu Dibaca"),
            Story(7, "Si Burung Pipit Penolong", R.drawable.pipit, 0.0f, "Fabel", "9,2 Ribu Dibaca"),
            Story(8, "Putri Bambu Gunung Arjuna", R.drawable.putri_bambu, 0.0f, "Legenda", "8,9 Ribu Dibaca")
        )
    }

    fun getUser(): User {
        return User("Levi Annora", "@leviannora", 7, 0.68f)
    }

    fun getBannerImages(): List<Int> {
        return listOf(
            R.drawable.banner_kancil,
            R.drawable.banner_istana,
            R.drawable.banner_pohon
        )
    }
}