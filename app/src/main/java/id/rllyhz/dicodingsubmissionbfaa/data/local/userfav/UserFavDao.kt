package id.rllyhz.dicodingsubmissionbfaa.data.local.userfav

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserFavDao {

    @Query("SELECT * FROM user_fav_table")
    fun getAllUserFavs(): List<UserFav>

    @Query("SELECT * FROM user_fav_table WHERE username = :username LIMIT 1")
    suspend fun doesUserExist(username: String): UserFav

    @Insert
    suspend fun add(userFav: UserFav)
}