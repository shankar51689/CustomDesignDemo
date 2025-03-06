import com.android.build.gradle.internal.scope.ProjectInfo.Companion.getBaseName

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.customdesigndemo"
    compileSdk = 35

    /*signingConfigs{
        create("release") {
            if (System.getenv("JITPACK") == null) {
                keyAlias = "customeDesign"
                keyPassword = "12345678"
                storeFile = file("C:/Users/Monu/Desktop/prac/customeDesign.jks")
                storePassword = "12345678"
            }

        }
    }*/

    defaultConfig {
        applicationId = "com.example.customdesigndemo"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
//        signingConfig = signingConfigs.getByName("release")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
//            signingConfig  = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    dataBinding {
        enable = true
    }
/*
    buildFeatures {
        dataBinding = true
    }*/

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(project(":UX4GDeisgn2"))
//    implementation("com.github.shankar51689:CustomDesignDemo:1.0.14")
//    implementation("com.github.shankar51689:CustomDesignDemo:1.0.3")
}