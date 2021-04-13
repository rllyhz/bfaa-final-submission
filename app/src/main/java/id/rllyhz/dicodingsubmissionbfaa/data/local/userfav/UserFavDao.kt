package id.rllyhz.dicodingsubmissionbfaa.data.local.userfav

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserFavDao {
    @Query("SELECT * FROM user_fav_table")
    fun getAllUserFavs(): Flow<List<UserFav>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(user: UserFav)
}