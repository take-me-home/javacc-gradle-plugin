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
    kotlin("jvm") version "1.3.72"

    // ide plugin
    idea

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

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()

dependencies {
    implementation(gradleKotlinDsl())
    implementation("com.helger:parser-generator-cc:1.1.3")

    testImplementation("commons-io:commons-io:2.8.0")
    testImplementation(gradleTestKit())
}

repositories {
    jcenter()
}
