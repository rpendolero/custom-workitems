/**
 * 
 */
package es.bde.aps.jbs.eaijava.exception;

/**
 * @author qinrpbx
 *
 */
public class EAIJavaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public EAIJavaException(String message) {

		super(message);
	}

	/**
	 * @param e
	 */
	public EAIJavaException(Throwable e) {
		super(e);
	}

	/**
	 * @param string
	 * @param e
	 */
	public EAIJavaException(String message, Exception e) {

		super(message, e);
	}
}
