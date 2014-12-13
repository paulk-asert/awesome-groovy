import groovy.transform.Canonical

@Canonical
class Person {
  String name
  int age
}

def people = [
    new Person('Peter', 45),
    new Person('Paul', 35),
    new Person('Mary', 25)
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