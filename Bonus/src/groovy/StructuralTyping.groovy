import groovy.transform.TypeChecked

interface ReadOnlyCollection {
  boolean contains(Object o)
  boolean isEmpty()
}

@TypeChecked(extensions='StructuralProvider.groovy')
def method() {
  def list = ['cat', 'dog']
  assert !(list instanceof ReadOnlyCollection)
  ReadOnlyCollection roc = list //as ReadOnlyCollection
  assert !roc.isEmpty()
  assert roc.contains('cat')
}

method()
