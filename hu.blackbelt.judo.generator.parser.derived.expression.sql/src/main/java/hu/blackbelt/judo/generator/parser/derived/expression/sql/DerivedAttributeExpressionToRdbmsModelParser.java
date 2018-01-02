package hu.blackbelt.judo.generator.parser.derived.expression.sql;

import hu.blackbelt.judo.generator.parser.derived.expression.sql.model.ExpressionPart;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
public final class DerivedAttributeExpressionToRdbmsModelParser {
    public DerivedAttributeExpressionToRdbmsModelParser() {
    }

    public List<ExpressionPart> parseExpression(String expr) {
        String exprOrig  = expr;
        expr = expr.trim();
        if (!expr.startsWith("{")) {
            expr = expr.replaceAll("(^[^\\'\\{\\}]*)\\{", "'$1' {");
        }
        expr = expr.replaceAll("\\}([^\\'\\{\\}]*)\\{", "} '$1' {");
        if (!expr.endsWith("}")) {
            expr = expr.replaceAll("\\}([^\\'\\{\\}]*)$", "} '$1'");
        }

        log.info("Original: " + exprOrig + " Parsing: " + expr);

        DerivedAttributeExpressionLexer lexer = new DerivedAttributeExpressionLexer(new ANTLRInputStream(expr));

        // Get a list of matched tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Pass the tokens to the parser
        DerivedAttributeExpressionParser parser = new DerivedAttributeExpressionParser(tokens);

        DerivedAttributeExpressionParserErrorListener errorListener = new DerivedAttributeExpressionParserErrorListener(expr);
        parser.addErrorListener(errorListener);
        // Specify our entry point
        DerivedAttributeExpressionParser.ParseContext dervedAttributeExpressionContext = parser.parse();

        if (errorListener.isFail()) {
            throw new IllegalArgumentException(errorListener.getMessage());
        }

        // Visitor pattern
        DerivedAttributeExpressionModelStructBuilderVisitor visitor = new DerivedAttributeExpressionModelStructBuilderVisitor();
        String output = visitor.visit(dervedAttributeExpressionContext);
        log.debug("\n\nTree:\n\n" + output);

        return visitor.getExpressionParts();
    }
}

