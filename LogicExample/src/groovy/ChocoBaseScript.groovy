//@Grab('org.choco-solver:choco-solver:3.3.1')
//@Grab('org.slf4j:slf4j-simple:1.7.12')
//@GrabExclude('org.javabits.jgrapht:jgrapht-core')
//@GrabExclude('args4j:args4j')
//@GrabExclude('dk.brics.automaton:automaton')

import org.chocosolver.solver.Model
import org.chocosolver.solver.constraints.Constraint
import org.chocosolver.solver.variables.IntVar

//import static org.chocosolver.solver.search.strategy.IntStrategyFactory.lexico_LB

abstract class ChocoBaseScript extends Script {
  static m = new Model()

  public static IntVar bounded(String name, IntRange r) {
    m.intVar(name, r.from, r.to, true)
  }

  public static boolean findSolution(IntVar... vars) {
    //m.set(lexico_LB(vars))
    m.solver.solve()
  }

  public static IntVar scale(IntVar var, int cste) {
    m.intScaleView(var, cste)
  }

  public static Constraint arithm(IntVar var1, String op1, IntVar var2, String op2, int cste) {
    m.arithm(var1, op1, var2, op2, cste).post()
  }
}
