https://stackoverflow.com/questions/75128041/this-version-compose-compiler-requires-kotlin-version-1-7-20-but-you-appear-to-b

# automate import core lib by adding compose activity
 - Right-click at the desired package and "new/Compose/Empty Activity" select new Compose Empty Activity

# update compileSdk
 - set the compileSdk to 33 at least

# update gradle version in gradle-wrapper.properties
 - distributionUrl=https\://services.gradle.org/distributions/gradle-8.0-bin.zip

# Update jdk to 17
 - search gradle build in project setting and select Gradle JDK to 17 (bundle jbr)

# Update Kotlin version
- composeOptions {
          kotlinCompilerExtensionVersion '1.4.7'
}

# update namespace in app/build.gradle
- namespace 'com.codemobiles.android.cmauthenmvvm'