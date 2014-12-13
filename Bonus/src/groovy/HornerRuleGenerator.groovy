package groovy

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.AnnotatedNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.VariableScope
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.ListExpression
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.syntax.Token
import org.codehaus.groovy.syntax.Types
import org.codehaus.groovy.transform.AbstractASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

import static org.codehaus.groovy.ast.ClassHelper.make
import static org.codehaus.groovy.ast.tools.GeneralUtils.block
import static org.codehaus.groovy.ast.tools.GeneralUtils.closureX
import static org.codehaus.groovy.ast.tools.GeneralUtils.constX
import static org.codehaus.groovy.ast.tools.GeneralUtils.stmt
import static org.codehaus.groovy.ast.tools.GeneralUtils.varX

@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
class HornerRuleGenerator extends AbstractASTTransformation {

  private static final ClassNode MY_TYPE = make(Horner.class)
  private static final String MY_TYPE_NAME = "@" + MY_TYPE.nameWithoutPackage

  void visit(ASTNode[] nodes, SourceUnit source) {
    init(nodes, source)
    AnnotatedNode parent = (AnnotatedNode) nodes[1]
    AnnotationNode node = (AnnotationNode) nodes[0]
    if (!MY_TYPE.equals(node.getClassNode())) return

    if (parent instanceof FieldNode) {
      FieldNode fNode = (FieldNode) parent
      List<Number> coeffs = getMemberNumberList(node, 'value')
      if (coeffs.size() < 1) {
        addError("Error during " + MY_TYPE_NAME + " processing. Empty coefficients not allowed!", fNode)
        return
      }
      def p = varX('it')
      def result = coeffs.inject(constX(0)) { Expression accum, coeff ->
        plusX(timesX(p, accum), coeff)
      }
      def hornerClosure = closureX(block(stmt(result)))
      hornerClosure.setVariableScope(new VariableScope())
      fNode.setInitialValueExpression(hornerClosure)
    }
  }

  private static Expression plusX(Expression x, Number n) {
    new BinaryExpression(x, PLUS, constX(n))
  }

  private static Expression timesX(Expression lhs, Expression rhs) {
    new BinaryExpression(lhs, TIMES, rhs)
  }

  private static final Token PLUS = Token.newSymbol(Types.PLUS, -1, -1)
  private static
  final Token TIMES = Token.newSymbol(Types.MULTIPLY, -1, -1)

  private static List<Number> getMemberNumberList(AnnotationNode anno, String name) {
    List<Number> list
    Expression expr = anno.getMember(name)
    if (expr != null && expr instanceof ListExpression) {
      list = new ArrayList<Number>()
      final ListExpression listExpression = (ListExpression) expr
      for (Expression itemExpr : listExpression.getExpressions()) {
        if (itemExpr != null && itemExpr instanceof ConstantExpression) {
          Object value = ((ConstantExpression) itemExpr).getValue()
          if (value != null) list.add(value as Number)
        }
      }
    } else {
      list = []
    }
    list
  }
}
