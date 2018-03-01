/**
 * 
 */
package es.bde.aps.jbs.workitem.exception;

/**
 * @author qinrpbx
 *
 */
public class JBSException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public JBSException(String message) {

		super(message);
	}

	/**
	 * @param e
	 */
	public JBSException(Throwable e) {
		super(e);
	}

	/**
	 * @param string
	 * @param e
	 */
	public JBSException(String message, Exception e) {

		super(message, e);
	}
}
