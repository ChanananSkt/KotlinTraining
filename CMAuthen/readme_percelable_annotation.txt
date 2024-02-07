# Parcelize is library that help you don't have to generate paracelable implementation methods in the
data class implemented the Pacelable interface.

# Install at build.gradle.kt
plugins {
...
...
     id("kotlin-parcelize")
}

# Usage case
@Parcelize
data class User(
    val id: Int = 0,
    val username: String,
    val password: String,
    val level: Int? = 0,
): Parcelable
