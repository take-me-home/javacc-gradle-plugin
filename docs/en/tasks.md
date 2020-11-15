Tasks
=====

The JavaCC Gradle plugin adds one task `javacc` to the project.

This task depends on all other plugin task. It is a task added for each configuration.

| Task name                  | Type                                       | Description                                                  |
| -------------------------- | ------------------------------------------ | ------------------------------------------------------------ |
| javacc                     | Task                                       | Overall ``javaCC`` code generation taks of a project. This tasks depends onl la |
| javacc<configuration name> | com.intershop.build.javacc.task.JavaCCTask | This task generates Java code for the specified configuration. |

