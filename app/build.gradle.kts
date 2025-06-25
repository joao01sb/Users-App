import io.gitlab.arturbosch.detekt.Detekt

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") version "2.1.21-2.0.1"
    kotlin("plugin.serialization") version "1.9.0"
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.detekt)
}

kover {
    reports {
        total {
            xml {
                onCheck = true
            }
            html {
                onCheck = true
            }
        }

        verify {
            rule {
                bound {
                    minValue = 50
                }
            }
        }

        filters {
            excludes {
                annotatedBy(
                    "androidx.compose.runtime.Composable",
                    "androidx.compose.ui.tooling.preview.Preview"
                )

                packages(
                    "com.joao01sb.usersapp.ui.theme",
                    "com.joao01sb.usersapp.ui.navigation",
                    "com.joao01sb.usersapp.*.di",
                    "com.joao01sb.usersapp.core.utils",
                    "com.joao01sb.usersapp.core.domain",
                    "com.joao01sb.usersapp.core.data.*",
                )
            }
        }
    }
}
allprojects {
    detekt {
        toolVersion = libs.versions.detekt.get()
        config.setFrom(file("$rootDir/config/detekt/detekt.yml"))
    }

    tasks.withType<Detekt>().configureEach {
        reports {
            xml.required.set(true)
            html.required.set(true)
            txt.required.set(false)
        }
    }
}

android {
    namespace = "com.joao01sb.usersapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.joao01sb.usersapp"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    // Retrofit
    implementation(libs.retrofit)
    // Converter JSON com Gson
    implementation(libs.converter.gson)
    // Suport from Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    // OkHttp
    implementation(libs.okhttp)
    // Core do Koin
    implementation(libs.koin.core)
    // Android
    implementation(libs.koin.android)
    // Koin Jetpack Compose
    implementation(libs.koin.androidx.compose)
    //Lifecycle viewmodel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    //Compose foundation
    implementation(libs.androidx.foundation)
    //Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    //navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    androidTestImplementation("androidx.navigation:navigation-testing:2.9.0")

    //test external libs
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.androidx.core.testing)
    testImplementation(kotlin("test"))

}