package hu.blackbelt.judo.generator.formatter.sql;

import org.eclipse.epsilon.egl.formatter.Formatter;
import org.hibernate.engine.jdbc.internal.DDLFormatterImpl;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SqlCreateTableFormatter implements Formatter {
    @Override
    public String format(String text) {
        String formatted = Stream.of(text.split(";"))
                .map(s ->
                        new String(s)
                                .replaceAll("  ", " ")
                                .replaceAll("  ", " ")
                                .replaceAll("\n", "")
                                .replaceAll("\t", "")
                                .trim())
                .map(s -> new DDLFormatterImpl().format(s))
                .collect(Collectors.joining(";\n"));

        return formatted;
    }
}