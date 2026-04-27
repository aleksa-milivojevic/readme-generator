plugins {
    id("java")
    alias(libs.plugins.kotlin)
    alias(libs.plugins.intellijPlatform)
}

group = "org.example"
version = "1.0.0-SNAPSHOT"

// Set the JVM language level used to build the project.
kotlin {
    jvmToolchain(21)
}

repositories {
    google()
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

// Read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
dependencies {
    intellijPlatform {
        intellijIdea(providers.gradleProperty("platformVersion"))
        testFramework(org.jetbrains.intellij.platform.gradle.TestFrameworkType.Platform)

        // Add plugin dependencies for compilation here:
        bundledPlugin("com.intellij.java")
        bundledPlugin("org.intellij.plugins.markdown")
    }

    implementation("dev.langchain4j:langchain4j:1.13.1")
    implementation("dev.langchain4j:langchain4j-google-ai-gemini:1.13.1")
}


intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = providers.gradleProperty("pluginSinceBuild")
        }

        changeNotes = """
            Initial version
        """.trimIndent()
    }
}

tasks {
    wrapper {
        gradleVersion = providers.gradleProperty("gradleVersion").get()
    }
}
