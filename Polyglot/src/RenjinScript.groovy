@GrabResolver('https://nexus.bedatadriven.com/content/groups/public')
@Grab('org.renjin:renjin-script-engine:0.7.0-RC7')
import javax.script.ScriptEngineManager

def mgr = new ScriptEngineManager()
def engine = mgr.getEngineByName('Renjin')

//if(engine == null) {
//    throw new RuntimeException("Renjin Script Engine not found on the classpath.");
//}

engine.with {
  eval '''
    factorial <- function(x) {
      y <- 1
      for (i in 1:x) { y <- y * i }
      return(y)
    }
  '''
  assert eval('factorial(4)')[0] == 24
}
/*
eval("df <- data.frame(x=1:10, y=(1:10)+rnorm(n=10))")
engine.eval("print(df)")
engine.eval("print(lm(y ~ x, df))")
/* */
