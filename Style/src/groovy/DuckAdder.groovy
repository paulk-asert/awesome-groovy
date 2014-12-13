def adder = { a, b -> a + b }

assert adder(100, 200) == 300
assert adder('X', 'Y') == 'XY'
