plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
//    kotlin("plugin.serialization") version "1.9.22"
    kotlin("plugin.serialization") version "1.5.10"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    //noinspection GradleDependency
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")//1.6.3
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.0")
}
