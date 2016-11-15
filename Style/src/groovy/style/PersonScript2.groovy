package style

def p2
p2 = [firstName: 'John',
          lastName: 'Wayne',
          fullName: { "$p2.firstName $p2.lastName" }]
assert p2.firstName == 'John' &&
        p2.fullName() == 'John Wayne'
