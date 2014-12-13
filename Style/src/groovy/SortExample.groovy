/*
def sort(list) {
  if (list.size() < 2) return list
  def pivot = list[0]
  def less = list.findAll{it < pivot}
  def equal = list.findAll{it == pivot}
  def more = list.findAll{it > pivot}
  sort(less) + equal + sort(more)
}
assert sort([5,9,3,7,1]) == [1,3,5,7,9]
/*
def sort(list) {
  if (list.size() < 2) return list
  def groups = list.groupBy{ it <=> list[0] }.withDefault{ [] }
  sort(groups[-1]) + groups[0] + sort(groups[1])
}
/* */
def sort(list) {
  if (list.size() < 2) return list
  def pivot = list.head()
  def (less, more) = list.tail().split{ it <= pivot }
  sort(less) + pivot + sort(more)
}
/* */
assert sort([5,9,3,7,1]) == [1,3,5,7,9]
/* */
