//@Grab('http://www.emn.fr/z-info/choco-repo/mvn/repository')
//@Grab('choco:choco-solver:3.2.2')
//@Grab('org.slf4j:slf4j-simple:1.7.6')
//@GrabExclude('jgrapht:jgrapht')
//@GrabExclude('args4j:args4j')
//@GrabExclude('dk.brics.automaton:automaton')
import solver.Solver

import static solver.constraints.IntConstraintFactory.*
import static solver.variables.VariableFactory.*

def s = new Solver()

def totalAnimals = 7
def totalLegs = 20
def numCranes = bounded('Cranes', 0, totalAnimals, s)
def numTortoises = bounded('Tortoises', 0, totalAnimals, s)
def numCraneLegs = scale(numCranes, 2)
def numTortoiseLegs = scale(numTortoises, 4)
s.post(arithm(numCranes, '+', numTortoises, '=', totalAnimals))
s.post(arithm(numCraneLegs, '+', numTortoiseLegs, '=', totalLegs))
if (s.findSolution())
  println "$numCranes\n$numTortoises"
else
  println "No Solutions"
