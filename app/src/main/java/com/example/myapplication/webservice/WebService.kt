package com.example.myapplication.webservice

import com.example.myapplication.MyApplication

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by duyjack on 2/24/17.
 */

object WebService {
    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var client: OkHttpClient? = null
    private var retrofit: Retrofit? = null
    private var webServiceInterface: WebServiceInterface? = null

    fun getWebServiceInterface(): WebServiceInterface? {
        client = OkHttpClient.Builder()
            .addInterceptor(newDefaltLogging())
            .build()
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client!!)
            .build()
        webServiceInterface = retrofit!!.create(WebServiceInterface::class.java)
        return webServiceInterface
    }

    private fun newDefaltLogging(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        if (MyApplication.isInDebugMode()) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        }
        return logging
    }
}
