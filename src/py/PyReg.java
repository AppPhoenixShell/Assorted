package py;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PyReg {
	private String regex;
	private Pattern pattern;
	
	public PyReg(String regex) {
		this.regex = regex;
		this.pattern = Pattern.compile(regex);
	}
	public boolean matches(String input) {
		return pattern.matcher(input).matches();
	}
	public Matcher matcher(String input) {
		return pattern.matcher(input);
	}
}
