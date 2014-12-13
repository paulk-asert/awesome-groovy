package dslTyped
//@Grab('http://www.emn.fr/z-info/choco-repo/mvn/repository')
//@Grab('choco:choco-solver:3.2.2')
//@Grab('org.slf4j:slf4j-simple:1.7.6')
//@GrabExclude('jgrapht:jgrapht')
//@GrabExclude('args4j:args4j')
//@GrabExclude('dk.brics.automaton:automaton')
import groovy.transform.Field
import groovy.transform.TypeChecked

import Solver
import IntVar

import static IntConstraintFactory.*
import static IntStrategyFactory.lexico_LB
import static VariableFactory.*

@Field List<Animal> _animals = []
@Field List<Integer> headCount = []
@Field List<Integer> legCount = []

class CountHolder {
  List<Integer> counter
  CountHolder(counter) {
    this.counter = counter
  }
  void is(int count) {
    counter << count
  }
}

class TypesHolder {
  List<Animal> animals
  TypesHolder(animals) { this.animals = animals }
  def include(Animal... animals) { this.animals.addAll(animals) }
}

enum Animal {
  Crane(2), Tortoise(4), Beetle(6), Centipede(100), Millipede(1000)
  int legs
  Animal(int legs) {
    this.legs = legs
  }
}
import static Animal.*

enum SeenStopWord { seen }
import static SeenStopWord.*

enum CountStopWord { count }
import static CountStopWord.*

enum SolutionStopWord { solution }
import static SolutionStopWord.*

CountHolder leg(CountStopWord _count) {
  new CountHolder(legCount)
}

CountHolder head(CountStopWord _count) {
  new CountHolder(headCount)
}

TypesHolder animals(SeenStopWord _types) {
  new TypesHolder(_animals)
}

def display(SolutionStopWord _solution) {
  def s = new Solver()

  IntVar[] animalVars = _animals.collect{
    int maxAnimals = legCount[0].intdiv(it.legs)
    solver.variables.VariableFactory.bounded(it.toString(), 0, maxAnimals, s)
  }
  int[] numCoeff = [1] * _animals.size()
  int[] legCoeff = _animals.collect{ it.legs }
  s.post(solver.constraints.IntConstraintFactory.scalar(animalVars, numCoeff, solver.variables.VariableFactory.fixed(headCount[0], s)))
  s.post(solver.constraints.IntConstraintFactory.scalar(animalVars, legCoeff, solver.variables.VariableFactory.fixed(legCount[0], s)))
  s.set(solver.search.strategy.IntStrategyFactory.lexico_LB(animalVars))

  def more = s.findSolution()
  def pretty = { it.value ? ["$it.name = $it.value"] : [] }
  while (more) {
    print "Solution: "
    println animalVars.collectMany(pretty).join(', ')
    more = s.nextSolution()
  }
}

@TypeChecked
def main() {
  animals seen include Crane, Tortoise, Beetle, Centipede
  leg count is 150
  head count is 26
  display solution
}

main()
