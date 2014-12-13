@BaseScript(ChocoBaseScript)
import groovy.transform.BaseScript

def totalAnimals = 7
def totalLegs = 20
def numCranes = bounded('Cranes', 0..totalAnimals)
def numTortoises = bounded('Tortoises', 0..totalAnimals)
def numCraneLegs = scale(numCranes, 2)
def numTortoiseLegs = scale(numTortoises, 4)
arithm(numCranes, '+', numTortoises, '=', totalAnimals)
arithm(numCraneLegs, '+', numTortoiseLegs, '=', totalLegs)
if (findSolution(numCranes, numTortoises))
  println "$numCranes\n$numTortoises"
else
  println "No Solutions"
