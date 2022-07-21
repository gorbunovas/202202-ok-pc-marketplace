plugins {
    kotlin("jvm")
}

group =rootProject.group
version =rootProject.version

dependencies {
    implementation(project(":pc-marketplace-common"))
    implementation(kotlin("stdlib-common"))
}
