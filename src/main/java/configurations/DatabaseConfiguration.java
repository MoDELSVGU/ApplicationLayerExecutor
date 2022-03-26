package configurations;

import java.util.Map;

public abstract class DatabaseConfiguration extends Configuration {
	private String dbusername, dbpassword;

	private static final String ENV_DB_USERNAME = "DBUSER";
	private static final String ENV_DB_PASSWORD = "DBPSW";

	public String getDbusername() {
		return dbusername;
	}

	public void setDbusername(String dbusername) {
		this.dbusername = dbusername;
	}

	public String getDbpassword() {
		return dbpassword;
	}

	public void setDbpassword(String dbpassword) {
		this.dbpassword = dbpassword;
	}

	public DatabaseConfiguration() {
		super();
		final Map<String, String> env = System.getenv();

		final String sDbUsername = env.get(ENV_DB_USERNAME);
		if (sDbUsername != null) {
			setDbusername(sDbUsername);
		}

		final String sDbPassword = env.get(ENV_DB_PASSWORD);
		if (sDbPassword != null) {
			setDbpassword(sDbPassword);
		}
	}
}
