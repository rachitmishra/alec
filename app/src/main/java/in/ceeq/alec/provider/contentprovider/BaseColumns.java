package in.ceeq.alec.provider.contentprovider;

public class BaseColumns {

	public static final String _ID = "_id";

	public static final String CREATE_TABLE = "create table ";

	public static final String COMMA = ", ";

	private static final String DROP_TABLE = "drop table if exists ";

	protected static final String INTEGER = " integer ";

	protected static final String REAL = " real ";

	protected static final String DEFAULT = " default ";

	protected static final String TEXT = " text ";

	protected static final String NOT_NULL = "not null ";

	private static final String PRIMARY_KEY = "primary key ";

	private static final String AUTO_INCREMENT = "autoincrement ";

	protected static final String UNIQUE = "unique ";

	protected static final String BRACE_OPEN = " (";

	protected static final String BRACE_CLOSE = " )";

	protected static final String BRACE_CLOSE_SC = " );";

	public static String create(final String mTableName, final String columns) {
		return new StringBuilder(CREATE_TABLE)
				.append(mTableName)
				.append(BRACE_OPEN)
				.append(_ID).append(INTEGER).append(PRIMARY_KEY).append(AUTO_INCREMENT)
				.append(COMMA)
				.append(columns)
				.append(BRACE_CLOSE)
				.toString();
	}

	public static String create(final String mTableName, final String columns, final String unique) {
		return new StringBuilder(CREATE_TABLE)
				.append(mTableName)
				.append(BRACE_OPEN)
				.append(_ID).append(INTEGER).append(PRIMARY_KEY).append(AUTO_INCREMENT)
				.append(COMMA)
				.append(columns)
				.append(COMMA)
				.append(unique)
				.append(BRACE_CLOSE_SC)
				.toString();
	}

	public static String drop(final String mTableName) {
		return new StringBuilder(DROP_TABLE).append(mTableName).toString();
	}
}
