import groovy.transform.Field
import org.codehaus.groovy.ast.expr.ConstantExpression
import static org.codehaus.groovy.ast.ClassHelper.STRING_TYPE

@Field List<String> cachedAnimalNames = null
@Field boolean offline = true

unresolvedVariable { var ->
  if (offline && !cachedAnimalNames) {
    cachedAnimalNames = ['SandhillCrane', 'GopherTortoise', 'ChihuahuanMillipede']
  }
  if (!cachedAnimalNames) {
    def accessKey = '72ddf45a-c751-44c7-9bca-8db3b4513347'
    // for illustrative purposes, just download xml for a few animals
    def uid = 'ELEMENT_GLOBAL.2.104550,ELEMENT_GLOBAL.2.105196,ELEMENT_GLOBAL.2.120227'
    def base = "https://services.natureserve.org/idd/rest/ns/v1.1/globalSpecies"
    def url = "$base/comprehensive?uid=$uid&NSAccessKeyId=$accessKey"
    def root = new XmlParser().parse(url)
    def names = root.globalSpecies.classification.names
    cachedAnimalNames = names.natureServePrimaryGlobalCommonName*.text()*.replaceAll(' ','')
  }
  if (var.name in cachedAnimalNames) {
    storeType(var, STRING_TYPE)
    handled = true
    enclosingClassNode.addField(var.name, 0, STRING_TYPE, new ConstantExpression(var.name))
  }
}
