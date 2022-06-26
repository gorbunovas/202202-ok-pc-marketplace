rootProject.name = "202202-ok-pc-marketplace"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val kotestVersion: String by settings
        val openapiVersion: String by settings
        val bmuschkoVersion: String by settings

        kotlin("jvm") version kotlinVersion apply false
        kotlin("multiplatfrom") version kotlinVersion apply false
        id("io.kotest.multiplatform") version kotestVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("com.bmuschko.docker-java-application") version bmuschkoVersion apply false
        id("com.bmuschko.docker-remote-api") version bmuschkoVersion apply false
        id("org.openapi.generator") version openapiVersion apply false

        // spring
//        val springBootVersion: String by settings
//        val springDependencyVersion: String by settings
//        val springPluginVersion: String by settings
//
//        id("org.springframework.boot") version springBootVersion
//        id("io.spring.dependency-management") version springDependencyVersion
//        kotlin("plugin.spring") version springPluginVersion
    }
}
include("pc-marketplace-common")
include("pc-marketplace-api-v1")
include("pc-marketplace-mappers-v1")
include("pc-marketplace-app-ktor")
include("pc-marketplace-services")
include("pc-marketplace-stubs")
include("pc-marketplace-stubs")
