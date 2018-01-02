package hu.blackbelt.judo.generator.parser.derived.expression.sql.model;

import lombok.Data;

@Data
public class RelationExpression {
    String relationName;
    RelationExpression relationExpression;

    public RelationExpression(String relationName, RelationExpression relationExpression) {
        this.relationName = relationName;
        this.relationExpression = relationExpression;
    }

    public boolean hasChild() {
        return relationExpression != null;
    }
}
