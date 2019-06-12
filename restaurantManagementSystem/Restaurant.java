import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;


public class Restaurant {	
	
	// Limitation constants
	private static final int MAX_TABLES = 5;
	private static final int ALLOWED_MAX_TABLES = 2;
	private static final int MAX_ITEMS = 10;
	private static final int MAX_TABLE_SERVICES = 3;
	private static final int MAX_ORDERS = 5;
	private static final int MAX_WAITER = 5;
	private static final int MAX_EMPLOYER = 5;
	
	
	// Main objects of restaurant
	private Waiter[] waiters = new Waiter[MAX_WAITER];
	private Employer[] employers = new Employer[MAX_EMPLOYER];
	private Table[] tables = new Table[MAX_TABLES];
	private ArrayList<Item> item_list = new ArrayList<Item>();


	private int employer_counter = 0;
	private int waiter_counter = 0;
	private int table_counter = 0;

	

	public int add_item(String line) {
		String name;
		float cost;
		int amount;
		String arguments = line.split(" ")[1];

		if( arguments.split(";").length != 3 ) {
			System.out.println("Invalid command (add_item)");
			return 0;
		}		
		try {
			name = arguments.split(";")[0];
			cost = Float.valueOf(arguments.split(";")[1]);
			amount = Integer.valueOf(arguments.split(";")[2]);
		}catch (IllegalArgumentException e) {
			System.out.println("Illegal argument! (add_item)");
			return 0;
		}
		
		for (int i = 0; i < item_list.size(); i++) {
			if (name.equals(item_list.get(i).getName())) {
				System.out.println(name+" already added to stock! (add_item)");
				return 0;
			}
		}
		
		Item new_item = new Item(name, cost, amount);
		item_list.add(new_item);
		
		return 0;
	}
	
	public int add_waiter(String line) {
		String name;
		int salary;
		String arguments = line.split(" ")[1];
		
		if( arguments.split(";").length != 2 ) {
			System.out.println("Invalid command (add_waiter)");
			return 0;
		}
		try {
			name = arguments.split(";")[0];
			salary = Integer.valueOf(arguments.split(";")[1]);
		}catch (IllegalArgumentException e) {
			System.out.println("Illegal argument! (add_waiter)");
			return 0;
		}
		if(waiter_counter == MAX_WAITER) {
			System.out.println("Not allowed to add waiter max. number of waiter, "+MAX_WAITER);
			return 0;
		}
		for (int i = 0; i < waiter_counter; i++) {
			if (name.equals(waiters[i].getName())) {
				System.out.println("Waiter "+name+" already added");
				return 0;
			}
		}
		
		waiters[waiter_counter] = new Waiter(name, salary);
		waiter_counter++;
		return 0;
	}
	
	public int add_employer(String line) {
		String name;
		int salary;
		String arguments = line.split(" ")[1];
		
		if( arguments.split(";").length != 2 ) {
			System.out.println("Invalid command (add_employer)");
			return 0;
		}
		try {
			name = arguments.split(";")[0];
			salary = Integer.valueOf(arguments.split(";")[1]);
		}catch (IllegalArgumentException e) {
			System.out.println("Illegal argument! (add_employer)");
			return 0;
		}
		if(employer_counter == MAX_EMPLOYER) {
			System.out.println("Not allowed to add employer max. number of employer, "+MAX_EMPLOYER);
			return 0;
		}
		for (int i = 0; i < employer_counter; i++) {
			if (name.equals(employers[i].getName())) {
				System.out.println("Employer "+name+" already added");
				return 0;
			}
		}

		employers[employer_counter] = new Employer(name, salary);
		employer_counter++;
		return 0;
	}
	
	public int create_table(String line) {
		System.out.println("***********************************");
		System.out.println("PROGRESSING COMMAND: create_table");
		String employer_name;
		int capacity;
		
		String arguments = line.split(" ")[1];
		
		// Checking the arguments
		if( arguments.split(";").length != 2 ) {
			System.out.println("Invalid command (create_table)");
			return 0;
		}
		try {
			employer_name = arguments.split(";")[0];
			capacity = Integer.valueOf(arguments.split(";")[1]);
		}catch (IllegalArgumentException e) {
			System.out.println("Illegal argument! (create_table)");
			return 0;
		}
		
		boolean employer_exist = false;
		int employer_id = 0;
		for (int i=0; i < employer_counter; i++) {
			if (employer_name.equals(employers[i].getName())) {
				employer_exist = true;
				employer_id = i;
			}
		}
		if (employer_exist == false) {
			System.out.println("There is no employer named "+employer_name);
			return 0;
		}
		if ( table_counter == MAX_TABLES ) {
			System.out.println("Not allowed to exceed max. number of tables, "+MAX_TABLES);
			return 0;
		}
		
		if (employers[employer_id].getNum_of_created_tables() == ALLOWED_MAX_TABLES) {
			System.out.println(employer_name+" has already created "+ALLOWED_MAX_TABLES+" tables!");
			return 0;
		}

		// Creating a new table object
		tables[table_counter] = new Table(table_counter, capacity, true, employer_name);
		
		int table_ids[] = new int[2];
		table_ids = Arrays.copyOf(employers[employer_id].getCreated_table_ids(), 2);
		table_ids[employers[employer_id].getNum_of_created_tables()] = table_counter;
		
		// Set the table datas to created employer
		employers[employer_id].setCreated_table_ids(table_ids);
		employers[employer_id].setNum_of_created_tables((employers[employer_id].getNum_of_created_tables())+1);
		
		table_counter++;
		
		System.out.println("A new table has successfully been added");
		return 0;
	}
	
	public int new_order(String line) {
		System.out.println("***********************************");
		System.out.println("PROGRESSING COMMAND: new_order");

		String waiter_name;
		int customer_number;
		
		String arguments = line.split(" ")[1];
		
		// Take the parameters
		if( arguments.split(";").length != 3 ) {
			System.out.println("Invalid command (new_order)");
			return 0;
		}
		try {
			waiter_name = arguments.split(";")[0];
			customer_number = Integer.valueOf(arguments.split(";")[1]);
		}catch (IllegalArgumentException e) {
			System.out.println("Illegal argument! (new_order)");
			return 0;
		}
		
		// Check the waiter name
		int operating_waiter = -1;
		for(int i = 0; i < waiter_counter; i++) {
			if(waiter_name.equals(waiters[i].getName()) == true) {
				operating_waiter = i;
				break;
			}
		}
		if (operating_waiter == -1) {
			System.out.println("There is no waiter named "+waiter_name);
			return 0;
		}
		
		// Check waiters dealing table num
		if (waiters[operating_waiter].getTable_ids().size() >= MAX_TABLE_SERVICES) {
			System.out.println("Not allowed to service max. number of tables, "+MAX_TABLE_SERVICES);
			return 0;
		}
		
		// Check for is there a free table and also its capacity
		int operating_table = -1;
		for(int i = 0; i < table_counter; i++) {
			if ( (tables[i].isFree() == true) && (tables[i].getCapacity() >= customer_number) ) {
				operating_table = tables[i].getTable_id();
				break;
			}			
		}
		if ( operating_table == -1 ) {
			System.out.println("There is no appropriate table for this order!");
			return 0;
		}
		
		// Check the MAX_ORDER
		if( tables[operating_table].getTotal_order_count() == MAX_ORDERS) {
			System.out.println("Not allowed to exceed max number of orders!");
			return 0;
		}
		
		// Checking the orders
		ArrayList<String> orders = new ArrayList<String>();
		int no_error = 0;
		no_error = check_and_update_order(arguments.split(";")[2], orders);
		
		if(no_error == 0) {
			return 0;
		}

		// Table and waiter confirmed ready for orders
		System.out.println("Table (= ID "+operating_table+") has been taken into service");
		
		// Check the stock and MAX_ITEMS
		ArrayList<String> confirmed_orders = new ArrayList<String>(check_and_update_stock(orders));
		
		// Set the datas to waiter and table
		ArrayList<Integer> id_list = new ArrayList<Integer>(waiters[operating_waiter].getTable_ids());
		id_list.add(operating_table);
		ArrayList<Integer> item_order_num = new ArrayList<Integer>(tables[operating_table].getItem_and_order_nums());
		item_order_num.add(confirmed_orders.size());
		
		// Add the table id to waiter
		waiters[operating_waiter].setTable_ids(id_list);
		waiters[operating_waiter].setNumber_of_orders(waiters[operating_waiter].getNumber_of_orders()+1);
		
		tables[operating_table].setItem_and_order_nums(item_order_num);
		tables[operating_table].setOpareting_waiter(waiter_name);
		tables[operating_table].setOrders(confirmed_orders);
		tables[operating_table].setFree(false);
		tables[operating_table].setTotal_order_count(tables[operating_table].getTotal_order_count()+1);
		
		return 0;
	}
	
	public int add_order(String line) {
		System.out.println("***********************************");
		System.out.println("PROGRESSING COMMAND: add_order");
		
		String waiter_name;
		int table_id;
		String arguments = line.split(" ")[1];
		
		// Check the arguments
		if( arguments.split(";").length != 3 ) {
			System.out.println("Invalid command (add_order)");
			return 0;
		}
		
		// Take the arguments
		try {
			waiter_name = arguments.split(";")[0];
			table_id = Integer.valueOf(arguments.split(";")[1]);
		}catch (IllegalArgumentException e) {
			System.out.println("Illegal argument! (add_order)");
			return 0;
		}
		
		// Check the waiter name
		int operating_waiter = -1;
		for(int i = 0; i < waiter_counter; i++) {
			if(waiter_name.equals(waiters[i].getName()) == true) {
				operating_waiter = i;
				break;
			}
		}
		if (operating_waiter == -1) {
			System.out.println("There is no waiter named "+waiter_name);
			return 0;
		}

		// Check the table id and compare to waiter's tables
		boolean table_confirmed = false;
		for (int i = 0; i < waiters[operating_waiter].getTable_ids().size(); i++ ) {
			if (table_id == waiters[operating_waiter].getTable_ids().get(i)) {
				table_confirmed = true;
				break;
			}
		}
		if (table_confirmed == false) {
			System.out.println("This table is either not in service now or "+waiter_name+" cannot be assigned this table!");
			return 0;
		}
				
		// Check the MAX_ORDER
		if( tables[table_id].getTotal_order_count() == MAX_ORDERS) {
			System.out.println("Not allowed to exceed max number of orders!");
			return 0;
		}
		
		// Table and waiter confirmed ready for orders
		
		// Checking the orders
		ArrayList<String> orders = new ArrayList<String>();
		int no_error = 0;
		no_error = check_and_update_order(arguments.split(";")[2], orders);
		
		if(no_error == 0) {
			return 0;
		}


		// Check the stock
		ArrayList<String> confirmed_orders = new ArrayList<String>();
		confirmed_orders = check_and_update_stock(orders);
		
		// Set the datas to waiter and table 
		waiters[operating_waiter].setNumber_of_orders(waiters[operating_waiter].getNumber_of_orders()+1);
		ArrayList<String> new_orders = new ArrayList<String>();
		ArrayList<Integer> item_order_num = tables[table_id].getItem_and_order_nums();
		item_order_num.add(confirmed_orders.size());
		new_orders = tables[table_id].getOrders();
		new_orders.addAll(confirmed_orders);
		tables[table_id].setItem_and_order_nums(item_order_num);
		tables[table_id].setOrders(new_orders);
		tables[table_id].setTotal_order_count(tables[table_id].getTotal_order_count()+1);
		
		return 0;
	}

	public int check_out(String line) {
		System.out.println("***********************************");
		System.out.println("PROGRESSING COMMAND: check_out");

		String waiter_name;
		int table_id;
		String arguments = line.split(" ")[1];
		
		// Check the arguments
		if( arguments.split(";").length != 2 ) {
			System.out.println("Invalid command (check_out)");
			return 0;
		}
		try {
			waiter_name = arguments.split(";")[0];
			table_id = Integer.valueOf(arguments.split(";")[1]);
		}catch (IllegalArgumentException e) {
			System.out.println("Illegal argument! (check_out)");
			return 0;
		}
				
		// Check the waiter name
		int operating_waiter = -1;
		for(int i = 0; i < waiter_counter; i++) {
			if(waiter_name.equals(waiters[i].getName()) == true) {
				operating_waiter = i;
				break;
			}
		}
		if (operating_waiter == -1) {
			System.out.println("There is no waiter named "+waiter_name);
			return 0;
		}

		// Check the table id and compare to waiter's tables
		boolean table_confirmed = false;
		for (int i = 0; i < waiters[operating_waiter].getTable_ids().size(); i++ ) {
			if (table_id == waiters[operating_waiter].getTable_ids().get(i)) {
				table_confirmed = true;
				break;
			}
		}
		if (table_confirmed == false) {
			System.out.println("This table is either not in service now or "+waiter_name+" cannot be assigned this table!");
			return 0;
		}
		
		// Table and waiter confirmed Ready to check out
		ArrayList<String> counted_items = new ArrayList<String>();
		float total_cost = 0;
		for (int i = 0; i < tables[table_id].getOrders().size(); i++) {
			String item_name = tables[table_id].getOrders().get(i);
			if(counted_items.contains(item_name) == false) {
				int item_num = 0;
				for(int j = 0; j < tables[table_id].getOrders().size(); j++) {
					if (item_name.equals(tables[table_id].getOrders().get(j))) {
						item_num++;
					}
				}
				counted_items.add(item_name);
				float cost = 0;
				for(int j = 0; j < item_list.size(); j++) {
					if ( item_name.equals(item_list.get(j).getName())) {
						cost = item_list.get(j).getCost();
						break;
					}
				}
				float all_nums_cost = cost * item_num;
				total_cost += all_nums_cost;
				String printformat = String.format(Locale.US, "%s:\t%.3f (x %d) %.3f $", item_name, cost, item_num, all_nums_cost);
				System.out.println(printformat);
			}
		}
		String printformat = String.format(Locale.US, "Total:\t%.3f $", total_cost);
		System.out.println(printformat);
		
		tables[table_id].resetTable();
		waiters[operating_waiter].cancel_a_table(table_id);
		return 0;
	}
	
	public void stock_status() {
		System.out.println("***********************************");
		System.out.println("PROGRESSING COMMAND: stock_status");
		for(int i = 0; i < getItem_list().size(); i++) {
			String name = getItem_list().get(i).getName();
			System.out.printf("%s:\t%s\n", name, getItem_list().get(i).getAmount());
		}
	}

	public void get_employer_salary() {
		System.out.println("***********************************");
		System.out.println("PROGRESSING COMMAND: get_employer_salary");
		int salary, create_num;
		double net_salary;
		String name;
		for (int i = 0; i < employer_counter; i++) {
			name = employers[i].getName()+":";
			salary = employers[i].getSalary();
			create_num = employers[i].getNum_of_created_tables();
			net_salary = salary + (create_num * salary * 0.1);
			String printformat = String.format(Locale.US, "Salary for %s %.1f", name, net_salary);
			System.out.println(printformat);
		}
	}
	
	public void get_waiter_salary() {
		System.out.println("***********************************");
		System.out.println("PROGRESSING COMMAND: get_waiter_salary");
		int salary, order_num;
		double net_salary;
		String name;
		for (int i = 0; i < waiter_counter; i++) {
			name = waiters[i].getName()+":";
			salary = waiters[i].getSalary();
			order_num = waiters[i].getNumber_of_orders();
			net_salary = salary + (order_num * salary * 0.05);
			String printformat = String.format(Locale.US, "Salary for %s %.1f", name, net_salary);
			System.out.println(printformat);
		}
	}
		
	public void get_order_status() {
		System.out.println("***********************************");
		System.out.println("PROGRESSING COMMAND: get_order_status");
		for (int i = 0; i < table_counter; i++) {
			System.out.println("Table: "+i+"\n\t"+tables[i].getTotal_order_count()+" order(s)");
			for (int j = 0; j < tables[i].getTotal_order_count(); j++) {
				System.out.println("\t\t"+tables[i].getItem_and_order_nums().get(j)+" item(s)");
			}
		}
	}
	
	public void get_table_status() {
		System.out.println("***********************************");
		System.out.println("PROGRESSING COMMAND: get_table_status");
		for (int i = 0; i < table_counter; i++) {
			if(getTables()[i].isFree() == true) {
				System.out.println("Table "+i+": Free");
			}else {
				System.out.println("Table "+i+": Reserved ("+getTables()[i].getOpareting_waiter()+")");		
			}
		}
	}
	
	// Helper methods
	private ArrayList<String> check_and_update_stock(ArrayList<String> orders){
		ArrayList<String> confirmed_orders = new ArrayList<String>();
		for (int i = 0; i < orders.size(); i++) {			
			String current_item = orders.get(i);

			boolean item_exist = false;
			for (int j = 0; j < item_list.size(); j++) {
				String stock_item = item_list.get(j).getName();
				
	
				if ( current_item.equals(stock_item) ) {
					item_exist = true;

					if ( item_list.get(j).getAmount() != 0 ) {						
						// Check the MAX_ITEMS
						if(confirmed_orders.size() >= MAX_ITEMS) {
							System.out.println("Not allowed to exceed max. number of items in one order "+MAX_ITEMS);
							return confirmed_orders;
						}
						confirmed_orders.add(current_item);
						
						// update the item amount
						item_list.get(j).setAmount(item_list.get(j).getAmount()-1);
						System.out.println("Item "+current_item+" added into order");
						break;						
					}
					else {
						System.out.println("Sorry! No "+current_item+" in the stock!");
						break;
					}
				}
			}
			//Check the item name
			if(item_exist == false) {
				System.out.println("Unknown item "+current_item);
			}
		}
		return confirmed_orders;
	}
	
	private int check_and_update_order(String order_parameters, ArrayList<String> orders) {
		// Adding the items*num to card if they are exist in the item class
		if( order_parameters.indexOf(':') == -1 ) {
			if ( order_parameters.indexOf('-') == -1 ) {
				System.out.println("Invalid argument in the order(s)");
				return 0;
			}else {
				
				String item_name;
				int order_num;
				
				try {
					item_name = order_parameters.split("-")[0];
					order_num = Integer.valueOf(order_parameters.split("-")[1]);
				}catch (IllegalArgumentException e) {
					System.out.println("Illegal argument!");
					return 0;
				}
				boolean item_exist = false;
				for(int j = 0; j < item_list.size(); j++) {
					if(item_name.equals(item_list.get(j).getName())) {
						item_exist = true;
						break;
					}
				}
				if(item_exist == false) {
					System.out.println("Unknown item "+item_name);
					return 0;
				}				
				for(int j = 0; j < order_num; j++) {
					orders.add(item_name);
				}
			}			
		}
		else {
			for (int i = 0; i < order_parameters.split(":").length; i++) {
				String one_order = order_parameters.split(":")[i];
				if( (one_order.indexOf('-') == -1) || (one_order.split("-").length != 2) ) {
					System.out.println("Invalid argument in the order(s)");
					return 0;
				}	
				String item_name;
				int order_num;
				
				try {
					item_name = one_order.split("-")[0];
					order_num = Integer.valueOf(one_order.split("-")[1]);
				}catch (IllegalArgumentException e) {
					System.out.println("Illegal argument!2");
					return 0;
				}
				for(int j = 0; j < order_num; j++) {
					orders.add(item_name);
				}
			}
		}
		return 1;
	}
	
	
	
	// Getters-setters
	public Waiter[] getWaiters() {
		return waiters;
	}

	public void setWaiters(Waiter[] waiters) {
		this.waiters = waiters;
	}

	public int getWaiter_counter() {
		return waiter_counter;
	}

	public void setWaiter_counter(int waiter_counter) {
		this.waiter_counter = waiter_counter;
	}
	
	public Employer[] getEmployers() {
		return employers;
	}

	public int getEmployer_counter() {
		return employer_counter;
	}

	public void setEmployer_counter(int employer_counter) {
		this.employer_counter = employer_counter;
	}
		
	public void setEmployers(Employer[] employers) {
		this.employers = employers;
	}

	public Table[] getTables() {
		return tables;
	}

	public void setTables(Table[] tables) {
		this.tables = tables;
	}

	public int getTable_counter() {
		return table_counter;
	}

	public void setTable_counter(int table_counter) {
		this.table_counter = table_counter;
	}
	
	public ArrayList<Item> getItem_list() {
		return item_list;
	}

	public void setItem_list(ArrayList<Item> item_list) {
		this.item_list = item_list;
	}
	

}
