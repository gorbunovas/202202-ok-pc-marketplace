plugins {
    kotlin("jvm")
}

group =rootProject.group
version =rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":pc-marketplace-api-v1"))
    implementation(project(":pc-marketplace-common"))

    testImplementation(kotlin("test-junit"))
}
