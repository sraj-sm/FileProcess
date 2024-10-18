plugins {
    kotlin("jvm") version "2.0.20"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.google.cloud:google-cloud-storage:2.35.0")
    implementation("com.facebook.business.sdk:facebook-java-business-sdk:21.0.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("org.example.MainKt")
}