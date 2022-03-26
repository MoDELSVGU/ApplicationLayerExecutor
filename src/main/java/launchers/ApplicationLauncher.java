package launchers;

import configurations.Configuration;
import configurations.TaskConfiguration;
import solutions.MySQLSolution;

public class ApplicationLauncher {

	public static void main(String[] args) {
		Configuration c = new TaskConfiguration();
		new MySQLSolution().run(c);
	}
	
}
