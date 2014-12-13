import groovy.transform.TailRecursive

/*
def fact
fact = { n, accu = 1G ->
  if (n < 2) accu
  else fact.trampoline(n - 1, n * accu)
}.trampoline()

/* */
def fact(n) {
  n < 2 ? 1 : n * fact(n - 1)
}
/*
@TailRecursive
def fact(n, accu=1G) {
  if (n < 2) accu
  else fact(n - 1, n * accu)
}
/* */
//println fact(10000)
println fact(5)
