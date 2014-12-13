/**
 * Things that hop
 * @author Grace
 */
trait Hopper {
  String jump() { "I'm jumping!" }
}

class Frog implements Hopper {}

def f = new Frog()

assert f.jump() == "I'm jumping!"
