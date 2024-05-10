package com.example.sprint3lab.data.retrofit

import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.HostnameVerifier

class RetrofitClient {
    companion object {
        const val URL = "https://api.themoviedb.org/3/movie/"
        const val SHA256_THEMOVIEDB = "sha256/5VLcahb6x4EvvFrCF2TePZulWqrLHS2jCg9Ywv6JHog="
    }

    val retrofit: Retrofit

    init {
        val hppClient: OkHttpClient.Builder = OkHttpClient.Builder()

        val certificatePinner = CertificatePinner.Builder()
            .add("api.themoviedb.org", SHA256_THEMOVIEDB)
            .build()
        hppClient.certificatePinner(certificatePinner)

        val hostnamesAllow = listOf(
            "api.themoviedb.org"
        )

        val hostnameVerifier = HostnameVerifier { hostname, _ ->
            hostname in hostnamesAllow
        }
        hppClient.hostnameVerifier(hostnameVerifier)

        retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .client(hppClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService(): MoviesApiService {
        return retrofit.create(MoviesApiService::class.java)
    }
}