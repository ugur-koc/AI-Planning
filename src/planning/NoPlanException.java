package planning;

@SuppressWarnings("serial")
public class NoPlanException extends Exception {

	
	public NoPlanException(String string) {
		System.err.println(string);
	}

}
