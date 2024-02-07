# set jre bin path
- windows: C:\Program Files\Android\Android Studio\jre
- mac: /Applications/Android\ Studio.app/Contents/jre/

cd android
keytool -genkey -v -keystore key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias key


# Android (certification)
-----------------

# Add android/key.properties
    storePassword=12341234
    keyPassword=12341234
    keyAlias=key
    storeFile=/........./android/key.jks

#Update android/app/build.gradle
    def keystoreProperties = new Properties()
    def keystorePropertiesFile = rootProject.file('key.properties')
    if (keystorePropertiesFile.exists()) {
        keystoreProperties.load(new FileInputStream(keystorePropertiesFile))
    }

    signingConfigs {
        release {
            keyAlias keystoreProperties['keyAlias']
            keyPassword keystoreProperties['keyPassword']
            storeFile file(keystoreProperties['storeFile'])
            storePassword keystoreProperties['storePassword']
        }
    }

    buildTypes {
        release {
            // TODO: Add your own signing config for the release build.
            // Signing with the debug keys for now, so `flutter run --release` works.
            signingConfig signingConfigs.release
        }
    }

# build
./gradlew assembleRelease
./gradlew bundleRelease
