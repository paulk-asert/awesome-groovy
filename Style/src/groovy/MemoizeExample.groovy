import groovy.transform.Memoized

/*
def fib
fib = { long n ->
  if (n < 2) 1
  else fib(n - 1) + fib(n - 2)
}.memoize()
/* */
@Memoized
def fib(long n) {
  if (n < 2) 1
  else fib(n - 1) + fib(n - 2)
}
/* */
assert fib(10) == 89
