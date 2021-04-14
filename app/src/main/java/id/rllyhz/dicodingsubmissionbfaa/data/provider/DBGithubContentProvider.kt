package id.rllyhz.dicodingsubmissionbfaa.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import id.rllyhz.dicodingsubmissionbfaa.data.local.GithubDatabase
import id.rllyhz.dicodingsubmissionbfaa.data.local.userfav.UserFavDao
import javax.inject.Inject

class DBGithubContentProvider @Inject constructor(
    private val db: GithubDatabase
) : ContentProvider() {

    private lateinit var userFavDao: UserFavDao

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun onCreate(): Boolean {
        userFavDao = db.userFavDao()
        return false
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        TODO("Implement this to handle query requests from clients.")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int = 0


    companion object {
        private const val AUTHORITY = "id.rllyhz.dicodingsubmissionbfaa"
        private const val EXPORTED_TABLE_NAME = "user_fav_table"
        const val ID_FAVOURITE_USER_DATA = 1
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, EXPORTED_TABLE_NAME, ID_FAVOURITE_USER_DATA)
        }
    }
}