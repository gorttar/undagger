import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.9.22"
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    application
}

group = "me.adp"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test", "1.9.22"))
    val daggerVersion = "2.50"
    implementation(group = "com.google.dagger", name = "dagger", version = daggerVersion)
    kapt(group = "com.google.dagger", name = "dagger-compiler", version = daggerVersion)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "${JavaVersion.VERSION_21}"
}

application {
    mainClass.set("MainKt")
}