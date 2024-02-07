package com.codemobiles.android.cmauthenmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.codemobiles.android.cmauthenmvvm.model.User
import com.codemobiles.android.cmauthenmvvm.model.Youtube
import com.codemobiles.android.cmauthenmvvm.utils.RetrofitClient1
import kotlinx.coroutines.flow.MutableStateFlow

class FeedViewModel(private val app:Application) : ViewModel() {
    val mDataArray = MutableStateFlow<List<Youtube>>(listOf());
    val userBean = MutableStateFlow<User?>(null);
//    val cmd = MutableStateFlow<GeneralCommand?>(null);


    fun feed(type:String){
//        val username = userBean.value!!.username
//        val password = userBean.value!!.password

        val api = RetrofitClient1.create()
        val call = api.getYoutube("admin", "password", type)
        val response = call.execute()
        mDataArray.value = response.body()!!.youtubes

    }
}