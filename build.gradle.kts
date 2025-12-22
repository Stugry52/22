plugins {
    kotlin("jvm") version "2.2.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    // dependencies - блок зависимостей (подключение библиотек)
    // implementation - команда градлу - добавить данную библиотеку в проект для использования
    // org.jetbrains.kotlinx... - это внутренняя библиотека IDE jb где org -> jetbrains -> kotlinx это вложенные packages

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}