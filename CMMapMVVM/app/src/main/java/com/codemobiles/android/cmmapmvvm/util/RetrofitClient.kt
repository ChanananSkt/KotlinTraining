package com.codemobiles.android.cmmapmvvm.util

import com.codemobiles.android.cmmapmvvm.model.NoteRequest
import com.codemobiles.android.cmmapmvvm.model.NoteResponse
import com.codemobiles.android.cmmapmvvm.util.Constants.Companion.BASE_URL
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RetrofitClient {

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("record_note")
    fun postNote(@Body data:NoteRequest): Call<NoteResponse>


    @FormUrlEncoded()
    @POST("record_position")
    fun postPosition(@Field("latitude") lat:Double, @Field("longitude") lng:Double):Call<ResponseBody>

    companion object{
        private var mRetrofit: RetrofitClient? = null

        fun create() : RetrofitClient {

            if (mRetrofit == null){
                mRetrofit = Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(RetrofitClient::class.java)
            }

            return mRetrofit!!
        }
    }
}