package style

interface PersonI {
    String getFirstName()
    String getLastName()
    String fullName()
}

def map
map = [getFirstName: { 'John' },
       getLastName: {'Wayne'},
       fullName: { map.getFirstName() +
            " ${map.getLastName()}" }]

def p3 = map as PersonI
assert p3.firstName == 'John' &&
        p3.fullName() == 'John Wayne' &&
        p3 instanceof PersonI
