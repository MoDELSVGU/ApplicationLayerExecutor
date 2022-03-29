package solutions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import configurations.Configuration;
import configurations.TaskConfiguration;
import connections.MySQLConnection;
import exceptions.UnauthorizedAccessException;

public class MySQLSolution extends Solution {

	private final static String METRIC_EXECUTION_TIME = "ExecutionTimeSecs";

	@Override
	public void run(Configuration c) {
		if (c instanceof TaskConfiguration) {
			TaskConfiguration tc = (TaskConfiguration) c;
			if (tc.getsTask().equals("4")) {
				final long nanosExecutionStart = System.nanoTime();
				runQuery4(tc);
				final long nanosExecutionEnd = System.nanoTime();
				final double timeInSecs = ((double) nanosExecutionEnd - nanosExecutionStart) / 1_000_000_000;
				printMetric(tc, METRIC_EXECUTION_TIME, timeInSecs);
			}

			else if (tc.getsTask().equals("5")) {
				final long nanosExecutionStart = System.nanoTime();
				runQuery5(tc);
				final long nanosExecutionEnd = System.nanoTime();
				final double timeInSecs = ((double) nanosExecutionEnd - nanosExecutionStart) / 1_000_000_000;
				printMetric(tc, METRIC_EXECUTION_TIME, timeInSecs);
			}

			else if (tc.getsTask().equals("6")) {
				final long nanosExecutionStart = System.nanoTime();
				runQuery6(tc);
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

	private void runQuery6(TaskConfiguration tc) {
		final String query6 = "SELECT age AS res FROM Student JOIN (SELECT * FROM Enrollment WHERE lecturers = ?) AS my_employments ON my_employments.students = Student_id";
		Connection conn = MySQLConnection.getConnection(tc.getsScenario(), tc.getDbusername(), tc.getDbpassword());

		try {
			if (!tc.getsRole().equals("Lecturer")) {
				throw new UnauthorizedAccessException("Unauthorized Access!");
			}
		} catch (UnauthorizedAccessException e) {
			e.printStackTrace();
		}

		PreparedStatement st2;
		try {
			st2 = conn.prepareStatement(query6);
			st2.setString(1, tc.getsCaller());

			ResultSet rs = st2.executeQuery();
			
			while (rs.next()) {
				rs.getInt("res");
			}
			
			st2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void runQuery5(TaskConfiguration tc) {
		final String query5 = "SELECT COUNT(*) AS res FROM Enrollment";
		final String authCheck = "SELECT (SELECT COUNT(*) FROM Student) = (SELECT COUNT(*) FROM Enrollment WHERE lecturers = ?) as res";
		Connection conn = MySQLConnection.getConnection(tc.getsScenario(), tc.getDbusername(), tc.getDbpassword());
		PreparedStatement st;

		try {
			st = conn.prepareStatement(authCheck);
			st.setString(1, tc.getsCaller());

			ResultSet rs = st.executeQuery();
			
			Boolean res = null;

			while (rs.next()) {
				res = rs.getBoolean("res");
				if (!res || !tc.getsRole().equals("Lecturer")) {
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

			ResultSet rs = st2.executeQuery(query5);

			while (rs.next()) {
				rs.getInt("res");
			}
			
			

			st2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	private void runQuery4(TaskConfiguration tc) {
		final String query4 = "SELECT COUNT(*) AS res FROM Student WHERE age > 18";
		final String authCheck = "SELECT (SELECT MAX(age) FROM Lecturer) = (SELECT age FROM Lecturer WHERE Lecturer_id = ?) as res";
		Connection conn = MySQLConnection.getConnection(tc.getsScenario(), tc.getDbusername(), tc.getDbpassword());
		PreparedStatement st;

		try {
			st = conn.prepareStatement(authCheck);
			st.setString(1, tc.getsCaller());

			ResultSet rs = st.executeQuery();
			
			Boolean res = null;

			while (rs.next()) {
				res = rs.getBoolean("res");
				if (!res || !tc.getsRole().equals("Lecturer")) {
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
				rs.getInt("res");
			}

			st2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void printMetric(Configuration c, String metricExecutionTime, Object metricValue) {
		if (c instanceof TaskConfiguration) {
			TaskConfiguration tc = (TaskConfiguration) c;

			System.out.println(String.format("%s;%s;%s;%s;%s;%s", tc.getsTool(), tc.getsScenario(), tc.getsTask(), tc.getRunIndex(),
					metricExecutionTime, metricValue.toString()));
		}

	}

}
