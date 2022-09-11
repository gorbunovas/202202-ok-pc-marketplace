plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version


dependencies {
    val coroutinesVersion: kotlin.String by project

    implementation(kotlin("stdlib"))
    implementation(project(":pc-marketplace-common"))
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    api(kotlin("test-junit"))
}