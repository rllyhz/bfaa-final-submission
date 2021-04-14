package id.rllyhz.dicodingsubmissionbfaa.data.local.userfav

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserFavDao {
    @Query("SELECT * FROM user_fav_table")
    fun getAllUserFavs(): Flow<List<UserFav>>

    @Query("SELECT count(*) FROM user_fav_table WHERE username = :username")
    suspend fun doesUserExist(username: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(user: UserFav)

    @Query("DELETE FROM user_fav_table WHERE userId = :userId")
    suspend fun remove(userId: Int)

    @Query("SELECT * FROM user_fav_table")
    fun getAllUserFavsInCursor(): Cursor
}