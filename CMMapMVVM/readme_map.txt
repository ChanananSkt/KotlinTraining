# Setup library
- At build.gradle.kt (app)
      // Extra libraries
      implementation("com.github.fondesa:kpermissions:3.3.0")
      implementation("com.google.android.gms:play-services-maps:18.2.0")
      implementation("com.google.android.gms:play-services-places:17.0.0")
      implementation("com.google.android.gms:play-services-location:20.0.0")
      implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
      implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
      implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
      implementation("com.squareup.retrofit2:retrofit:2.9.0")
      implementation("com.squareup.retrofit2:converter-gson:2.9.0")

- At build.gradle.kt (top-level)
buildscript {
    repositories {
        google()
    }
    dependencies {

        // to allow pass argument between fragment easier than traditional way without bundle builder
        val nav_version = "2.7.6"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
    }
}


-  AndroidManifest.xml

        <uses-permission android:name="android.permission.INTERNET" />
        <uses-permission android:name="com.google.android.providers.gsf.permission.READ_GSERVICES" />
        <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
        <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
        <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
        <uses-permission android:name=".permission.MAPS_RECEIVE" />

        <permission
            android:name=".permission.MAPS_RECEIVE"
            android:protectionLevel="signature" />

        <uses-feature
            android:glEsVersion="0x00020000"
            android:required="true" />

-------------------------------------------

        <meta-data
            android:name="com.google.android.gms.version"
            android:value="@integer/google_play_services_version" />
        <meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="AIzaSyAvHhquqVwZRd2vtUWzwTayiLsWyjWmK2w" />

        <uses-library
            android:name="org.apache.http.legacy"
            android:required="false" />

