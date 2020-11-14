import org.asciidoctor.gradle.jvm.AsciidoctorTask
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/*
 * Copyright 2015 Intershop Communications AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
plugins {
    // project plugins
    `java-gradle-plugin`
    groovy
    id("nebula.kotlin") version "1.3.61"

    // ide plugin
    idea

    // plugin for documentation
    id("org.asciidoctor.jvm.convert") version "3.1.0"

    // documentation
    id("org.jetbrains.dokka") version "0.10.1"

    // code analysis for kotlin
    id("io.gitlab.arturbosch.detekt") version "1.14.2"

    // plugin for publishing to Gradle Portal
    id("com.gradle.plugin-publish") version "0.12.0"
}


group = "net.bitnine.gradle.javacc"
description = "Gradle javacc plugin"
version = "1.0.2"

val pluginId = "net.bitnine.gradle.javacc"

gradlePlugin {
    plugins {
        create("javaccPlugin") {
            id = pluginId
            implementationClass = "com.intershop.gradle.javacc.JavaCCPlugin"
            displayName = project.name
            description = project.description
        }
    }
}

pluginBundle {
    website = "https://github.com/take-me-home/${project.name}"
    vcsUrl = "https://github.com/take-me-home/${project.name}"
    tags = listOf("javacc")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

// set correct project status
if (project.version.toString().endsWith("-SNAPSHOT")) {
    status = "snapshot'"
}

detekt {
    input = files("src/main/kotlin")
    config = files("detekt.yml")
}

tasks {
    withType<Test>().configureEach {
        systemProperty("intershop.gradle.versions", "6.0, 6.1, 6.2, 6.3, 6.4, 6.5")

        dependsOn("jar")
    }

    val copyAsciiDoc = register<Copy>("copyAsciiDoc") {
        includeEmptyDirs = false

        val outputDir = file("$buildDir/tmp/asciidoctorSrc")
        val inputFiles = fileTree(rootDir) {
            include("**/*.asciidoc")
            exclude("build/**")
        }

        inputs.files.plus(inputFiles)
        outputs.dir(outputDir)

        doFirst {
            outputDir.mkdir()
        }

        from(inputFiles)
        into(outputDir)
    }

    withType<AsciidoctorTask> {
        dependsOn("copyAsciiDoc")

        setSourceDir(file("$buildDir/tmp/asciidoctorSrc"))
        sources(delegateClosureOf<PatternSet> {
            include("README.asciidoc")
        })

        outputOptions {
            setBackends(listOf("html5", "docbook"))
        }

        options = mapOf("doctype" to "article",
                "ruby" to "erubis")
        attributes = mapOf(
                "latestRevision" to project.version,
                "toc" to "left",
                "toclevels" to "2",
                "source-highlighter" to "coderay",
                "icons" to "font",
                "setanchors" to "true",
                "idprefix" to "asciidoc",
                "idseparator" to "-",
                "docinfo1" to "true")
    }

    getByName("jar").dependsOn("asciidoctor")

    val compileKotlin by getting(KotlinCompile::class) {
        kotlinOptions.jvmTarget = "1.8"
    }

    val dokka by existing(DokkaTask::class) {
        outputFormat = "javadoc"
        outputDirectory = "$buildDir/javadoc"

        // Java 8 is only version supported both by Oracle/OpenJDK and Dokka itself
        // https://github.com/Kotlin/dokka/issues/294
        enabled = JavaVersion.current().isJava8
    }

    register<Jar>("sourceJar") {
        description = "Creates a JAR that contains the source code."

        from(sourceSets.getByName("main").allSource)
        archiveClassifier.set("sources")
    }

    register<Jar>("javaDoc") {
        dependsOn(dokka)
        from(dokka)
        archiveClassifier.set("javadoc")
    }
}

dependencies {
    implementation(gradleKotlinDsl())
    implementation("com.helger:parser-generator-cc:1.1.3")

    testImplementation("commons-io:commons-io:2.8.0")
    testImplementation(gradleTestKit())
}

repositories {
    jcenter()
}
