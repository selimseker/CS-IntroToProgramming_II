import java.util.ArrayList;
import java.util.Arrays;

public class Waiter {
	
	private String name;
	private int salary;
	ArrayList<Integer> table_ids = new ArrayList<Integer>();
	private int number_of_orders;
	
	
	public void cancel_a_table(int tableid) {
		ArrayList<Integer> id_list = getTable_ids();
		int removing_index = -1;
		for (int i = 0; i < id_list.size(); i++) {
			if( id_list.get(i) == tableid) {
				removing_index = i;
				break;
			}
		}
		id_list.remove(removing_index);
		setTable_ids(id_list);
	}
	
	
	public Waiter(String name, int salary) {
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
	public int getNumber_of_orders() {
		return number_of_orders;
	}
	public void setNumber_of_orders(int number_of_orders) {
		this.number_of_orders = number_of_orders;
	}
	public ArrayList<Integer> getTable_ids() {
		return table_ids;
	}
	public void setTable_ids(ArrayList<Integer> table_ids) {
		this.table_ids = table_ids;
	}
}
