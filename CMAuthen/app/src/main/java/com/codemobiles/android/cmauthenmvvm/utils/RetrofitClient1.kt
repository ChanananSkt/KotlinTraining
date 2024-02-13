package com.codemobiles.android.cmauthenmvvm.utils


import com.codemobiles.android.cmauthenmvvm.model.YoutubeBean
import com.codemobiles.android.cmauthenmvvm.utils.Constants.Companion.BASE_URL_API
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitClient1 {

    @GET("/adhoc/youtubes/index_new.php/")
    fun getYoutube(@Query("username") username:String,
                   @Query("password") password:String,
                   @Query("type") type:String): Call<YoutubeBean>

    companion object createAPIForFeedingJSON{
        fun create(): RetrofitClient1{
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(RetrofitClient1::class.java)
        }
    }


}