package hu.blackbelt.judo.generator.parser.derived.expression.sql.model;

import lombok.Data;

@Data
public class LabelExpression {
    String text;

    public LabelExpression(String text) {
        this.text = text;
    }
}
