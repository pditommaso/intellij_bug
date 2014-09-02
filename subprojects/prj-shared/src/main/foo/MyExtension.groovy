package foo

import groovy.transform.CompileStatic

@CompileStatic
class MyExtensions {

    static String bar(String self) {
        self.substring(0,1).toUpperCase() + self.substring(1)
    }

}
