public class Employer {
	
	private static final int ALLOWED_MAX_TABLES = 2;
	
	private String name;
	private int salary;
	private int created_table_ids[] = new int[ALLOWED_MAX_TABLES];
	private int num_of_created_tables = 0;
	
	
	public Employer(String name, int salary) {
		this.name = name;
		this.salary = salary;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public int[] getCreated_table_ids() {
		return created_table_ids;
	}
	public void setCreated_table_ids(int[] created_table_ids) {
		this.created_table_ids = created_table_ids;
	}
	public int getNum_of_created_tables() {
		return num_of_created_tables;
	}
	public void setNum_of_created_tables(int num_of_created_tables) {
		this.num_of_created_tables = num_of_created_tables;
	}
}
