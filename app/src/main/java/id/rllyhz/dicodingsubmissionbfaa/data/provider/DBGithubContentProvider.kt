package id.rllyhz.dicodingsubmissionbfaa.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import id.rllyhz.dicodingsubmissionbfaa.data.local.GithubDatabase
import id.rllyhz.dicodingsubmissionbfaa.data.local.userfav.UserFavDao

class DBGithubContentProvider : ContentProvider() {

    private lateinit var userFavDao: UserFavDao

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int =
        -1 // no implementation

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int = -1

    override fun onCreate(): Boolean {
        // Hilt doesn't directly support content provider
        // but this is an alternative way given by google
        val appContext = context?.applicationContext ?: throw IllegalStateException()
        val hiltEntryPoint =
            EntryPointAccessors.fromApplication(appContext, ContentProviderEntryPoint::class.java)

        val db = hiltEntryPoint.getDB()
        userFavDao = db.userFavDao()
        return false
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? =
        when (uriMatcher.match(uri)) {
            ID_FAVOURITE_USER_DATA -> {
                val cursor: Cursor = userFavDao.getAllUserFavsInCursor()
                context?.let {
                    cursor.setNotificationUri(it.contentResolver, uri)
                }
                cursor
            }
            else -> {
                null
            }
        }


    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface ContentProviderEntryPoint {
        fun getDB(): GithubDatabase
    }


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