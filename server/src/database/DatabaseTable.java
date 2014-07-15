package database;

/**
 * A pseudo-ORM. More flexible than an ORM, 
 * reduces potential persistence issues.
 */
public interface DatabaseTable {
	
	/**
	 * Returns a SQL statement that creates the
	 * table and its columns. This value can
	 * be passed directly to {@link java.sql.Statement}.
	 * @return SQL CREATE TABLE statement.
	 */
	public String getSchema();

}
