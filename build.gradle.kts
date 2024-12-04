plugins {
    kotlin("jvm") version "2.0.21"
}

group = "com.origincoding"
version = "1.0-SNAPSHOT"

repositories {
    maven { url = uri("https://maven.aliyun.com/repository/public") }
    maven { url = uri("https://maven.aliyun.com/repository/gradle-plugin") }
    maven { url = uri("https://maven.aliyun.com/repository/apache-snapshots") }
    mavenCentral()
    mavenLocal()
}

dependencies {
    testImplementation(kotlin("test"))

    // Apache Commons Text for generating password.
    implementation("org.apache.commons:commons-text:1.12.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}