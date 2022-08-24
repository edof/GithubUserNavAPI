package com.edo.githubusernavapi

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

data class Result(

    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @field:SerializedName("items")
    val items: List<GitResponse>
)
@Parcelize
@Entity(tableName = "user")
data class GitResponse (
    @ColumnInfo(name = "avatar_url")
    @field:SerializedName("avatar_url")
    val avatarUrl: String,
    @PrimaryKey
    @field:SerializedName("id")
    val id: Int,
    @ColumnInfo(name = "login")
    @field:SerializedName("login")
    val login: String,

) : Parcelable

data class DetailUsers (
    val login: String,
    val avatarUrl: String,
    val name: String,
    val bio : String,
    val followers : Int,
    val following : Int,
)

data class DetailFollowers(
    val login: String,
    val id: Int,
    @field:SerializedName("avatar_url")
    val avatarUrl: String,
) : Serializable

data class DetailFollowing(
    val login: String,
    val id: Int,
    @field:SerializedName("avatar_url")
    val avatarUrl: String,
) : Serializable