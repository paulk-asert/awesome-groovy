package provider
//@Grab('org.choco-solver:choco-solver:3.3.1')
//@Grab('org.slf4j:slf4j-simple:1.7.12')
//@GrabExclude('org.javabits.jgrapht:jgrapht-core')
//@GrabExclude('args4j:args4j')
//@GrabExclude('dk.brics.automaton:automaton')
import groovy.transform.Field
import groovy.transform.TypeChecked

import org.chocosolver.solver.Solver
import org.chocosolver.solver.variables.IntVar

import static org.chocosolver.solver.constraints.IntConstraintFactory.*
import static org.chocosolver.solver.search.strategy.IntStrategyFactory.lexico_LB
import static org.chocosolver.solver.variables.VariableFactory.*

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
    bounded(it.toString(), 0, maxAnimals, s)
  }
  int[] numCoeff = [1] * animals.size()
  int[] legCoeff = animals.collect(legCalculator)
  s.post(scalar(animalVars, numCoeff, fixed(headCount[0], s)))
  s.post(scalar(animalVars, legCoeff, fixed(legCount[0], s)))
  s.set(lexico_LB(animalVars))

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
