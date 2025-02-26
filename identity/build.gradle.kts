import org.jetbrains.kotlin.konan.target.HostManager

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.ksp)
    id("maven-publish")
}

val projectVersionCode: Int by rootProject.extra
val projectVersionName: String by rootProject.extra

kotlin {
    jvmToolchain(17)

    jvm()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        val platform = when (it.name) {
            "iosX64" -> "iphonesimulator"
            "iosArm64" -> "iphoneos"
            "iosSimulatorArm64" -> "iphonesimulator"
            else -> error("Unsupported target ${it.name}")
        }
//        if (HostManager.hostIsMac) {
//            it.compilations.getByName("main") {
//                val SwiftBridge by cinterops.creating {
//                    definitionFile.set(project.file("nativeInterop/cinterop/SwiftBridge-$platform.def"))
//                    includeDirs.headerFilterOnly("$rootDir/identity/SwiftBridge/build/Release-$platform/include")
//
//                    val interopTask = tasks[interopProcessingTaskName]
//                    interopTask.dependsOn(":identity:SwiftBridge:build${platform.capitalize()}")
//                }
//
//                it.binaries.all {
//                    // Linker options required to link to the library.
//                    linkerOpts(
//                        "-L/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/lib/swift/${platform}/",
//                        "-L$rootDir/identity/SwiftBridge/build/Release-${platform}/",
//                        "-lSwiftBridge"
//                    )
//                }
//            }
//        }
    }

    sourceSets {
        val commonMain by getting {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
            dependencies {
                implementation(projects.processorAnnotations)
                implementation(libs.kotlinx.io.bytestring)
                implementation(libs.kotlinx.io.core)
                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.json)
            }
        }

        val commonTest by getting {
            kotlin.srcDir("build/generated/ksp/metadata/commonTest/kotlin")
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.kotlinx.coroutine.test)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libs.bouncy.castle.bcprov)
                implementation(libs.bouncy.castle.bcpkix)
                implementation(libs.tink)
            }
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", project(":processor"))
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

tasks["compileKotlinIosX64"].dependsOn("kspCommonMainKotlinMetadata")
tasks["compileKotlinIosArm64"].dependsOn("kspCommonMainKotlinMetadata")
tasks["compileKotlinIosSimulatorArm64"].dependsOn("kspCommonMainKotlinMetadata")

tasks["iosX64SourcesJar"].dependsOn("kspCommonMainKotlinMetadata")
tasks["iosArm64SourcesJar"].dependsOn("kspCommonMainKotlinMetadata")
tasks["iosSimulatorArm64SourcesJar"].dependsOn("kspCommonMainKotlinMetadata")
tasks["jvmSourcesJar"].dependsOn("kspCommonMainKotlinMetadata")
tasks["sourcesJar"].dependsOn("kspCommonMainKotlinMetadata")

group = "com.android.identity"
version = projectVersionName

publishing {
    repositories {
        maven {
            url = uri("${rootProject.rootDir}/repo")
        }
    }
    publications.withType(MavenPublication::class) {
        pom {
            licenses {
                license {
                    name = "Apache 2.0"
                    url = "https://opensource.org/licenses/Apache-2.0"
                }
            }
        }
    }
}
