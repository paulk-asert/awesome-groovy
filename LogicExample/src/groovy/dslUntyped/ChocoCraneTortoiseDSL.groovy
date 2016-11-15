package dslUntyped

import groovy.transform.Field
import org.chocosolver.solver.Model
import org.chocosolver.solver.variables.IntVar

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
  def m = new Model()
  IntVar[] animalVars = legCount.collect { a, n ->
    int maxAnimals = total.legs.intdiv(n)
    m.intVar(a, 0, maxAnimals, true)
  }
  int[] numCoeff = [1] * legCount.size()
  int[] legCoeff = legCount.collect { it.value }
  m.scalar(animalVars, numCoeff, '=', m.intVar(total.animals)).post()
  m.scalar(animalVars, legCoeff, '=', m.intVar(total.legs)).post()
  //m.set(lexico_LB(animalVars))

  def s = m.solver
  if (s.solve())
    animalVars.each { println it }
  else
    println "No Solutions"
}

cranes have 2 legs
tortoises have 4 legs
there are 7 animals
there are 20 legs
display solution
