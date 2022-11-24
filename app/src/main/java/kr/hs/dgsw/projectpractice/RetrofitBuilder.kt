package kr.hs.dgsw.projectpractice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitBuilder {
    var api: API
    private var serverIP: String = "10.80.162.223:8080/"

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://" + serverIP)
            .addConverterFactory(ScalarsConverterFactory.create() )
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(API::class.java)
    }
}