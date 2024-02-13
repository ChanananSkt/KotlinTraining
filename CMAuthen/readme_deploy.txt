# set jre bin path
- windows: C:\Program Files\Android\Android Studio\jre
- mac: /Applications/Android\ Studio.app/Contents/jre/

cd android
keytool -genkey -v -keystore key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias key


# Android (certification)
-----------------

# Add key.properties (at root folder)
    storePassword=12341234
    keyPassword=12341234
    keyAlias=key
    storeFile=/........./android/key.jks

#Update android/app/build.gradle.kt
import java.io.FileInputStream
import java.util.Properties

    val keystoreProperties = Properties()
    val keystorePropertiesFile = rootProject.file("key.properties")
    if (keystorePropertiesFile.exists()) {
        keystoreProperties.load(FileInputStream(keystorePropertiesFile))
    }

  signingConfigs {
          create("release") {
              keyAlias = keystoreProperties["keyAlias"] as String
              keyPassword = keystoreProperties["keyPassword"] as String
              storeFile = file(keystoreProperties["storeFile"] as String)
              storePassword = keystoreProperties["storePassword"] as String
          }
  }

  buildTypes {
          release {
              signingConfig = signingConfigs.getByName("release")
              isMinifyEnabled = false
              proguardFiles(
                  getDefaultProguardFile("proguard-android-optimize.txt"),
                  "proguard-rules.pro"
              )
          }
}

# output
app/buid/outputs/apk/release
app/buid/outputs/bundle/release

# build
./gradlew assembleRelease => apk
./gradlew bundleRelease => aab
