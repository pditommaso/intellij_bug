package foo

import groovy.transform.CompileStatic

@CompileStatic
class Foo {

    def hello(String str) {
        return str.bar()
    }

    public static void main(String ... args ) {
        println new Foo().hello('ciao')
    }

}