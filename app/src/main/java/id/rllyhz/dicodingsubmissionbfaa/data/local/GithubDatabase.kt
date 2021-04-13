package id.rllyhz.dicodingsubmissionbfaa.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import id.rllyhz.dicodingsubmissionbfaa.data.local.userfav.UserFav
import id.rllyhz.dicodingsubmissionbfaa.data.local.userfav.UserFavDao

@Database(entities = [UserFav::class], version = 1)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun userFavDao(): UserFavDao
}