import groovy.transform.TypeChecked

@TypeChecked(extensions='PrintfChecker.groovy')
def method() {
  int count = 5
  def c = new Date()
  println sprintf("Count = %d", count) // check that count is a Number (Integral number?)
//  println sprintf("Euler's constant = %+10.4f", Math.E) // check that the argument is a Number (float or double?)
  println sprintf("Euler's constant = %f", Math.E) // check that the argument is a Number (float or double?)
//  println sprintf('Duke\'s Birthday: %1$tm %1$te,%1$tY', c) // check that c is a Date
}

method()
