package hu.blackbelt.judo.generator.parser.derived.expression.sql;

import hu.blackbelt.judo.generator.parser.derived.expression.sql.model.ExpressionPart;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class DerivedAttributeExpressionToRdbmsModelParserTest {


    public void testRet(List<ExpressionPart> ret) {
        assertThat(ret, hasSize(4));

        assertThat(ret.get(0), allOf(
                hasProperty("labelExpression",
                        hasProperty("text", equalTo("Part 1:"))),
                hasProperty("relationExpression", nullValue())
        ));

        assertThat(ret.get(1), allOf(
                hasProperty("labelExpression", nullValue()),
                hasProperty("relationExpression", allOf(
                        hasProperty("relationName", equalTo("a")),
                        hasProperty("relationExpression", allOf(
                                hasProperty("relationName", equalTo("b")),
                                hasProperty("relationExpression", allOf(
                                        hasProperty("relationName", equalTo("c")),
                                        hasProperty("relationExpression", allOf(
                                                hasProperty("relationName", equalTo("d")),
                                                hasProperty("relationExpression", nullValue())
                                        ))
                                ))
                        ))
                ))
        ));

        assertThat(ret.get(2), allOf(
                hasProperty("labelExpression",
                        hasProperty("text", equalTo("Part 2:"))),
                hasProperty("relationExpression", nullValue())
        ));

        assertThat(ret.get(3), allOf(
                hasProperty("labelExpression", nullValue()),
                hasProperty("relationExpression", allOf(
                        hasProperty("relationName", equalTo("g")),
                        hasProperty("relationExpression", allOf(
                                hasProperty("relationName", equalTo("h")),
                                hasProperty("relationExpression", nullValue())
                        ))
                ))
        ));

    }
    @Test
    public void testParse() {
        testRet(new DerivedAttributeExpressionToRdbmsModelParser().parseExpression("'Part 1:' {self.a.b.c.d} 'Part 2:' {self.g.h}"));
        testRet(new DerivedAttributeExpressionToRdbmsModelParser().parseExpression("'Part 1:' self.a.b.c.d 'Part 2:' self.g.h"));
        new DerivedAttributeExpressionToRdbmsModelParser().parseExpression("{self.reqDataTypeDef.name} - {self.firstOccurence}, {self.repetitionRate}, {self.repetitionUnit} \"");
    }

}
