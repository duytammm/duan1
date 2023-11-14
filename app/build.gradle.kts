@file:Suppress("DEPRECATION")

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

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

    packagingOptions {
        exclude ("META-INF/**")
    }

    dependencies {

        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("com.google.android.material:material:1.10.0")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

        //Firebase
        implementation(platform("com.google.firebase:firebase-bom:32.5.0"))
        implementation("com.google.firebase:firebase-database")
        implementation("com.google.firebase:firebase-analytics")

        //Thư viện tạo slideshow
        implementation("me.relex:circleindicator:2.1.6")
        implementation("com.github.bumptech.glide:glide:4.16.0")

        //Lưu ảnh trên firebase
        implementation("com.google.firebase:firebase-storage")
        implementation ("com.firebaseui:firebase-ui-database:8.0.2")
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.databinding:databinding-compiler:8.1.3")
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation("com.google.firebase:firebase-auth:22.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.5.0"))
    implementation("com.google.firebase:firebase-analytics")

    //Thư viện tạo slideshow
    implementation ("me.relex:circleindicator:2.1.6")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("me.relex:circleindicator:1.3.2")
    implementation ("me.relex:circleindicator:2.1.6")


}

