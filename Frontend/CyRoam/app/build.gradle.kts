plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.lg1_1.cyroam"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.lg1_1.cyroam"
        minSdk = 24
        targetSdk = 34
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

task javadoc(type: Javadoc) {

    doFirst {
        configurations.implementation
            .filter { it.name.endsWith('.aar') }
            .each { aar ->
                copy {
                    from zipTree(aar)
                    include "**/classes.jar"
                    into "$buildDir/tmp/aarsToJars/${aar.name.replace('.aar', '')}/"
                }
            }
    }

    configurations.implementation.setCanBeResolved(true)
    source = android.sourceSets.main.java.srcDirs
    classpath += project.files(android.getBootClasspath().join(File.pathSeparator))
    classpath += configurations.implementation
    classpath += fileTree(dir: "$buildDir/tmp/aarsToJars/")

    android.applicationVariants.all { variant ->
        if (variant.name == 'release') {
            owner.classpath += variant.javaCompileProvider.get().classpath
        }
    }

    destinationDir = file("${project.buildDir}/outputs/javadoc/")
    options.memberLevel = JavadocMemberLevel.PRIVATE
    failOnError false
    exclude '**/BuildConfig.java'
    exclude '**/R.java'
}


dependencies {
    implementation("org.java-websocket:Java-WebSocket:1.5.1")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation ("com.google.android.gms:play-services-maps:18.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.android.volley:volley:1.2.1")
    implementation("com.google.android.gms:play-services-location:21.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

dependencies {
    implementation("org.java-websocket:Java-WebSocket:1.5.1")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation ("com.google.android.gms:play-services-maps:18.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.android.volley:volley:1.2.1")
    implementation("com.google.android.gms:play-services-location:21.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}