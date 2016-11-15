package style

class Person {
    String firstName
    String lastName
    String fullName() { "$firstName $lastName" }
}

def p1 = new Person(firstName: 'John', lastName: 'Wayne')
assert p1.firstName == 'John' &&
        p1.fullName() == 'John Wayne'
