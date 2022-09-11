plugins {
    kotlin("jvm")
}

group =rootProject.group
version =rootProject.version

repositories {
    mavenCentral()
}

val coroutinesVersion: String by project
val kotlinCorVersion: String by project

dependencies {
    implementation(kotlin("stdlib"))

    implementation(project(":pc-marketplace-common"))
    implementation(project(":pc-marketplace-stubs"))
    implementation("com.github.crowdproj.kotlin-cor:kotlin-cor:$kotlinCorVersion")

    implementation(kotlin("test-common"))
    implementation(kotlin("test-annotations-common"))
    testImplementation(kotlin("test-junit"))
    implementation(project(":pc-marketplace-inmemory"))
    testApi("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
}
