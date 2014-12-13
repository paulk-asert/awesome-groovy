import groovy.transform.TypeChecked
import org.codehaus.groovy.control.CompilationFailedException

import static groovy.test.GroovyAssert.assertScript
import static groovy.test.GroovyAssert.shouldFail

def myPets = ['Lassie', 'Skip']
List yourPets = ['Lassie', 'Skip']
shouldFail(ClassCastException) {
  List ourPets = new Date()
}

@TypeChecked
def myMethod() {
  List yourPets = ['Lassie', 'Skip']
}

shouldFail(CompilationFailedException) {
  assertScript '''
    @groovy.transform.TypeChecked
    def yourMethod() {
      List yourPets = new Date()
    }
  '''
}
