/*
 * Copyright 2018 Intershop Communications AG.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:Suppress("UnstableApiUsage")
package com.intershop.gradle.javacc.extension

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import javax.inject.Inject

/**
 * JavaCC main extension of this plugin.
 */
@Suppress("UnstableApiUsage")
abstract class JavaCCExtension {

    companion object {
        /**
         * Extension name of plugin.
         */
        const val JAVACC_EXTENSION_NAME = "javacc"

        /**
         * Task group name of jaxb code generation.
         */
        const val JAVACC_GROUP_NAME = "JAVACC Code Generation"

        /**
         * Dependency configuration name for JavaCC code generation.
         */
        const val JAVACC_CONFIGURATION_NAME = "javacc"

        /**
         * Default output path of all generation tasks.
         **/
        const val CODEGEN_OUTPUTPATH = "generated/javacc"
    }

    /**
     * Inject service of ObjectFactory (See "Service injection" in Gradle documentation.
     */
    @get:Inject
    abstract val objectFactory: ObjectFactory

    /**
     * Config container for JavaCC code generation.
     */
    val configs: NamedDomainObjectContainer<JavaCC> = objectFactory.domainObjectContainer(JavaCC::class.java)
}
