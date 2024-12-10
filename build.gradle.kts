import kotlinx.benchmark.gradle.JvmBenchmarkTarget
import org.jetbrains.kotlin.allopen.gradle.AllOpenExtension

plugins {
    kotlin("jvm") version "2.0.21"
    kotlin("plugin.allopen") version "2.0.21"
    id("org.jetbrains.kotlinx.benchmark") version "0.4.12"
    `maven-publish`
}

group = "com.origincoding"
version = "1.0.0"

repositories {
    // Kotlinx Benchmark's runtime cannot be found in Aliyun's Maven repository.
    mavenCentral()
    maven { url = uri("https://maven.aliyun.com/repository/public") }
    maven { url = uri("https://maven.aliyun.com/repository/gradle-plugin") }
    maven { url = uri("https://maven.aliyun.com/repository/apache-snapshots") }
    mavenLocal()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
    // Kotlinx Benchmark's runtime dependency.
    implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:0.4.12")

    // Apache Commons Text for generating password.
    implementation("org.apache.commons:commons-text:1.12.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
    sourceSets {
        dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:0.4.12")
        }
    }
}

sourceSets.configureEach {
    java.setSrcDirs(listOf("$name/src"))
    resources.setSrcDirs(listOf("$name/resources"))
}

configure<AllOpenExtension> {
    annotation("org.openjdk.jmh.annotations.State")
}

benchmark {
    configurations {
        named("main") {
            iterationTime = 5
            iterationTimeUnit = "sec"
        }
    }
    targets {
        register("main") {
            this as JvmBenchmarkTarget
            jmhVersion = "1.21"
        }
    }
}

// 附带源码Jar包，如果需要发布Java Doc Jar包，在代码块里添加：withJavadocJar()
java { withSourcesJar() }

// 这里发布到Maven Local，因为没有Maven账号
publishing {
    publications {
        create<MavenPublication>("bootJava") { from(components["java"]) }
    }
}
