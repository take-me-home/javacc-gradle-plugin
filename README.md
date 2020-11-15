# JavaCC Gradle Plugin

Table of Contents

- [Notice](#notice)
- [Summary](#summary)
- [Changelog](#changelog)
- [Usage](#usage)
- [License](#license)



- [Tasks](docs/en/tasks.md)
- [Project Extension](docs/en/project-extension.md)

# Notice

***This project forked from <https://github.com/IntershopCommunicationsAG/javacc-gradle-plugin>.***  

This project use ParserGeneratorCC instead of JavaCC.  



# Summary

This plugin generates Java code from JavaCC and JJTree files.

See [Java Compiler Compiler tm (JavaCC tm) - The Java Parser Generator](https://javacc.github.io/javacc/).



ParserGeneratorCC = 1.1.3



# Changelog

- 1.0.2 - 2020-11-16
  - [Fix bug: build wrong with use tokenExtends option](https://github.com/take-me-home/javacc-gradle-plugin/commit/64ef245)
- 1.0.1 - 2020-11-14
  - Updated to ParserGeneratorCC 1.1.3



# Usage

***This plugin not provided on any repository, not yet.***



To apply the JavaCC Gradle Plugin to your projects, add the following in your build script:

Groovy

**build.gradle.**

    plugins {
        id 'net.bitnine.gradle.javacc' version '{latestRevision}'
    }
    
    javacc {
        // configuration container for all javacc configurations
        configs {
            template {
                inputFile = file('jj/TemplateParser.jj')
                packageName = 'com.corporate.internal.parser'
                lookahead = '2'
            }
        }
    }

Kotlin

**build.gradle.kts.**

    plugins {
        id("net.bitnine.gradle.javacc") version "{latestRevision}"
    }
    
    javacc {
        // configuration container for all javacc configurations
        configs {
            register("template") {
                inputFile = file("jj/TemplateParser.jj")
                packageName = "com.corporate.internal.parser"
                lookahead = "2"
            }
        }
    }



# License

Copyright 2014-2019 Intershop Communications.

Licensed under the Apache License, Version 2.0 (the "License"); you may
not use this file except in compliance with the License. You may obtain
a copy of the License at

<http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
