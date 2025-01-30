plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id ("maven-publish")
}

android {
    namespace = "com.example.ux4gdesign"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}


publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "com.example.mylibrary"
            artifactId = "mylibrary"
            version = "release"

            // Add artifacts
//            artifact(tasks.getByName("sourcesJar"))
            artifact("${buildDir}/outputs/aar/mylibrary-release.aar")
            /*artifact("$buildDir/outputs/aar/mylibrary-release.aar") {
                builtBy (tasks.named("assembleRelease"))
            }*/
            // Configure POM details
            /*pom {
                name.set("My Library")
                description.set("A sample Android library.")
                url.set("https://github.com/shankar51689/testProj")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
            }*/
            /*jar {
                archiveBaseName.set("my-library")  // Name of the jar file
                archiveVersion.set("1.0.0")        // Version of the jar file
                from( sourceSets.main.output )       // Specify which files should be included in the jar
            }*/
        }
    }

    repositories {
        /*maven {
//            name = "myMavenRepo"
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = "monu40128@gmail.com"
                password = "Monu@5168900"
            }
        }*/

        /*maven { // https://repo1.maven.org/maven2/
            url = uri("https://trial8y03rm.jfrog.io/artifactory/api/maven/libs-release-local") // Replace with your repo key
            credentials {
//                username = (project.findProperty("artifactory_user") ?: System.getenv("ARTIFACTORY_USER")).toString()
//                password = (project.findProperty("artifactory_password") ?: System.getenv("ARTIFACTORY_PASSWORD")).toString()
                username = ""
                password = ""
            }
        }*/
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}