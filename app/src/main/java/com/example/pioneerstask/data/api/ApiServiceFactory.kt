package com.example.pioneerstask.data.api

import com.example.pioneerstask.utils.AppConstant.Constants.BASE_URL
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit


class ApiServiceFactory {
    companion object {


        fun getInstance(): ApiService {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val client = OkHttpClient.Builder().connectTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES).readTimeout(3, TimeUnit.MINUTES).build()

            return Retrofit.Builder().baseUrl(BASE_URL).client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson)).build()
                .create(ApiService::class.java)
        }


    }



}