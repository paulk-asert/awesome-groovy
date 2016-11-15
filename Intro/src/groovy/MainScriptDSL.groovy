names = []
def of, having, less
def given(_the) { [names:{ Object[] ns -> names.addAll(ns)
    [and: { n -> names += n }] }] }
def the = [
        number: { _of -> [names: { _having -> [size: { _less -> [than: { size ->
            println names.findAll{ it.size() < size }.size() }]}] }] },
        names: { _having -> [size: { _less -> [than: { size ->
            names.findAll{ it.size() < size }.each{ println it } }]}] }
]
def all = [the: { println it }]
def display(arg) { arg }

given the names "Ted", "Fred", "Jed" and "Ned"
display all the names
display the number of names having size less than 4

/**/    display the names having size less than 4
