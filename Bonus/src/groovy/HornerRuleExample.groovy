package groovy
/*
  Closure pa = { x -> 3 * x ** 3 + 4 * x ** 2 - 5 * x ** 1 + 6 }
  Closure pb = { x -> 3 * x * x * x + 4 * x * x - 5 * x + 6 }
  Closure pc = { x -> 6 + x * ( -5 + x * ( 4 + x * 3 ) ) }
*/

class HornerCalc {
  @Horner([3, -2, 0, 4]) pa
  @Horner([3, 4, -5, 6]) pb
  @Horner([4, 0, -1]) pc
//  @Horner([-3.5d, 0d, -1.5d]) pd
}

def calc = new HornerCalc()
assert calc.pa(2) == 20
assert calc.pb(2) == 36
assert calc.pc(5) == 99
//assert calc.pd(4.0) == -57.5

def hornersRule = { cs, x -> cs.reverse().inject(0) { accum, c -> accum * x + c } }
def coeff = [6, -5, 4, 3]
println hornersRule(coeff, 2)
def coeff2 = [-19g, 7g, -4g, 6g]
def pe = hornersRule.curry(coeff2)
println pe(3)
