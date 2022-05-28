plugins {
    kotlin("jvm") apply false
}

group = "ru.gorbunovas.pcmarketplace"
version = "1.0"

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version
}
