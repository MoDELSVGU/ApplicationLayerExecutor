package launchers;

import configurations.Configuration;
import configurations.TaskConfiguration;
import solutions.MySQLSolution;

public class MySQLLauncher {

	public static void main(String[] args) {
		Configuration c = new TaskConfiguration();
		new MySQLSolution().run(c);
	}
	
}
