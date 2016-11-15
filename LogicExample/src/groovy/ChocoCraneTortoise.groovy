@Grab('org.choco-solver:choco-solver:4.0.0')
//@Grab('org.choco-solver:choco-solver:3.3.1')
//@Grab('org.slf4j:slf4j-simple:1.7.12')
//@GrabExclude('org.javabits.jgrapht:jgrapht-core')
//@GrabExclude('args4j:args4j')
//@GrabExclude('dk.brics.automaton:automaton')
import org.chocosolver.solver.Model

def m = new Model()

def totalAnimals = 7
def totalLegs = 20
def numCranes = m.intVar('Cranes', 0, totalAnimals, true)
def numTortoises = m.intVar('Tortoises', 0, totalAnimals, true)
def numCraneLegs = m.intScaleView(numCranes, 2)
def numTortoiseLegs = m.intScaleView(numTortoises, 4)
m.arithm(numCranes, '+', numTortoises, '=', totalAnimals).post()
m.arithm(numCraneLegs, '+', numTortoiseLegs, '=', totalLegs).post()
if (m.solver.solve())
    println "$numCranes\n$numTortoises"
else
    println "No Solutions"
