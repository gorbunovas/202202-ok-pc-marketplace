plugins {
    kotlin("jvm")
}



dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":pc-marketplace-api-v1"))
    implementation(project(":pc-marketplace-common"))

    testImplementation(kotlin("test-junit"))
}
