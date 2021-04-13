package id.rllyhz.dicodingsubmissionbfaa.data.local.userfav

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_fav_table")
data class UserFav(
    val username: String,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String,
    @PrimaryKey val userId: Int = 0
)