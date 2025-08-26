import org.gradle.kotlin.dsl.implementation
import java.text.SimpleDateFormat
import java.util.Date

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.0.21"
}

android {
    namespace = "fr.dammerey.seichampsvb"
    compileSdk = 36

    defaultConfig {
        applicationId = "fr.dammerey.seichampsvb"
        minSdk = 26
        targetSdk = 35
        versionCode = 2
        versionName = "2.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    applicationVariants.all {
        outputs.all {
            val appName = "SeichampsVB"
            val versionName = defaultConfig.versionName
            val versionCode = defaultConfig.versionCode
            val buildType = buildType.name
            val date = SimpleDateFormat("yyyyMMdd").format(Date())

            val newName = "${appName}_${versionName}_${date}.apk"
            (this as com.android.build.gradle.internal.api.BaseVariantOutputImpl).outputFileName = newName
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation (libs.material3)
    implementation (libs.androidx.material3.window.size.class1)
    implementation (libs.androidx.ui.text.google.fonts)
    implementation ("com.google.accompanist:accompanist-navigation-animation:0.31.3-beta")
    //navigation et serialisation
 val nav_version ="2.7.7"
    //val nav_version ="2.9.0"
    // Jetpack Compose integration
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // Views/Fragments integration
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")

    // Feature module support for Fragments
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")

    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")

    // JSON serialization library, works with the Kotlin serialization plugin
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

    //implementation("androidx.navigation:navigation-compose-serialization:$nav_version")
    implementation("androidx.navigation:navigation-runtime-ktx:$nav_version")
    // Implementation de WebView pour afficher des pages web
    implementation("androidx.webkit:webkit:1.14.0")
    //SCRAPPING des données
    implementation("org.jsoup:jsoup:1.17.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.1")
    implementation("androidx.compose.runtime:runtime-livedata:1.8.3") // si tu utilises LiveData

    implementation("com.google.code.gson:gson:2.11.0")
}