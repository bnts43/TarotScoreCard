package com.bneuts.tarotscorecard.persistence.dataprovider

import com.bneuts.tarotscorecard.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Created by bneut on 11/01/2018.
 */
interface Webservice {
    /**
     * @GET declares an HTTP GET request
     * @Path("user") annotation on the userId parameter marks it as a
     * replacement for the {user} placeholder in the @GET path
     */
    @GET("/users/{user}")
    fun getUser(@Path("user") userId: Int): Call<User>
}