package hu.blackbelt.judo.generator.parser.derived.expression.sql;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

import java.util.BitSet;

@SuppressWarnings("checkstyle:parameternumber")
public class DerivedAttributeExpressionParserErrorListener implements ANTLRErrorListener {

    private boolean fail;

    private String message;

    private final String sentence;


    public DerivedAttributeExpressionParserErrorListener(String sentence) {
        this.sentence = sentence;
    }

    public boolean isFail() {
        return fail;
    }

    public String getMessage() {
        return message;
    }

    public void setFail(boolean pFail, String pMessage) {
        this.fail = pFail;
        this.message = pMessage;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol,
                            int line,
                            int charPositionInLine,
                            String msg,
                            RecognitionException e) {
        setFail(true, String.format("Syntax error - Line: %d Col: %d \nMessage: %s \nQuery: %s", line, charPositionInLine, msg, sentence));
    }

    @Override
    public void reportAmbiguity(Parser recognizer,
                                DFA dfa,
                                int startIndex,
                                int stopIndex,
                                boolean exact,
                                BitSet ambigAlts,
                                ATNConfigSet configs) {
        setFail(true, String.format("Ambiguity error \nStart: %d Stop: %d Exact %d \nAmbigous alternatives: %s \nDFA: %s \nQuery: %s",
                startIndex, stopIndex, exact, ambigAlts, dfa, sentence));

    }

    @Override
    public void reportAttemptingFullContext(Parser recognizer,
                                            DFA dfa,
                                            int startIndex,
                                            int stopIndex,
                                            BitSet conflictingAlts,
                                            ATNConfigSet configs) {
        setFail(true, String.format("Attempting full context error. \nStart: %d Stop: %d \nConflicting alternatives: %s \nDFA: %s \nQuery: %s",
                startIndex, stopIndex, conflictingAlts, dfa, sentence));
    }

    @Override
    public void reportContextSensitivity(Parser recognizer,
                                         DFA dfa,
                                         int startIndex,
                                         int stopIndex,
                                         int prediction,
                                         ATNConfigSet configs) {
        setFail(true, String.format("Context sensitivity error. \nStart: %d Stop: %d Predicition: %d \nDFA: %s \nQuery: %s",
                startIndex, stopIndex, prediction, dfa, sentence));
    }

}
