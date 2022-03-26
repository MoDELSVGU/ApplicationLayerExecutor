package configurations;

import java.util.Map;

public abstract class Configuration {
	private int runIndex;
	private String sTool;

	private static final String ENV_RUN_INDEX = "RUNINDEX";
	private static final String ENV_TOOL = "TOOL";

	public int getRunIndex() {
		return runIndex;
	}

	public void setRunIndex(int runIndex) {
		this.runIndex = runIndex;
	}

	public Configuration() {
		final Map<String, String> env = System.getenv();

		final String sRunIndex = env.get(ENV_RUN_INDEX);
		if (sRunIndex != null) {
			setRunIndex(Integer.parseInt(sRunIndex));
		}
		
		final String sTool = env.get(ENV_TOOL);
		if (sTool != null) {
			setsTool(sTool);
		}

	}

	public String getsTool() {
		return sTool;
	}

	public void setsTool(String sTool) {
		this.sTool = sTool;
	}
}
