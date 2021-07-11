package com.example.project
import retrofit2.Response
import retrofit2.http.GET
interface Albumapi {
    @GET( value = "/albums")
    suspend fun getTodos(): Response<ArrayList<Albumdataformat>>
}