package com.codemobiles.android.cmauthenmvvm.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.navArgument
import androidx.room.Room
import com.codemobiles.android.cmauthenmvvm.model.AppDatabase
import com.codemobiles.android.cmauthenmvvm.model.User
import com.codemobiles.android.cmauthenmvvm.utils.Constants
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel(private val app: Application) : ViewModel() {

    var count = MutableLiveData<Int>(0)
    val navigationCommand = MutableLiveData<NavigationCommand>(NavigationCommand.NoDestination)
    private val db = MutableStateFlow<AppDatabase?>(null)

    fun updateCount() {
        count.value = count.value!! + 1
    }

    private fun getDB(context: Context): AppDatabase {
        if (db.value == null) {
            // the db is saved here
            // /data/data/com.codemobiles.android.cmauthenmvvm/databases
            db.value = Room.databaseBuilder(context, AppDatabase::class.java, "cmdb").build()
        }
        return db.value!!
    }

    suspend fun login(value: User) {
        val result = getDB(app.applicationContext).userDao().query(value.username)
        if (result == null) {
            navigationCommand.value = NavigationCommand.AlertDialogDestination(
                title = "Login",
                content = "Failed - invalid username"
            )
        } else if (result.password != value.password) {
            navigationCommand.value = NavigationCommand.AlertDialogDestination(
                title = "Login",
                content = "Failed - invalid password"
            )
        } else {
            Prefs.putBoolean(Constants.AUTHEN_PASSED, true)
            navigationCommand.value = NavigationCommand.SuccessDestination
        }
    }

    suspend fun register(user: User) {
        val result = getDB(app.applicationContext).userDao().query(user.username)
        if (result == null) {
            getDB(app.applicationContext).userDao().insert(user)
            Log.i("CMDev", "Registration successfully")
//            navigationCommand.value = NavigationCommand.AlertDialogDestination

            navigationCommand.value = NavigationCommand.AlertDialogDestination(
                title = "Registration",
                content = "Successfully"
            )
        } else {
            Log.i("CMDev", "Registration failed")
//            navigationCommand.value = NavigationCommand.AlertDialogDestination
            navigationCommand.value = NavigationCommand.AlertDialogDestination(
                title = "Registration",
                content = "Failed"
            )
        }

    }
}