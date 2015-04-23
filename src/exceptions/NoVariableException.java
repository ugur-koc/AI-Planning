package exceptions;

@SuppressWarnings("serial")
public class NoVariableException extends Exception {

	public NoVariableException(String string) {
		System.err.println(string);
	}

}
