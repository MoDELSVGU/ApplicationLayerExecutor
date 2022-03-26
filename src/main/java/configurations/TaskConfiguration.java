package configurations;

import java.util.Map;

public class TaskConfiguration extends DatabaseConfiguration {
	private String sScenario, sTask, sCaller, sRole;

	private static final String ENV_TASK = "TASK";
	private static final String ENV_SCENARIO = "SCENARIO";
	private static final String ENV_CALLER = "CALLER";
	private static final String ENV_ROLE = "ROLE";

	public String getsScenario() {
		return sScenario;
	}

	public void setsScenario(String sScenario) {
		this.sScenario = sScenario;
	}

	public String getsTask() {
		return sTask;
	}

	public void setsTask(String sTask) {
		this.sTask = sTask;
	}

	public TaskConfiguration() {
		super();
		final Map<String, String> env = System.getenv();

		final String sTask = env.get(ENV_TASK);
		if (sTask != null) {
			setsTask(sTask);
		}

		final String sScenario = env.get(ENV_SCENARIO);
		if (sScenario != null) {
			setsScenario(sScenario);
		}
		
		final String sCaller = env.get(ENV_CALLER);
		if (sCaller != null) {
			setsCaller(sCaller);
		}
		
		final String sRole = env.get(ENV_ROLE);
		if (sRole != null) {
			setsRole(sRole);
		}

	}

	public String getsCaller() {
		return sCaller;
	}

	public void setsCaller(String sCaller) {
		this.sCaller = sCaller;
	}

	public String getsRole() {
		return sRole;
	}

	public void setsRole(String sRole) {
		this.sRole = sRole;
	}

}
