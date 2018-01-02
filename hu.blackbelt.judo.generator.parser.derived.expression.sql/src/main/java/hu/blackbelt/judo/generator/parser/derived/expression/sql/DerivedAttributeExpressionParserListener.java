package hu.blackbelt.judo.generator.parser.derived.expression.sql;

@SuppressWarnings({"checkstyle:methodcount", "checkstyle:avoidinlineconditionals"})
public class DerivedAttributeExpressionParserListener extends DerivedAttributeExpressionBaseListener {

    /*
    public static final String DOT = ".";
    public static final String ID = "id";
    public static final String COLON = ":";
    private String navigatePath = "";
    private String attributePath = "";

    private Query.QueryBuilder queryBuilder = Query.builder();
    private Expression expression;
//    private Expression selfExpression;
    private boolean previousNavigateHasExpression;

    private Deque<List<Expression>> expressionStack = new ArrayDeque();
    private List<Expression> filterExpressions = Lists.newArrayList();
    private BoTypeResolver selfieTypeResolver;
    private Class entityClass;
    private Class selfClass;
    private Serializable selfId;

    public SelfieQueryParserListener() {
    }

    public SelfieQueryParserListener(Query.QueryBuilder pQueryBuilder, BoTypeResolver pSelfieTypeResolver,
                                     Class pSelfClass, Serializable pSelfId) {
        this.queryBuilder = pQueryBuilder;
        this.selfClass = pSelfClass;
        this.selfieTypeResolver = pSelfieTypeResolver;
        this.selfId = pSelfId;
    }

    public SelfieQueryParserListener(Expression pExpression) {
        this.expression = pExpression;
    }

    public Query.QueryBuilder buildQuery() {
        // TODO: Checking in META that relation exists or not?
        if (!Strings.isNullOrEmpty(navigatePath)) {
            queryBuilder.navigate(new Property(navigatePath));
        }
        if (expression != null) {
            expression = Expressions.and(expression, chainFilter(filterExpressions, false));
        } else {
            expression = chainFilter(filterExpressions, false);
        }
//        if (expression == null) {
//            expression = selfExpression;
//        } else  if (selfExpression != null) {
//            expression = Expressions.and(selfExpression, expression);
//        }
        queryBuilder.condition(expression);
        return queryBuilder;
    }

    @Override
    public void enterSelfEntity(SelfieParser.SelfEntityContext ctx) {
        LOGGER.debug("enterSelfEntity: " + ctx.getText());
        entityClass = selfClass;
        if (entityClass == null) {
            throw new IllegalStateException("Self class have not been set");
        }
        queryBuilder.source(entityClass);
        //if (selfId != null) {
            //filterExpressions.add(expression("id").equal(selfId));
        //    selfExpression = expression(ID).equal(selfId);
       // }
    }

    @Override
    public void exitSelfEntity(SelfieParser.SelfEntityContext ctx) {
    //    LOGGER.debug("exitSelfEntity: " + ctx.getText());
        filterExpressions.add(expression(ID).equal(selfId));
        Expression e = chainFilter(filterExpressions, false);
        filterExpressions.clear();
        filterExpressions.add(e);
        previousNavigateHasExpression = true;

    }

    @Override
    public void enterDomainEntity(SelfieParser.DomainEntityContext ctx) {
        LOGGER.debug("enterDomainEntity Domain: " + ctx.IDENTIFIER().get(0).getText() + " Class: " + ctx.IDENTIFIER().get(1).getText());
        if (selfieTypeResolver == null) {
            throw new IllegalStateException("There is no lookup service defined and custom type is used");
        }
        entityClass = selfieTypeResolver.reolveFullName(ctx.IDENTIFIER().get(0).getText() + COLON + ctx.IDENTIFIER().get(1).getText());
        if (entityClass == null) {
            throw new IllegalStateException("Class for domain class does not exists: " + ctx.IDENTIFIER().get(0).getText()
                    + COLON + ctx.IDENTIFIER().get(1).getText());
        }
        queryBuilder.source(entityClass);
    }

    @Override
    public void exitDomainEntity(SelfieParser.DomainEntityContext ctx) {
    //    LOGGER.debug("exitDomainEntity: " + ctx.getText());
    }

    @Override
    public void enterFilter(SelfieParser.FilterContext ctx) {
        LOGGER.debug("enterFilter: " + ctx.getText());
        expressionStack.push(Lists.<Expression>newArrayList());
    }

    @Override
    public void exitFilter(SelfieParser.FilterContext ctx) {
        List<Expression> expressions = expressionStack.pop();
        if (expressions.size() == 1) {
            previousNavigateHasExpression = true;
            filterExpressions.add(expressions.get(0));
            LOGGER.debug("FILTER: " + expressions.get(0));
        } else {
            throw new RuntimeException("Illegal number of expressions on filter stack");
        }
    }

    @Override
    public void enterRelation(SelfieParser.RelationContext ctx) {
        LOGGER.debug("--- enterRelation: " + ctx.IDENTIFIER().getSymbol().getText() + " - " + ctx.getText());
        String prefix = "";
        if (ctx.NOT() != null) {
            prefix = "!";
            Expression previousInvertedExpression = null;
            if (previousNavigateHasExpression) {
                Expression prevExpression = filterExpressions.get(filterExpressions.size() - 1);
                filterExpressions.remove(prevExpression);
                previousInvertedExpression = ExpressionNormalizer.normalize(Expressions.not(prevExpression));
            }
            Expression previousNavigateIdIsNullExpression = expression(ID).equal(null);
            if (!Strings.isNullOrEmpty(attributePath)) {
                previousNavigateIdIsNullExpression = expression(attributePath + DOT + ID).equal(null);
            }
            if (previousInvertedExpression == null) {
                filterExpressions.add(previousNavigateIdIsNullExpression);
            } else {
                filterExpressions.add(Expressions.or(previousNavigateIdIsNullExpression, previousInvertedExpression));
            }

            -/-*
            // When self entiy is defined and the related starts from self
            if (selfExpression != null && Strings.isNullOrEmpty(navigatePath)) {
                selfExpression = Expressions.or(expression(ID).notEqual(selfId), expression(ID).equal(null));
            } else {
                Expression negatedExpression = expression(ID).equal(null);
                if (!Strings.isNullOrEmpty(attributePath)) {
                    negatedExpression = expression(attributePath + DOT + ID).equal(null);
                }
                if (expression != null) {
                    expression = Expressions.and(expression, negatedExpression);
                } else {
                    expression = negatedExpression;
                }
            } -*-/
        }
        previousNavigateHasExpression = false;
        navigatePath = (Strings.isNullOrEmpty(navigatePath) ? "" : navigatePath + DOT) + prefix + ctx.IDENTIFIER().getSymbol().getText();
        attributePath = (Strings.isNullOrEmpty(attributePath) ? "" : attributePath + DOT) + ctx.IDENTIFIER().getSymbol().getText();
    }

    @Override
    public void exitRelation(SelfieParser.RelationContext ctx) {
    //    LOGGER.debug("exitRelation: " + ctx.getText());
    }

    @Override
    public void enterExpression(SelfieParser.ExpressionContext ctx) {
        LOGGER.debug("enterExpression: " + ctx.getText());
        expressionStack.push(Lists.<Expression>newArrayList());
    }

    @Override
    public void exitExpression(SelfieParser.ExpressionContext ctx) {
    //    LOGGER.debug("exitExpression: " + ctx.getText());

        Expression e = chainFilter(expressionStack.pop(), true);
        expressionStack.peek().add(e);
        LOGGER.debug("EXPRESSION: " + e);
    }

    @Override
    public void enterLogicalAndExpression(SelfieParser.LogicalAndExpressionContext ctx) {
    //    LOGGER.debug("enterLogicalAndExpression: " + ctx.getText());
        expressionStack.push(Lists.<Expression>newArrayList());
    }

    @Override
    public void exitLogicalAndExpression(SelfieParser.LogicalAndExpressionContext ctx) {
    //    LOGGER.debug("exitLogicalAndExpression: " + ctx.getText());
        Expression e = chainFilter(expressionStack.pop(), false);
        expressionStack.peek().add(e);
        LOGGER.debug("LOGICALANDEXPRESSION: " + e);

    }

    @Override
    public void enterSubExpression(SelfieParser.SubExpressionContext ctx) {
    //    LOGGER.debug("enterSubExpression: " + ctx.getText());
    }

    @Override
    public void exitSubExpression(SelfieParser.SubExpressionContext ctx) {
    //    LOGGER.debug("exitSubExpression: " + ctx.getText());
    }

    @Override
    public void enterSimpleExpression(SelfieParser.SimpleExpressionContext ctx) {
    //    LOGGER.debug("enterSimpleExpression: " + ctx.getText());
    }

    @Override
    public void exitSimpleExpression(SelfieParser.SimpleExpressionContext ctx) {
    //    LOGGER.debug("exitSimpleExpression: " + ctx.getText());
    }

    @Override
    public void enterStringExpression(SelfieParser.StringExpressionContext ctx) {
    //    LOGGER.debug("enterStringExpresion: " + ctx.getText());
    }


    @Override public void exitStringExpression(SelfieParser.StringExpressionContext ctx) {
        ExpressionBuilder eb = expression(getProperty(ctx.IDENTIFIER().getText()));
        Expression e;

        if (ctx.stringValue() != null) {
            String sv = ctx.stringValue().getText().substring(1, ctx.stringValue().getText().length() - 1);
            if (ctx.stringOperator().EQ() != null) {
                e = eb.equal(sv);
            } else if (ctx.stringOperator().NE() != null) {
                e = eb.notEqual(sv);
            } else if (ctx.stringOperator().LIKE() != null) {
                e = eb.like(sv);
            } else if (ctx.stringOperator().NOTLIKE() != null) {
                e = eb.notLike(sv);
            } else {
                throw new IllegalArgumentException("Unknown string operator: " + ctx.stringOperator().getText());
            }
        } else if (ctx.stringListValue() != null) {
            List<String> values = Lists.newArrayList();
            for (SelfieParser.StringValueContext v : ctx.stringListValue().stringValue()) {
                values.add(v.getText().substring(1, v.getText().length() - 1));
            }

            if (ctx.listOperator().IN() != null) {
                e = eb.in(values);
            } else if (ctx.listOperator().NOTIN() != null) {
                e = eb.notIn(values);
            } else {
                throw new IllegalArgumentException("Unknown string list operator: " + ctx.listOperator().getText());
            }

        } else {
            throw new IllegalArgumentException("Invalid string expression: " + ctx.getText());
        }
        LOGGER.debug("--- String expression: " + e);
        expressionStack.peek().add(e);
    }

    @Override
    public void enterDecimalExpression(SelfieParser.DecimalExpressionContext ctx) {
    //    LOGGER.debug("enterDecimalExpression: " + ctx.getText());
    }

    @Override
    @SuppressWarnings("checkstyle:executablestatementcount")
    public void exitDecimalExpression(SelfieParser.DecimalExpressionContext ctx) {
        ExpressionBuilder eb = expression(getProperty(ctx.IDENTIFIER().getText()));
        Expression e;

        if (ctx.decimalValue() != null) {
            BigDecimal dv = new BigDecimal(ctx.decimalValue().getText());
            if (ctx.decimalOperator().EQ() != null) {
                e = eb.equal(dv);
            } else if (ctx.decimalOperator().NE() != null) {
                e = eb.notEqual(dv);
            } else if (ctx.decimalOperator().GT() != null) {
                e = eb.greater(dv);
            } else if (ctx.decimalOperator().LT() != null) {
                e = eb.less(dv);
            } else if (ctx.decimalOperator().GE() != null) {
                e = eb.greaterOrEqual(dv);
            } else if (ctx.decimalOperator().LE() != null) {
                e = eb.lessOrEqual(dv);
            } else {
                throw new IllegalArgumentException("Unknown decimal operator: " + ctx.decimalOperator().getText());
            }
        } else if (ctx.decimalListValue() != null) {
            List<BigDecimal> values = Lists.newArrayList();
            for (SelfieParser.DecimalValueContext v : ctx.decimalListValue().decimalValue()) {
                values.add(new BigDecimal(v.getText()));
            }

            if (ctx.listOperator().IN() != null) {
                e = eb.in(values);
            } else if (ctx.listOperator().NOTIN() != null) {
                e = eb.notIn(values);
            } else {
                throw new IllegalArgumentException("Unknown decimal list operator: " + ctx.listOperator().getText());
            }

        } else {
            throw new IllegalArgumentException("Invalid decimal expression: " + ctx.getText());
        }
        LOGGER.debug("--- Decimal expression: " + e);
        expressionStack.peek().add(e);
    }

    @Override
    public void enterLogicalExpression(SelfieParser.LogicalExpressionContext ctx) {
    //    LOGGER.debug("enterLogicalExpression: " + ctx.getText());
    }

    @Override
    public void exitLogicalExpression(SelfieParser.LogicalExpressionContext ctx) {
        ExpressionBuilder eb = expression(getProperty(ctx.IDENTIFIER().getText()));
        Expression e;

        if (ctx.logicalValue() != null) {
            Boolean lv = false;
            if (ctx.logicalValue().TRUE() != null) {
                lv = true;
            }
            if (ctx.logicalOperator().EQ() != null) {
                e = eb.equal(lv);
            } else if (ctx.logicalOperator().NE() != null) {
                e = eb.notEqual(lv);
            } else {
                throw new IllegalArgumentException("Unknown logical operator: " + ctx.logicalOperator().getText());
            }
        } else {
            throw new IllegalArgumentException("Invalid logical expression: " + ctx.getText());
        }
        LOGGER.debug("--- Logical expression: " + e);
        expressionStack.peek().add(e);
    }

    @Override
    public void enterNullExpression(SelfieParser.NullExpressionContext ctx) {
    //    LOGGER.debug("enterNullExpression: " + ctx.getText());
    }

    @Override
    public void exitNullExpression(SelfieParser.NullExpressionContext ctx) {
        ExpressionBuilder eb = expression(getProperty(ctx.IDENTIFIER().getText()));
        Expression e;
        if (ctx.EQ() != null) {
            e = eb.equal(null);
        } else if (ctx.NE() != null) {
            e = eb.notEqual(null);
        } else {
            throw new IllegalArgumentException("Unknown null operator: " + ctx.getText());
        }
        LOGGER.debug("--- Null expression: " + e);
    }


    -/-*
     * Private helper methods.
     -*-/
    private Property getProperty(String propName) {
        return new Property((Strings.isNullOrEmpty(attributePath) ? "" : attributePath + DOT) + propName);
    }

    private Expression chainFilter(List<Expression> expressions, boolean logicalOr) {
        if (expressions.size() > 0) {
            Expression r = expressions.get(0);
            for (int idx = 1; idx < expressions.size(); idx++) {
                if (logicalOr) {
                    r = Expressions.or(r, expressions.get(idx));
                } else {
                    r = Expressions.and(r, expressions.get(idx));
                }
            }
            return r;
        } else {
            return null;
        }
    }
    */
}
