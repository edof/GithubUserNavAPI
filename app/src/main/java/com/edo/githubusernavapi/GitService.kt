package com.edo.githubusernavapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitService {

    @GET("search/users")
    fun getGithubUsers(
        @Query("q") query: String
    ): Call<Result>

    @GET("users/{login}")
    fun getDetailGitUsers(
        @Path("login") login: String
    ): Call<DetailUsers>

    @GET("/users/{login}/followers")
    fun getDetailFollowers(
        @Path("login") login: String
    ): Call<List<DetailFollowers>>

    @GET("/users/{login}/following")
    fun getDetailFollowing(
        @Path("login") login: String
    ): Call<List<DetailFollowing>>
}