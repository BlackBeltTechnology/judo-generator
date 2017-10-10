package hu.blackbelt.judo.generator.egl.formatter;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.epsilon.egl.formatter.Formatter;


public class CompositeFormatter implements Formatter {

	private final Collection<Formatter> formatters;
	
	public CompositeFormatter(Formatter... formatters) {
		this(Arrays.asList(formatters));
	}
	
	public CompositeFormatter(Collection<Formatter> formatters) {
		this.formatters = new LinkedList<Formatter>(formatters);
	}
	
	@Override
	public String format(String text) {
		String current = text;
		
		for (Formatter formatter : formatters) {
			current = formatter.format(current);
		}
		
		return current;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CompositeFormatter))
			return false;
		
		final CompositeFormatter other = (CompositeFormatter)obj;
		
		return this.formatters.equals(other.formatters);
	}
	
	@Override
	public int hashCode() {
		return formatters.hashCode();
	}
}
