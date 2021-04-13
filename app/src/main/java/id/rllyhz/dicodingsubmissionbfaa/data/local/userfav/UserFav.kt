package id.rllyhz.dicodingsubmissionbfaa.data.local.userfav

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "user_fav_table")
@Parcelize
data class UserFav(
    @PrimaryKey val userId: Int,
    val username: String,
    val avatar_url: String
) : Parcelable