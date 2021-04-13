package id.rllyhz.dicodingsubmissionbfaa.data.local.userfav

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserFavDao {

    @Query("SELECT * FROM user_fav_table")
    fun getAllUserFavs(): LiveData<List<UserFav>>

    @Query("SELECT * FROM user_fav_table WHERE username = :username LIMIT 1")
    suspend fun doesUserExist(username: String): LiveData<UserFav>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(userFav: UserFav)
}