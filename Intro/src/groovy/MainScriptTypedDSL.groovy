import groovy.transform.Field
import groovy.transform.TypeChecked

@Field
static List<String> names = []

enum Of { of }
import static Of.*

enum Having { having }
import static Having.*

enum Less { less }
import static Less.*

enum All { all }
import static All.*

enum The { the }
import static The.*

class TypedDSL {
    static GivenThe given(The the) { new GivenThe() }
    static DisplayThe display(The the) { new DisplayThe() }
    static DisplayAll display(All all) { new DisplayAll() }
}
class DisplayAll {
    void the(List<String> names) {
        println names
    }
}
class GivenThe {
    AndConnector names(String[] listOfAllNamesButLast) {
        MainScriptTypedDSL.names.addAll(listOfAllNamesButLast)
        new AndConnector()
    }
    void name(String singleName) {
        MainScriptTypedDSL.names += singleName
    }
}
class DisplayThe {
    DisplayTheNamesHaving names(Having having) {
        new DisplayTheNamesHaving()
    }
    DisplayTheNumberOf number(Of of) {
        new DisplayTheNumberOf()
    }
}
class AndConnector {
    void and(String finalName) { MainScriptTypedDSL.names += finalName }
}
class DisplayTheNumberOf {
    DisplayTheNumberOfNamesHaving names(Having having) {
        new DisplayTheNumberOfNamesHaving()
    }
}
class DisplayTheNamesHaving {
    DisplayTheNamesHavingSizeLess size(Less less) {
        new DisplayTheNamesHavingSizeLess()
    }
}
class DisplayTheNamesHavingSizeLess {
    void than(int size) {
        MainScriptTypedDSL.names.findAll { it.size() < size }.each { println it }
    }
}
class DisplayTheNumberOfNamesHaving {
    DisplayTheNumberOfNamesHavingSizeLess size(Less less) {
        new DisplayTheNumberOfNamesHavingSizeLess()
    }
}
import static TypedDSL.*

class DisplayTheNumberOfNamesHavingSizeLess {
    void than(int size) {
        println MainScriptTypedDSL.names.findAll { it.size() < size }.size()
    }
}

given the names "Ted", "Fred", "Jed" and "Ned"
display all the names
display the number of names having size less than 4
display the names having size less than 4
