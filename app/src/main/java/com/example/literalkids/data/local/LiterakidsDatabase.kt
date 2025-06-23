package com.example.literalkids.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.literalkids.data.model.HomeUser

// Deklarasikan semua entity yang Anda miliki di sini. Untuk sekarang baru HomeUser.
@Database(entities = [HomeUser::class], version = 2, exportSchema = false)
abstract class LiterakidsDatabase : RoomDatabase() {

    // Sediakan fungsi abstrak untuk setiap DAO yang Anda buat.
    abstract fun userDao(): UserDao

    companion object {

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE homepage_user_cache ADD COLUMN isSynced INTEGER NOT NULL DEFAULT 1")
            }
        }

        // @Volatile memastikan instance ini selalu up-to-date dan sama untuk semua thread.
        @Volatile
        private var INSTANCE: LiterakidsDatabase? = null

        fun getDatabase(context: Context): LiterakidsDatabase {
            // Mengembalikan instance yang sudah ada, atau membuatnya jika belum ada.
            // Ini disebut pola Singleton, memastikan hanya ada satu koneksi database yang terbuka.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LiterakidsDatabase::class.java,
                    "literakids_database" // Nama file database lokal Anda
                ).addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
