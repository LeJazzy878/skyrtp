plugins {
    kotlin("jvm") version "1.9.20"
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "fr.lejazzy.rtp"
version = "1.0"
description = "RTP pour skymc"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
    maven { url = uri("https://repo.aikar.co/content/groups/aikar/") }

    flatDir {
        dirs("libs")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")

    implementation("net.projecttl:InventoryGUI-api:4.6.1")
    implementation("co.aikar:acf-paper:0.5.1-SNAPSHOT")

}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
        }
    }

    shadowJar {
        archiveFileName.set("SkyRTP.jar")


        manifest {
            attributes["Main-Class"] = "fr.lejazzy.rtp.RTP"
        }
    }

    // Configuration du jar normal
    jar {
        archiveFileName.set("SkyRTP-without-dependencies.jar")
        manifest {
            attributes["Main-Class"] = "fr.lejazzy.rtp.RTP"
        }
    }

    // Make build depend on shadowJar
    build {
        dependsOn(shadowJar)
    }
}