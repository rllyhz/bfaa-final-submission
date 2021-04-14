package id.rllyhz.githubconsumerapp.util

import android.database.Cursor
import android.graphics.drawable.ColorDrawable
import id.rllyhz.githubconsumerapp.data.local.DBGithubContract
import id.rllyhz.githubconsumerapp.data.model.UserFav

fun Int.toColorDrawable(): ColorDrawable = ColorDrawable(this)

fun Cursor.toUserFavs(): List<UserFav> {
    val userFavs = ArrayList<UserFav>()

    while (moveToNext()) {
        val userId = getInt(getColumnIndexOrThrow(DBGithubContract.UserFavColumns.ID_COLUMN))
        val username =
            getString(getColumnIndexOrThrow(DBGithubContract.UserFavColumns.USERNAME_COLUMN))
        val avatarUrl =
            getString(getColumnIndexOrThrow(DBGithubContract.UserFavColumns.AVATAR_URL_COLUMN))

        userFavs.add(
            UserFav(userId, username, avatarUrl)
        )
    }

    return userFavs
}