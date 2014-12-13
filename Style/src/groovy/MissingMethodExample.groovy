class Foo {
  def one() { println "Called one()" }
  def methodMissing(String name, params) {
    println "Attempted $name($params)"
  }
}

def f = new Foo()
f.one()
f.two()
f.three('Some Arg')
