plugins {
    kotlin("jvm")
}

group =rootProject.group
version =rootProject.version

dependencies {
    implementation(project(":pc-marketplace-common"))
    implementation(project(":pc-marketplace-stubs"))
    implementation(project(":pc-marketplace-biz"))
    implementation(kotlin("stdlib-common"))
}


//for multiplatform
//kotlin {
//    jvm {}
//    macosX64 {}
//    linuxX64 {}
//
//    sourceSets {
//        val commonMain by getting {
//            dependencies {
//                implementation(kotlin("stdlib-common"))
//
//                // transport models
//                implementation(project(":pc-marketplace-common"))
//                implementation(project(":pc-marketplace-stubs"))
//            }
//        }
//        val commonTest by getting {
//            dependencies {
//                implementation(kotlin("test-common"))
//                implementation(kotlin("test-annotations-common"))
//            }
//        }
//        val jvmMain by getting {
//            dependencies {
//                implementation(kotlin("stdlib-jdk8"))
//            }
//        }
//        val jvmTest by getting {
//            dependencies {
//                implementation(kotlin("test-junit"))
//            }
//        }
//    }
//}
