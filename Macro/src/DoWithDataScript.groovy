doWithData {
    dowith:
        assert a + b == c
    where:
        a | b || c
        1 | 2 || 3
        4 | 5 || 9
        7 | 8 || 15
}
