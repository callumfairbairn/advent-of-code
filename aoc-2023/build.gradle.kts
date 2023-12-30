plugins {
    kotlin("jvm") version "1.8.20"
}

repositories {
    mavenCentral()
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
        test {
            java.srcDirs("test")
        }
    }

    wrapper {
        gradleVersion = "8.0"
    }

    test {
        useJUnitPlatform()
    }
}

dependencies {
    testImplementation(kotlin("test"))
}
