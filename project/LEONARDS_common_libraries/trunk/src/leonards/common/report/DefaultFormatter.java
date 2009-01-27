/*
 * Created on Aug 12, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package leonards.common.report;

/**
 * @author Mariano
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DefaultFormatter implements ColumnFormatter {

	/**
	 * 
	 */
	public DefaultFormatter() {
		super();
	}

	/* (non-Javadoc)
	 * @see leonards.common.report.ColumnFormatter#format(java.lang.Object)
	 */
	public String format(Object obj) {
		return obj != null ? obj.toString() : null;
	}

}
