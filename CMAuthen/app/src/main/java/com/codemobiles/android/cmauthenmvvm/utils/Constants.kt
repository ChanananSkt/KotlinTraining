package com.codemobiles.android.cmauthenmvvm.utils

class Constants {

    companion object {

        const val APP_TAG = "CMDEV"
        const val USER_BEAN = "USER_BEAN"
        const val AUTHEN_PASSED = "AUTHEN_PASSED"


        // SQLite Database
        const val COLUMN_USERNAME = "USERNAME"
        const val COLUMN_PASSWORD = "PASSWORD"
        const val COLUMN_LEVEL = "LEVEL"

        // Feed Server
        const val BASE_URL_API = "https://codemobiles.com"
        const val BASE_URL_UPLOAD = "http://10.0.2.2:3000/"
        const val FEED_TRAININGS = "training"
        const val FEED_FOODS = "foods"
        const val FEED_SUPERHEROS = "superhero"
        const val FEED_SONGS = "songs"

        // Commands
        const val CMD_TOAST = "CMD_TOAST"
        const val CMD_START_ACTIVITY = "CMD_START_ACTIVITY"
    }

}