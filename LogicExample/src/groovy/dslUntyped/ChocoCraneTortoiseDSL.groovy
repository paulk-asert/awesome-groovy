package dslUntyped

import groovy.transform.Field
import Solver
import IntVar

import static IntConstraintFactory.scalar
import static IntStrategyFactory.lexico_LB
import static VariableFactory.bounded
import static VariableFactory.fixed

@Field legCount = [:]
@Field total = [:]

def legs = 'legs'
def animals = 'animals'
def have, are, solution
have = are = solution = ''

class IntHolder {
  Closure action
  String what
  IntHolder(w) { what = w }
  def whenMissing(Closure c) { action = c; this }
  def methodMissing(String name, args) {
    action(what, Integer.valueOf(name), args)
  }
}

def methodMissing(String name, _args) {
  new IntHolder(name).whenMissing { what, n, _ ->
    legCount[what] = n
  }
}

def there(_are) {
  new IntHolder('totals').whenMissing { _, n, args ->
    total[args[0]] = n
  }
}

def display(_solution) {
  def s = new Solver()
  IntVar[] animalVars = legCount.collect { a, n ->
    int maxAnimals = total.legs.intdiv(n)
    solver.variables.VariableFactory.bounded(a, 0, maxAnimals, s)
  }
  int[] numCoeff = [1] * legCount.size()
  int[] legCoeff = legCount.collect { it.value }
  s.post(solver.constraints.IntConstraintFactory.scalar(animalVars, numCoeff, fixed(total.animals, s)))
  s.post(solver.constraints.IntConstraintFactory.scalar(animalVars, legCoeff, fixed(total.legs, s)))
  s.set(solver.search.strategy.IntStrategyFactory.lexico_LB(animalVars))

  if (s.findSolution())
    animalVars.each { println it }
  else
    println "No Solutions"
}

cranes have 2 legs
tortoises have 4 legs
there are 7 animals
there are 20 legs
display solution
