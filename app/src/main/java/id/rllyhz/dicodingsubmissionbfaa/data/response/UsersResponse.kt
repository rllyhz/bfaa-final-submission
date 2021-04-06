package id.rllyhz.dicodingsubmissionbfaa.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UsersResponse(
        val id: Long,
        @SerializedName("avatar_url") @Expose val avatarUrl: String,
        @SerializedName("login") @Expose val username: String
)