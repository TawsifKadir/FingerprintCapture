plugins {
    id("com.android.application")
    ///id("com.android.library")
}

android {
    namespace = "com.faisal.fingerprintcapture"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.faisal.fingerprintcapture"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.1"
        ///version = "1.3"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("debug")

    }

    buildFeatures.aidl=true

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(files("libs/FDxSDKProFDAndroid.jar","libs/MorphoSmart_SDK_6.42.0.0.jar","libs/SourceAFIS-debug.aar"))

    implementation("com.google.code.gson:gson:2.8.6")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.10.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-cbor:2.10.2")

    // v8.* and above cannot compile in Android. https://github.com/vigna/fastutil/issues/136
    implementation("it.unimi.dsi:fastutil:7.2.1")

    implementation("com.machinezoo.noexception:noexception:1.4.4")
    implementation("commons-io:commons-io:2.6")

    implementation("net.sourceforge.streamsupport:streamsupport:1.6.1")


    /// implementation ("androidx.appcompat:appcompat:1.2.0")
   /// implementation ("com.google.android.material:material:1.2.1")
    ///implementation ("com.github.LintraMax:SourceAFIS-Android:v3.10.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

}