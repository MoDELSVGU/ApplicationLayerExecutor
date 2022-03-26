package solutions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import configurations.Configuration;
import configurations.TaskConfiguration;
import connections.MySQLConnection;
import exceptions.UnauthorizedAccessException;
import models.Student;

public class MySQLSolution extends Solution {

	private final static String METRIC_EXECUTION_TIME = "ExecutionTimeSecs";

	@Override
	public void run(Configuration c) {
		if (c instanceof TaskConfiguration) {
			TaskConfiguration tc = (TaskConfiguration) c;
			if (tc.getsTask() == "4") {
				final long nanosExecutionStart = System.nanoTime();
				runQuery4(tc);
				final long nanosExecutionEnd = System.nanoTime();
				final double timeInSecs = ((double) nanosExecutionEnd - nanosExecutionStart) / 1_000_000_000;
				printMetric(tc, METRIC_EXECUTION_TIME, timeInSecs);
			}

			else if (tc.getsTask() == "5") {
				final long nanosExecutionStart = System.nanoTime();
				runQuery4(tc);
				final long nanosExecutionEnd = System.nanoTime();
				final double timeInSecs = ((double) nanosExecutionEnd - nanosExecutionStart) / 1_000_000_000;
				printMetric(tc, METRIC_EXECUTION_TIME, timeInSecs);
			}

			else if (tc.getsTask() == "6") {
				final long nanosExecutionStart = System.nanoTime();
				runQuery4(tc);
				final long nanosExecutionEnd = System.nanoTime();
				final double timeInSecs = ((double) nanosExecutionEnd - nanosExecutionStart) / 1_000_000_000;
				printMetric(tc, METRIC_EXECUTION_TIME, timeInSecs);
			}

			else {
			}

//			Connection conn = MySQLConnection.getConnection(qc.getsScenario(), qc.getDbusername(), qc.getDbpassword());
//			Statement st;
//			try {
//				st = conn.createStatement();
//				final long nanosExecutionStart = System.nanoTime();
//				st.executeQuery(qc.getsQuery());
//				final long nanosExecutionEnd = System.nanoTime();
//				final double timeInSecs = ((double) nanosExecutionEnd - nanosExecutionStart) / 1_000_000_000;
//				printMetric(qc, METRIC_EXECUTION_TIME, timeInSecs);
//			} catch (SQLException e) {
//				e.printStackTrace();
		}
	}

	private void runQuery4(TaskConfiguration tc) {
		final String query4 = "SELECT COUNT(*) FROM Student WHERE age > 18";
		final String authCheck = "SELECT (SELECT MAX(age) FROM Lecturer) = (SELECT age FROM Lecturer WHERE Lecturer_id = ?) as res";
		Connection conn = MySQLConnection.getConnection(tc.getsScenario(), tc.getDbusername(), tc.getDbpassword());
		PreparedStatement st;
		List<Student> students = new ArrayList<Student>();
		
		try {
			st = conn.prepareStatement(authCheck);
			st.setString(0, tc.getsCaller());
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				boolean res = rs.getBoolean("res");
				if (!res) {
					throw new UnauthorizedAccessException("Unauthorized Access!");
				}
			}
			
			st.close();
		} catch (SQLException | UnauthorizedAccessException e) {
			e.printStackTrace();
		}
		
		Statement st2;
		try {
			st2 = conn.createStatement();
			
			ResultSet rs = st2.executeQuery(query4);

			while (rs.next()) {
				int total = rs.getInt(0);
			}
			
			st2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private void printMetric(Configuration c, String metricExecutionTime, Object metricValue) {
		if (c instanceof TaskConfiguration) {
			TaskConfiguration tc = (TaskConfiguration) c;

			System.out.println(String.format("%s;%s;%s;%s;%s", tc.getsTool(), tc.getsTask(), tc.getRunIndex(),
					metricExecutionTime, metricValue.toString()));

		}

	}

}
