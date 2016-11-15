def keepShorterThan(strings, length) {
    def result = new ArrayList()
    for (s in strings) {
        if (s.size() < length) {
            result.add(s)
        }
    }
    return result
}

names = new ArrayList()
names.add("Ted"); names.add("Fred")
names.add("Jed"); names.add("Ned")
System.out.println(names)
shortNames = keepShorterThan(names, 4)
System.out.println(shortNames.size())
for (s in shortNames) {
    System.out.println(s)
}
