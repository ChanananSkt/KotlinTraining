# setting.gradle.kts
   repositories {
        ..
        ..
           maven(url = "https://jitpack.io")
}


# build.gradle.kts (app)

// Chart
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation("com.github.fondesa:kpermissions:3.3.0")