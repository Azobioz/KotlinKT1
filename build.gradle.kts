import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktor_version = "2.3.12"

plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
    application
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.example.ApplicationKt"
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}

application {
    mainClass.set("com.example.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("ch.qos.logback:logback-classic:1.5.6")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "21"
}