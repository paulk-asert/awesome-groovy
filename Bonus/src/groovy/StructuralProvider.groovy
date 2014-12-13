import org.codehaus.groovy.ast.expr.*

incompatibleAssignment { lhsType, rhsType, expr ->
  if (!(expr instanceof DeclarationExpression)) return
  Expression orig = expr.rightExpression
  def ce = new CastExpression(lhsType, orig)
  ce.coerce = true
  expr.setRightExpression(ce)
  storeType(expr, lhsType)
  handled = true
}
