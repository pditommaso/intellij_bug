intellij_bug
============

intellij project that replicated issue http://youtrack.jetbrains.com/issue/IDEA-129282

Problem 
----------

The problem is related how the file org.codehaus.groovy.runtime.ExtensionModule is managed by Gradle and IntelliJ 

First I've tried to clean up the project with Gradle. There's only one extension module definition file: 

    $ gradle clean 
    $ find . -name '*ExtensionModule'
    ./subprojects/prj-shared/src/resources/META-INF/services/org.codehaus.groovy.runtime.ExtensionModule

Then if you compile with Gradle, it will copy the extension definition file in the build path 
    
    $ gradle classes
    $ find . -name '*ExtensionModule'
    ./subprojects/prj-shared/build/resources/main/META-INF/services/org.codehaus.groovy.runtime.ExtensionModule
    ./subprojects/prj-shared/src/resources/META-INF/services/org.codehaus.groovy.runtime.ExtensionModule

Now any "incremental" compilation with IntelliJ will stop with an error. It complains that cannot find the extension methods definitions. 

The only way to have it to compile is rebuild the project. 

Run IntelliJ Build (menu: Build > Rebuild project). You will get:

    $ find . -name '*ExtensionModule'
    ./subprojects/prj-shared/build/classes/main/META-INF/services/org.codehaus.groovy.runtime.ExtensionModule
    ./subprojects/prj-shared/build/resources/main/META-INF/services/org.codehaus.groovy.runtime.ExtensionModule
    ./subprojects/prj-shared/src/resources/META-INF/services/org.codehaus.groovy.runtime.ExtensionModule 

At the end the problems is that the Gradle build expect the ExtensionModule file in the build/resources/main/.. path, while IntelliJ is expecting it in the path build/classes/main/..


Workaround 
-----------

Change the path where Gradle copies the resources to `build/classes/main` with the following snippet: 

    allprojects {
      sourceSets {
          main {
              output.resourcesDir = 'build/classes/main'
          }
      }
    }
