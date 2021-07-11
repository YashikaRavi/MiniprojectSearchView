package com.example.project
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object Retrofit {
    val API: Albumapi by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Albumapi::class.java)
    }
}