package id.rllyhz.dicodingsubmissionbfaa.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import id.rllyhz.dicodingsubmissionbfaa.data.local.userfav.UserFav

@Database(entities = [UserFav::class], version = 1)
abstract class GithubDatabase : RoomDatabase()