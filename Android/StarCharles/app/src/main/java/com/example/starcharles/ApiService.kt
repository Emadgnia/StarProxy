package com.example.starcharles

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


interface ApiService {

    @GET("films/")
    suspend fun fetchFilms(): Films

    @GET("people/")
    suspend fun fetchCharacters(): Characters

    @GET("people/{id}/")
    suspend fun fetchCharactersFromUrl(@Path("id") id: String): Character

    @GET("films/{id}/")
    suspend fun fetchFilmsFromUrl(@Path("id") id: String): Film

    companion object {

        fun create(): ApiService {
            // Create a custom trust manager that bypasses SSL validation
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            })

            // Create an SSL context that uses the custom trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())

            // Create an OkHttpClient.Builder that uses the custom SSL context
            val okHttpClientBuilder = OkHttpClient.Builder()
                .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier(HostnameVerifier { _, _ -> true })

            val retrofit = Retrofit.Builder()
                .client(okHttpClientBuilder.build())
                .baseUrl("https://swapi.dev/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}
