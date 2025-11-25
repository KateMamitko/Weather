package com.example.weather.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weather.data.local.model.CityDbModel

@Database(entities = [CityDbModel::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun favoriteCityDao(): FavoriteCityDao

//    companion object {
//        const val DB_NAME = "FavoriteDatabase"
//        private var INSTANCE: FavoriteDatabase? = null
//        private val LOCK = Any()
//        fun getInstance(context: Context): FavoriteDatabase {
//            INSTANCE?.let { return it }
//
//            synchronized(LOCK) {
//                INSTANCE?.let { return it }
//
//                val database = Room.databaseBuilder(
//                    context = context,
//                    klass = FavoriteDatabase::class.java,
//                    name = DB_NAME
//                ).build()
//
//                INSTANCE = database
//                return database
//            }
//        }
//    }
}