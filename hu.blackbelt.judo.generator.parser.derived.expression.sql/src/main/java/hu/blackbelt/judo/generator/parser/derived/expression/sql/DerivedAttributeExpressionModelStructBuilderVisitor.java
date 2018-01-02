package hu.blackbelt.judo.generator.parser.derived.expression.sql;

import hu.blackbelt.judo.generator.parser.derived.expression.sql.model.ExpressionPart;
import hu.blackbelt.judo.generator.parser.derived.expression.sql.model.LabelExpression;
import hu.blackbelt.judo.generator.parser.derived.expression.sql.model.RelationExpression;
import lombok.Getter;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"checkstyle:missingctor", "checkstyle:methodcount"})
public class DerivedAttributeExpressionModelStructBuilderVisitor implements DerivedAttributeExpressionVisitor<String> {

    private int indentLevel;

    @Getter
    private List<ExpressionPart> expressionParts = new ArrayList<>();

    private List<RelationExpression> relationExpressionList = new ArrayList<>();

    public String visit(ParseTree arg0) {
        return arg0.accept(this);
    }

    public String visitChildren(RuleNode arg0) {
        int childrenCount = arg0.getChildCount();
        StringBuilder builder = new StringBuilder();
        increaseIndentationLevel();
        for (int i = 0; i < childrenCount; i++) {
            builder.append(arg0.getChild(i).accept(this));
        }
        decreaseIndentationLevel();
        return builder.toString();
    }

    public String visitErrorNode(ErrorNode arg0) {
        return null;
    }

    public String visitTerminal(TerminalNode arg0) {
        return indent() + "[terminal] '" + arg0.getText() + "'" + n();
    }

    private String indent() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < getIndentLevel(); i++) {
            builder.append("\t");
        }
        return builder.toString();
    }

    private void increaseIndentationLevel() {
        this.indentLevel += 1;
    }

    private void decreaseIndentationLevel() {
        this.indentLevel -= 1;
    }

    public int getIndentLevel() {
        return indentLevel;
    }

    public void setIndentLevel(int indentLevel) {
        this.indentLevel = indentLevel;
    }

    private String n() {
        return System.lineSeparator();
    }

    @Override
    public String visitParse(DerivedAttributeExpressionParser.ParseContext ctx) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("[parse]").append(n());
        builder.append(visitChildren(ctx));
        return builder.toString();
    }

    @Override
    public String visitFormulas(DerivedAttributeExpressionParser.FormulasContext ctx) {
        return indent() + "[formulas]" + n() + visitChildren(ctx);
    }

    @Override
    public String visitExpressionFormula(DerivedAttributeExpressionParser.ExpressionFormulaContext ctx) {
        relationExpressionList = new ArrayList<>();
        String ret =  indent() + "[expessionFormula]" + n() + visitChildren(ctx) + indent() + "Add Relation expression" + ctx.getText() + "\n";

        Collections.reverse(relationExpressionList);
        final RelationExpression[] exp = new RelationExpression[1];
        relationExpressionList.stream().forEach(e -> { exp[0] = new RelationExpression(e.getRelationName(), exp[0]);});
        expressionParts.add(new ExpressionPart(exp[0]));
        return ret;
    }

    @Override
    public String visitStringFormula(DerivedAttributeExpressionParser.StringFormulaContext ctx) {
        relationExpressionList = null;
        String str = ctx.getText();
        if (str.startsWith("'")) {
            str = str.substring(1);
        }
        if (str.endsWith("'")) {
            str = str.substring(0, str.length() - 1);
        }

        String ret =  indent() + "[stringFormula]" + n() + visitChildren(ctx) + indent() + "Add Label expression: " + ctx.getText() + "\n";
        expressionParts.add(new ExpressionPart((new LabelExpression(str))));
        return ret;
    }

    @Override
    public String visitSelfEntity(DerivedAttributeExpressionParser.SelfEntityContext ctx) {
        return indent() + "[selfEntity]" + n() + visitChildren(ctx);
    }

    @Override
    public String visitRelationChain(DerivedAttributeExpressionParser.RelationChainContext ctx) {
        return indent() + "[relationChain]" + n() + visitChildren(ctx);
    }

    @Override
    public String visitRelation(DerivedAttributeExpressionParser.RelationContext ctx) {
        String ret = indent() + "[relation]" + n() + visitChildren(ctx) + indent() + "Add Relation expression: " + ctx.getText() + "\n";
        relationExpressionList.add(new RelationExpression(ctx.getText(), null));
        return ret;
    }

}
