plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    val cache4kVersion: String by project
    val coroutinesVersion: String by project
    val kmpUUIDVersion: String by project

    implementation(kotlin("stdlib"))
    implementation("io.github.reactivecircus.cache4k:cache4k:$cache4kVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("com.benasher44:uuid:$kmpUUIDVersion")
    implementation(project(":pc-marketplace-common"))

    testImplementation(kotlin("test-common"))
    testImplementation(kotlin("test-annotations-common"))
    testImplementation(kotlin("test-junit"))
    testImplementation(project(":pc-marketplace-repo-test"))

}