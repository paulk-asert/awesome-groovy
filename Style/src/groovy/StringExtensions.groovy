assert 'Hello'.reverse() == 'olleH'

String.metaClass.swapCase = { delegate.collect{
  it in 'A'..'Z' ? it.toLowerCase() : it.toUpperCase()
}.join() }

assert 'Hello'.swapCase() == 'hELLO'
