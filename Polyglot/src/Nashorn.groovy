import javax.script.ScriptEngineManager

def mgr = new ScriptEngineManager()
def engine = mgr.getEngineByName('nashorn')
assert engine.eval('''
function factorial(n) {
    if (n == 0) { return 1; }
    return n * factorial(n - 1);
}
factorial(4)
''') == 24.0

