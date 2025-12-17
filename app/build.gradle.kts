
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.yarasa.netflux"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.yarasa.retrofitcompose"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        // Hilt kullanıldığı için test koşucusunu Hilt'e özel olanla değiştir
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // Eğer Hilt testleri kullanacaksan, bunu da eklemelisin:
        // testInstrumentationRunner = "com.google.dagger.hilt.android.testing.HiltTestRunner"
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
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Lifecycle (Zaten ktx versiyonları var, livedata ve viewmodel eklendi)
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1") // Tekrar olmasına rağmen bıraktım

    // Icons
    implementation("androidx.compose.material:material-icons-extended:1.5.4")

    // Navigation
    val nav_version = "2.9.6"
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // Gson
    implementation("com.google.code.gson:gson:2.13.2")


    val room_version = "2.8.4"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    implementation("androidx.room:room-rxjava3:$room_version")
    implementation("androidx.room:room-guava:$room_version")
    implementation("androidx.room:room-paging:$room_version")


    ksp("androidx.room:room-compiler:$room_version")


    testImplementation("androidx.room:room-testing:$room_version")


    val hilt_version = "2.57.2"
    implementation("com.google.dagger:hilt-android:$hilt_version")
    ksp("com.google.dagger:hilt-compiler:$hilt_version")


}