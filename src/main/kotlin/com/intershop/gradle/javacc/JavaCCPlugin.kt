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
package com.intershop.gradle.javacc

import com.intershop.gradle.javacc.extension.JavaCCExtension
import com.intershop.gradle.javacc.extension.JavaCCExtension.Companion.JAVACC_EXTENSION_NAME
import com.intershop.gradle.javacc.task.JavaCCTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.api.plugins.JavaPluginConvention


/**
 * Plugin Class implementation.
 */
class JavaCCPlugin : Plugin<Project> {

    companion object {
        /**
         * Task description for JavaCC tasks.
         */
        const val TASKDESCRIPTION = "Generate Java code with JAVA Compiler Compiler (JavaCC)"

        /**
         * Name of the main tasks of all javaCC tasks.
         */
        const val TASKNAME = "javacc"
    }

    override fun apply(project: Project) {
        with(project) {
            logger.info("JavaCC plugin adds extension {} to {}", JAVACC_EXTENSION_NAME, name)
            val extension = extensions.findByType(JavaCCExtension::class.java) ?:
                                        extensions.create(JAVACC_EXTENSION_NAME, JavaCCExtension::class.java)

            configureTask(this, extension)
        }
    }

    /**
     * Configure task for javaCC code generation.
     *
     * @param project       project to configure
     * @param extension     extension of this plugin
     */
    private fun configureTask(project: Project, extension: JavaCCExtension) {
        with(project) {
            val javaCCMain = tasks.maybeCreate(TASKNAME).apply {
                description = TASKDESCRIPTION
                group = JavaCCExtension.JAVACC_GROUP_NAME
            }

            extension.configs.all { javaCC ->
                tasks.maybeCreate(javaCC.getJavaCCTaskName(), JavaCCTask::class.java).apply {
                    group = JavaCCExtension.JAVACC_GROUP_NAME

                    provideOutputDir(javaCC.outputDirProvider)
                    provideInputFile(javaCC.inputFileProvider)

                    providePackageName(javaCC.packageNameProvider)
                    provideJdkVersion(javaCC.jdkVersionProvider)

                    provideLookahead(javaCC.lookaheadProvider)
                    provideChoiceAmbiguityCheck(javaCC.choiceAmbiguityCheckProvider)
                    provideOtherAmbiguityCheck(javaCC.otherAmbiguityCheckProvider)
                    provideStaticParam(javaCC.staticParamProvider)
                    provideSupportClassVisibilityPublic(javaCC.supportClassVisibilityPublicProvider)
                    provideDebugParser(javaCC.debugParserProvider)
                    provideDebugLookahead(javaCC.debugLookaheadProvider)
                    provideDebugTokenManager(javaCC.debugTokenManagerProvider)
                    provideErrorReporting(javaCC.errorReportingProvider)
                    provideJavaUnicodeEscape(javaCC.javaUnicodeEscapeProvider)
                    provideUnicodeInput(javaCC.unicodeInputProvider)
                    provideIgnoreCase(javaCC.ignoreCaseProvider)
                    provideCommonTokenAction(javaCC.commonTokenActionProvider)
                    provideUserTokenManager(javaCC.userTokenManagerProvider)
                    provideUserCharStream(javaCC.userCharStreamProvider)
                    provideBuildParser(javaCC.buildParserProvider)
                    provideBuildTokenManager(javaCC.buildTokenManagerProvider)
                    provideTokenManagerUsesParser(javaCC.tokenManagerUsesParserProvider)
                    provideSanityCheck(javaCC.sanityCheckProvider)
                    provideForceLaCheck(javaCC.forceLaCheckProvider)
                    provideCacheTokens(javaCC.cacheTokensProvider)
                    provideKeepLineColumn(javaCC.keepLineColumnProvider)
                    provideTokenExtends(javaCC.tokenExtendsProvider)
                    provideTokenFactory(javaCC.tokenFactoryProvider)

                    provideJavaCCArgs(javaCC.argsProvider)

                    jjTree = javaCC.jjtree

                    afterEvaluate {
                        plugins.withType(JavaBasePlugin::class.java) {
                            val javaPluginConvention = project.convention.getPlugin(JavaPluginConvention::class.java)
                            javaPluginConvention.sourceSets.matching {
                                it.name == javaCC.sourceSetName
                            }.forEach {
                                it.java.srcDir(this@apply.outputs)
                            }
                        }
                    }

                    javaCCMain.dependsOn(this)
                }
            }
        }
    }
}
