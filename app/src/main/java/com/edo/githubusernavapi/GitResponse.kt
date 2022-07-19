package com.edo.githubusernavapi

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Result(

    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @field:SerializedName("items")
    val items: List<GitResponse>
)

data class GitResponse (

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("repos_url")
    val reposUrl: String,

    @field:SerializedName("html_url")
    val htmlUrl: String,

    @field:SerializedName("following_url")
    val followingUrl: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("followers_url")
    val followersUrl: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("gravatar_id")
    val gravatarId: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("node_id")
    val nodeId: String
) : Serializable

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