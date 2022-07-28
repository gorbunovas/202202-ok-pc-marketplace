plugins {
    kotlin("jvm") apply false
}

group = "ru.gorbunovas.pcmarketplace"
version = "1.0"

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version
}
