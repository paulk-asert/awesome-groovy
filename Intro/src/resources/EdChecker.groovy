afterMethodCall { mc ->
    mc.arguments.each {
        if (isConstantExpression(it)) {
            if (it.value instanceof String && !it.value.endsWith('ed')) {
                addStaticTypeError("I don't like the name '${it.value}'", mc)
            }
        }
    }
}
