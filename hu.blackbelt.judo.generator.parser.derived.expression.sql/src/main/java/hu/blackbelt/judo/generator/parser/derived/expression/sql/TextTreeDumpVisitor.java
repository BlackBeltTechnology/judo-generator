package hu.blackbelt.judo.generator.parser.derived.expression.sql;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

@SuppressWarnings({"checkstyle:missingctor", "checkstyle:methodcount"})
public class TextTreeDumpVisitor implements DerivedAttributeExpressionVisitor<String> {

    private int indentLevel;

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
        return indent() + "[expressionFormula]" + n() + visitChildren(ctx);
    }

    @Override
    public String visitStringFormula(DerivedAttributeExpressionParser.StringFormulaContext ctx) {
        return indent() + "[stringFormula]" + n() + visitChildren(ctx);
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
        return indent() + "[relation]" + n() + visitChildren(ctx);
    }
}
