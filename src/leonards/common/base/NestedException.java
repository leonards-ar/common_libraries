/*
 * Created on 26/04/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package leonards.common.base;

/**
 * @author Mariano
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NestedException extends Exception {

	private static final long serialVersionUID = -1371276280062167708L;
	
	private Throwable nestedException = null;
	private int code = -1;
	
	/**
	 * 
	 */
	public NestedException() {
		this(null, null);
	}

	/**
	 * @param message
	 */
	public NestedException(String message) {
		this(message, null);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NestedException(String message, Throwable cause) {
		super(message);
		setNestedException(cause);
	}

	/**
	 * @param code
	 * @param message
	 * @param cause
	 */
	public NestedException(int code, String message, Throwable cause) {
		this(message, cause);
		setCode(code);
	}

	/**
	 * @param code
	 * @param cause
	 */
	public NestedException(int code, Throwable cause) {
		this(code, null, cause);
	}

	/**
	 * @param code
	 * @param message
	 */
	public NestedException(int code, String message) {
		this(code, message, null);
	}
	
	/**
	 * @param cause
	 */
	public NestedException(Throwable cause) {
		this(null, cause);
	}

	/**
	 * @return
	 */
	public Throwable getNestedException() {
		return nestedException;
	}

	/**
	 * @param throwable
	 */
	public void setNestedException(Throwable throwable) {
		nestedException = throwable;
	}

	/**
	 * @return
	 */
	public String getNestedMessage() {
		StringBuffer msg = new StringBuffer();
		
		msg.append(this.getMessage());
		
		if( getNestedException() != null ) {
			msg.append( CommonUtils.getNewLine() + "Cause: " + CommonUtils.getNewLine() );
			if( getNestedException() != null && getNestedException() instanceof NestedException ) {
				msg.append( ((NestedException)getNestedException()).getNestedMessage() );
			} else if( getNestedException() != null ) {
				msg.append(getNestedException().getMessage());
			}
		}
		
		return msg.toString();
	}

	/**
	 * @return
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param i
	 */
	public void setCode(int i) {
		code = i;
	}

}
