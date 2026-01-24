plugins {
    id("com.android.library")
    //id("com.novoda.bintray-release")
    id("maven-publish")
    signing
}

/*ext {
    val VERSION_NAME = rootProject.ext.burstLinkerVer
}*/

android {
    compileSdk = 36
    //buildToolsVersion '28.0.3'
    namespace = "com.bilibili.burstlinker"
    defaultConfig {
        aarMetadata {
            minCompileSdk = 33
        }
        minSdk = 21
        //versionCode = 13
        //versionName = "0.0.13"
        //testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"

        // renderscriptTargetApi 26
        // renderscriptSupportModeEnabled true
        // renderscriptNdkModeEnabled true

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }

        externalNativeBuild {
            cmake {
//                arguments "-DANDROID_ARM_NEON=TRUE"
//                arguments "-DANDROID_ABI=armeabi-v7a with NEON"
                arguments("-DANDROID_STL=c++_static")
                cppFlags("-std=c++14 -fno-rtti -fno-exceptions")
            }
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        create("debug_native") {
            isJniDebuggable = true
        }
    }
    externalNativeBuild {
        cmake {
            path = File("CMakeLists.txt")
        }
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {
    //implementation fileTree(dir: "libs", include: ["*.jar"])
    //testImplementation("junit:junit:4.12")
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Cryolitia/BurstLinker")
            credentials {
                username = "Cryolitia"
                password = project.findProperty("github.publishPAT").toString()
            }
        }
    }
    publications {
        register<MavenPublication>("release") {
            groupId = "com.bilibili"
            artifactId = "burstlinker"
            version = "0.0.14"
            afterEvaluate {
                from(components["release"])
            }
            pom {
                name.set("BurstLinker")
                description.set("\uD83D\uDE80 A simple GIF encoder for Android.")
                url.set("https://github.com/Cryolitia/BurstLinker")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("succlz123")
                        name.set("Ning")
                    }
                    developer {
                        id.set("Cryolitia")
                        name.set("Cryolitia PukNgae")
                        email.set("Cryolitia@gmail.com")
                    }
                }
            }
        }
    }
}

//apply from: rootProject.file('upload.gradle')
