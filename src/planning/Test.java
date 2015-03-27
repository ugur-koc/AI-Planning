package planning;

public class Test {

	public static void main(String[] args) throws Exception {

		Problem problem = Helper.parseInputFile("sample_csv_input.txt");
		Plan plan = Planner.solve(problem, "AStar");
		System.out.println(plan.toString());
	}
}