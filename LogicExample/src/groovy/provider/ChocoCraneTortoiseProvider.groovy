package provider
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

@Field List<String> animals = []
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

class SeenHolder {
  List<String> animals
  SeenHolder(animals) { this.animals = animals }
  def include(String... animals) { this.animals.addAll(animals) }
}

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

SeenHolder animals(SeenStopWord _types) {
  new SeenHolder(animals)
}

def display(SolutionStopWord _solution) {
  def s = new Solver()
  def legCalculator = { String name ->
    switch(name.toLowerCase()) {
      case ~/.*crane/: return 2
      case ~/.*tortoise/: return 4
      case ~/.*millipede/: return 1000
    }
  }

  IntVar[] animalVars = animals.collect{
    int maxAnimals = legCount[0].intdiv(legCalculator(it))
    solver.variables.VariableFactory.bounded(it.toString(), 0, maxAnimals, s)
  }
  int[] numCoeff = [1] * animals.size()
  int[] legCoeff = animals.collect(legCalculator)
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

@TypeChecked(extensions='NatureServeAnimalProvider.groovy')
def main() {
  animals seen include SandhillCrane, GopherTortoise, ChihuahuanMillipede
  leg count is 1020
  head count is 8
  display solution
}

main()
