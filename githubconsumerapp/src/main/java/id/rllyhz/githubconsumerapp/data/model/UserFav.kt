package id.rllyhz.githubconsumerapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserFav(
    val userId: Int,
    val username: String,
    val avatarUrl: String
) : Parcelable