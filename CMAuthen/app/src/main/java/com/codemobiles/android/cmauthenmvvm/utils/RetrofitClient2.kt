package com.codemobiles.android.cmauthenmvvm.utils


import com.codemobiles.android.cmauthenmvvm.model.YoutubeBean
import com.codemobiles.android.cmauthenmvvm.utils.Constants.Companion.BASE_URL_API
import com.codemobiles.android.cmauthenmvvm.utils.Constants.Companion.BASE_URL_UPLOAD
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface RetrofitClient2 {

    @Multipart
    @POST("/uploads")
    fun upload(
        @Part image: MultipartBody.Part,
        @Part("username") username: RequestBody,
        @Part("password") password: RequestBody
    ):Call<ResponseBody>

    companion object {
        fun create(): RetrofitClient2{
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_UPLOAD)
                .build()
            return retrofit.create(RetrofitClient2::class.java)
        }
    }


}