package groovy

import groovy.transform.Memoized
import groovy.transform.TailRecursive

//def expN = { x, n -> println "$x $n"; n == 0 ? 1 : x * doCall(x, n - 1) }.memoize()
//def expN
//expN = { x, n -> println "$x $n"; n == 0 ? 1 : x * expN(x, n - 1) }.memoize()

//@TailRecursive
@Memoized
def expN(x, n) {
  println "$x $n"
  if (n == 0) return 1
  x * expN(x, n - 1)
}
println expN(2, 4)
println expN(2, 5)
