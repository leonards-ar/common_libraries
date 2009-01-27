package leonards.common.sql;

/**
 * @author Mariano Capurro (28/10/2002)
 *
 * This class is the abstraction of a query result set column
 */
class DBResultSetColumn {

	private int idx = -1;
	private String name = null;
	
	/**
	 * Constructor for DBResultSetColumn.
	 */
	private DBResultSetColumn() {
		super();
	}
	
	/**
	 * Constructor for DBResultSetColumn.
	 * @param idx
	 * @param name
	 */
	public DBResultSetColumn(int idx, String name) {
		this();
		setIdx(idx);
		setName(name);

	}
	
	/**
	 * Returns the idx.
	 * @return int
	 */
	public int getIdx() {
		return idx;
	}

	/**
	 * Returns the name.
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the idx.
	 * @param idx The idx to set
	 */
	public void setIdx(int idx) {
		this.idx = idx;
	}

	/**
	 * Sets the name.
	 * @param name The name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}