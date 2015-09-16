//@Grab('org.choco-solver:choco-solver:3.3.1')
//@Grab('org.slf4j:slf4j-simple:1.7.12')
//@GrabExclude('org.javabits.jgrapht:jgrapht-core')
//@GrabExclude('args4j:args4j')
//@GrabExclude('dk.brics.automaton:automaton')

import org.chocosolver.solver.Solver
import org.chocosolver.solver.constraints.Constraint
import org.chocosolver.solver.constraints.IntConstraintFactory
import org.chocosolver.solver.variables.IntVar
import org.chocosolver.solver.variables.VariableFactory

import static org.chocosolver.solver.search.strategy.IntStrategyFactory.lexico_LB

abstract class ChocoBaseScript extends Script {
  static s = new Solver()

  public static IntVar bounded(String name, IntRange r) {
    VariableFactory.bounded(name, r.from, r.to, s)
  }

  public static boolean findSolution(IntVar... vars) {
    s.set(lexico_LB(vars))
    s.findSolution()
  }

  public static IntVar scale(IntVar var, int cste) {
    VariableFactory.scale(var, cste)
  }

  public static Constraint arithm(IntVar var1, String op1, IntVar var2, String op2, int cste) {
    s.post(IntConstraintFactory.arithm(var1, op1, var2, op2, cste))
  }
}
