package com.codemobiles.android.cmauthenmvvm.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.codemobiles.android.cmauthenmvvm.model.User
import com.codemobiles.android.cmauthenmvvm.model.Youtube
import com.codemobiles.android.cmauthenmvvm.utils.RetrofitClient1
import com.codemobiles.android.cmauthenmvvm.utils.RetrofitClient2
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CameraViewModel(private val app:Application) : ViewModel() {

    fun upload(uri: Uri) {
        val inputStream = app.contentResolver.openInputStream(uri)
        val byteArray = inputStream!!.readBytes()
        inputStream.close()
        val fileName = getFileName(app, uri)

        // Sent Data to server
        val username = "admin"
        val password = "i love codemobiles"

        val reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), byteArray)
        val body = MultipartBody.Part.createFormData("userfile", fileName, reqFile)  //images
        val bodyUsername = RequestBody.create(MediaType.parse("text/plain"), username)
        val bodyPassword = RequestBody.create(MediaType.parse("text/plain"), password)

        val api = RetrofitClient2.create()
        val call = api.upload(body, bodyUsername, bodyPassword)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Toast.makeText(app.applicationContext, response.body()!!.string(), Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(app.applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

        })

    }



    @SuppressLint("Range")
    private fun getFileName(context: Context, uri: Uri): String {

        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor.use {
                if(cursor!!.moveToFirst()) {
                    return cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            }
        }

        return uri.path!!.substring(uri.path!!.lastIndexOf('/') + 1)
    }
}