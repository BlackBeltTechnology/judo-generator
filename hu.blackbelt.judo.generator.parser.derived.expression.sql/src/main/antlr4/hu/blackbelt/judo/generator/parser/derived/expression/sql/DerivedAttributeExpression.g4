grammar DerivedAttributeExpression;

/* ============= */
/* Grammar rules */
/* ============= */
parse
 : formulas EOF
 ;

formulas
  : formula (formula)*
  ;

formula
  : LQRLYBRACKET? selfEntity (DOT relationChain) RQRLYBRACKET? #expressionFormula
  | STRING                                                   #stringFormula
  ;

selfEntity
  : SELF
  ;

relationChain
  : relation (DOT relation)*
  ;


relation
  : IDENTIFIER
  ;

/* ============= */
/* Lexical rules */
/* ============= */
SELF : S E L F ;

TRUE : 'true' | 'True' | 'TRUE' | 'yes' | 'Yes' | 'YES' | '1' ;
FALSE : 'false' | 'False' | 'FALSE' | 'no' | 'No' | 'NO' | '0' ;

NULL : N U L L ;

NOTLIKE : N O T ' '  L I K E;
NOTIN : N O T ' ' I N ;

EQ : '=' | '==' | E Q ;
GT : '>' | G T;
GE : '>=' | G E ;
LT : '<'  | L T ;
LE : '<=' | L E ;
NE : '!=' | '<>' | N E ;
LIKE : L I K E;
IN : I N ;


AND : A N D | '&&' ;
OR  : O R  | '||' ;
NOT : N O T | '!' ;

COMMA : ',' ;
DOT : '.' ;
COLON : ':' ;

LPAREN : '(' ;
RPAREN : ')' ;

LSQBRACKET : '[' ;
RSQBRACKET : ']' ;

LQRLYBRACKET : '{' ;
RQRLYBRACKET : '}' ;


DECIMAL : '-'?[0-9]+('.'[0-9]+)? ;
STRING :  '\'' ( ~'\'' | '\'\'' )* '\'' ;

IDENTIFIER : [a-zA-Z_][a-zA-Z_0-9]* ;

fragment A: [aA];
fragment B: [bB];
fragment C: [cC];
fragment D: [dD];
fragment E: [eE];
fragment F: [fF];
fragment G: [gG];
fragment H: [hH];
fragment I: [iI];
fragment J: [jJ];
fragment K: [kK];
fragment L: [lL];
fragment M: [mM];
fragment N: [nN];
fragment O: [oO];
fragment P: [pP];
fragment Q: [qQ];
fragment R: [rR];
fragment S: [sS];
fragment T: [tT];
fragment U: [uU];
fragment V: [vV];
fragment W: [wW];
fragment X: [xX];
fragment Y: [yY];
fragment Z: [zZ];

WS  :   [ \t\n\r]+ -> skip ;
