package id.rllyhz.dicodingsubmissionbfaa.ui.activity.userfav

import id.rllyhz.dicodingsubmissionbfaa.data.local.GithubDatabase
import id.rllyhz.dicodingsubmissionbfaa.data.local.userfav.UserFav
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserFavRepository @Inject constructor(
    private val db: GithubDatabase
) {
    fun getAllUserFavs(): Flow<List<UserFav>> =
        db.userFavDao().getAllUserFavs()
}