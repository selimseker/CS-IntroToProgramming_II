import java.util.ArrayList;

public class Table {
	

	private int table_id, capacity;
	private boolean free = true;
	private int total_order_count = 0;
	private String created_employer, opareting_waiter;
	private ArrayList<String> orders = new ArrayList<String>();
	private ArrayList<Integer> item_and_order_nums = new ArrayList<Integer>();
	
	
	
	public void resetTable() {
		setFree(true);
		setOpareting_waiter(null);
		setTotal_order_count(0);
		orders.clear();
		item_and_order_nums.clear();
	}
	
	public Table(int table_id, int capacity, boolean free, String created_employer) {
		this.table_id = table_id;
		this.capacity = capacity;
		this.free = free;
		this.created_employer = created_employer;
	}
	
	public int getTable_id() {
		return table_id;
	}

	public void setTable_id(int table_id) {
		this.table_id = table_id;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public int getTotal_order_count() {
		return total_order_count;
	}

	public void setTotal_order_count(int total_order_count) {
		this.total_order_count = total_order_count;
	}

	public String getCreated_employer() {
		return created_employer;
	}

	public void setCreated_employer(String created_employer) {
		this.created_employer = created_employer;
	}

	public String getOpareting_waiter() {
		return opareting_waiter;
	}

	public void setOpareting_waiter(String opareting_waiter) {
		this.opareting_waiter = opareting_waiter;
	}
	
	public ArrayList<String> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<String> orders) {
		this.orders = orders;
	}

	
	public ArrayList<Integer> getItem_and_order_nums() {
		return item_and_order_nums;
	}

	public void setItem_and_order_nums(ArrayList<Integer> item_and_order_nums) {
		this.item_and_order_nums = item_and_order_nums;
	}

}
