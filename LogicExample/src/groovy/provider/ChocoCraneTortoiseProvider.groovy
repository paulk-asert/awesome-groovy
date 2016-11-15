package provider
//@Grab('org.choco-solver:choco-solver:3.3.1')
//@Grab('org.slf4j:slf4j-simple:1.7.12')
//@GrabExclude('org.javabits.jgrapht:jgrapht-core')
//@GrabExclude('args4j:args4j')
//@GrabExclude('dk.brics.automaton:automaton')
import groovy.transform.Field
import groovy.transform.TypeChecked
import org.chocosolver.solver.Model
import org.chocosolver.solver.variables.IntVar

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
  def m = new Model()
  def legCalculator = { String name ->
    switch(name.toLowerCase()) {
      case ~/.*crane/: return 2
      case ~/.*tortoise/: return 4
      case ~/.*millipede/: return 1000
    }
  }

  IntVar[] animalVars = animals.collect{
    int maxAnimals = legCount[0].intdiv(legCalculator(it))
    m.intVar(it.toString(), 0, maxAnimals, true)
  }
  int[] numCoeff = [1] * animals.size()
  int[] legCoeff = animals.collect(legCalculator)
  m.scalar(animalVars, numCoeff, '=', m.intVar(headCount[0])).post()
  m.scalar(animalVars, legCoeff, '=', m.intVar(legCount[0])).post()
  //m.set(lexico_LB(animalVars))

  def solver = m.solver
  def pretty = { it.value ? ["$it.name = $it.value"] : [] }
  while (solver.solve()) {
    print "Solution: "
    println animalVars.collectMany(pretty).join(', ')
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
