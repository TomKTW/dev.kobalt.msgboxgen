plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow")
}

group = "dev.kobalt"
version = "0000.00.00.00.00.00.000"

repositories {
    mavenCentral()
}

fun kobalt(module: String, version: String) = "dev.kobalt:$module:$version"
fun ktor(module: String, version: String) = "io.ktor:ktor-$module:$version"
fun exposed(module: String, version: String) = "org.jetbrains.exposed:exposed-$module:$version"
fun general(module: String, version: String) = "$module:$version"
fun kotlinx(module: String, version: String) = "org.jetbrains.kotlinx:kotlinx-$module:$version"
fun kotlinw(module: String, version: String) = "org.jetbrains.kotlin-wrappers:kotlin-$module:$version"

fun DependencyHandler.standardLibrary() {
    implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.3")
    implementation(kotlin("stdlib", "1.5.21"))
}

fun DependencyHandler.logger() {
    implementation(general("org.slf4j:slf4j-simple", "1.7.35"))
}

dependencies {
    standardLibrary()
    logger()
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveFileName.set("msgboxgen.jvm.jar")
        mergeServiceFiles()
        manifest {
            attributes("Main-Class" to "dev.kobalt.msgboxgen.jvm.MainKt")
        }
    }
}