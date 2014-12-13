

import solver.Solver
import solver.constraints.Constraint
import solver.constraints.IntConstraintFactory
import solver.variables.IntVar
import solver.variables.VariableFactory

import static solver.search.strategy.IntStrategyFactory.lexico_LB

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
