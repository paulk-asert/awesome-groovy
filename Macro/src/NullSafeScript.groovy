class X {
    String name
}
class Y {
    List<X> list
}
class Z {
    Y y
}
def getName1(Z z) {
    z.y.list[0].name
}
def getName2(Z z) {
    def result = null
    if (z != null && z.y != null && z.y.list != null && z.y.list[0] != null) {
        result = z.y.list[0].name
    }
    result
}
def getName3(Z z) {
    //z?.y?.list?[0]?.name
}
def getName4(Z z) {
    nullSafe(z.y.list[0].name)
}
