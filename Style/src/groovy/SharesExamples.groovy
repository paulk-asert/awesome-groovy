Number.metaClass.getShares = { delegate }
Number.metaClass.getDollars = { delegate }

String GOOG = 'Google'

def sell(int nShares) {
  [of: { String ticker ->
    [at: { int price ->
      println "Sold $nShares $ticker at \$$price"
    }]
  }]
}

sell 100.shares of GOOG at 1000.dollars
