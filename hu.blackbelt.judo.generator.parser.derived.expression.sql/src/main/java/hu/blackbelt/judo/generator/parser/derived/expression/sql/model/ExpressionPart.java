package hu.blackbelt.judo.generator.parser.derived.expression.sql.model;

import lombok.Data;

@Data
public class ExpressionPart {
    LabelExpression labelExpression;
    RelationExpression relationExpression;

    public ExpressionPart(LabelExpression labelExpression) {
        this.labelExpression = labelExpression;
    }

    public ExpressionPart(RelationExpression relationExpression) {
        this.relationExpression = relationExpression;
    }

    public boolean isExpressionLabelExpression() {
        return labelExpression != null;
    }

    public boolean isExpressionRelationExpression() {
        return relationExpression != null;
    }

}
