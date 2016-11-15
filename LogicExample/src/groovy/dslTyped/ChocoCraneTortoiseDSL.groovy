package dslTyped
@Grab('org.choco-solver:choco-solver:4.0.0')
//@Grab('org.slf4j:slf4j-simple:1.7.12')
@GrabExclude('org.javabits.jgrapht:jgrapht-core')
@GrabExclude('args4j:args4j')
@GrabExclude('dk.brics.automaton:automaton')
import groovy.transform.Field
import groovy.transform.TypeChecked
import org.chocosolver.solver.Model
import org.chocosolver.solver.variables.IntVar

//import static org.chocosolver.solver.search.strategy.IntStrategyFactory.lexico_LB

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
  def m = new Model()

  IntVar[] animalVars = _animals.collect{
  int maxAnimals = legCount[0].intdiv(it.legs)
  m.intVar(it.toString(), 0, maxAnimals, true)
  }
  int[] numCoeff = [1] * _animals.size()
  int[] legCoeff = _animals.collect{ it.legs }
  m.scalar(animalVars, numCoeff, '+', m.intVar(headCount[0])).post()
  m.scalar(animalVars, legCoeff, '+', m.intVar(legCount[0])).post()
  //m.set(lexico_LB(animalVars))

  def solver = m.solver
  def pretty = { it.value ? ["$it.name = $it.value"] : [] }
  while (solver.solve()) {
    print "Solution: "
    println animalVars.collectMany(pretty).join(', ')
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
