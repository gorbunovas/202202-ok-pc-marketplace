import org.jetbrains.kotlin.util.suffixIfNot

val ktorVersion: String by project
val logbackVersion: String by project

group =rootProject.group
version =rootProject.version

// ex: Converts to "io.ktor:ktor-ktor-server-netty:2.0.1" with only ktor("netty")
fun DependencyHandler.ktor(module: String, prefix: String = "server-", version: String? = ktorVersion): Any =
    "io.ktor:ktor-${prefix.suffixIfNot("-")}$module:$version"


plugins {
    application
    kotlin("jvm")
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

dependencies {

    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation(ktor("core")) // "io.ktor:ktor-server-core:$ktorVersion"
    implementation(ktor("netty")) // "io.ktor:ktor-ktor-server-netty:$ktorVersion"

    // jackson
    implementation(ktor("jackson", "serialization")) // io.ktor:ktor-serialization-jackson
    implementation(ktor("content-negotiation")) // io.ktor:ktor-server-content-negotiation

    implementation(ktor("locations"))
    implementation(ktor("caching-headers"))
    implementation(ktor("call-logging"))
    implementation(ktor("auto-head-response"))
    implementation(ktor("cors")) // "io.ktor:ktor-cors:$ktorVersion"
    implementation(ktor("default-headers")) // "io.ktor:ktor-cors:$ktorVersion"
    implementation(ktor("auto-head-response"))
    implementation(ktor("websockets"))

    implementation(ktor("websockets")) // "io.ktor:ktor-websockets:$ktorVersion"
    // implementation(ktor("auth")) // "io.ktor:ktor-auth:$ktorVersion"
    // implementation(ktor("auth-jwt")) // "io.ktor:ktor-auth-jwt:$ktorVersion"

    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    // transport models
    implementation(project(":pc-marketplace-common"))
    implementation(project(":pc-marketplace-api-v1"))
    implementation(project(":pc-marketplace-mappers-v1"))

    // Services
    implementation(project(":pc-marketplace-services"))

    // Stubs
    implementation(project(":pc-marketplace-stubs"))

    testImplementation(kotlin("test-junit"))
    testImplementation(ktor("test-host")) // "io.ktor:ktor-server-test-host:$ktorVersion"
}

