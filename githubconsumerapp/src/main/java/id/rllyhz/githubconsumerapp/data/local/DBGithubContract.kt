package id.rllyhz.githubconsumerapp.data.local

import android.net.Uri
import android.provider.BaseColumns

object DBGithubContract {
    private const val AUTHORITY_TARGET = "id.rllyhz.dicodingsubmissionbfaa"
    private const val SCHEME = "content"

    internal class UserFavColumns : BaseColumns {

        companion object {
            const val TABLE_NAME = "user_fav_table"
            const val ID_COLUMN = "userId"
            const val USERNAME_COLUMN = "username"
            const val AVATAR_URL_COLUMN = "avatar_url"

            val CONTENT_URI = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY_TARGET)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}