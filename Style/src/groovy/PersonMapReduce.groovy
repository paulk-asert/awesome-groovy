import groovy.transform.Canonical

//import java.util.stream.Collectors

@Canonical
class Person {
  String name
  int age
}

def people = [
        new style.Person('Peter', 45),
        new style.Person('Paul', 35),
        new style.Person('Mary', 25)
]

assert people
    .findAll{ it.age < 40 }
    .collect{ it.name.toUpperCase() }
    .sort()
    .join(', ') == 'MARY, PAUL'

// requires JRE 8+
/*
def commaSep = Collectors.joining(", ")
assert people.stream()
    .filter{ it.age < 40 }
    .map{ it.name.toUpperCase() }
    .sorted()
    .collect(commaSep) == 'MARY, PAUL'
 */