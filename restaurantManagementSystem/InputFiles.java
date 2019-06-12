import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class InputFiles {

	private String file_name;
	private ArrayList<String> lines = new ArrayList<String>();
	
	
	public InputFiles(String file_name) {
		this.file_name = file_name;
	}

	public void execute_commands(Restaurant restaurant) {
		ArrayList<String> lines = getLines();
		for(String line : lines) {
			if(line.split(" ")[0].equals("add_item")) {
				restaurant.add_item(line);
			}
			else if(line.split(" ")[0].equals("add_waiter")) {
				restaurant.add_waiter(line);
			}
			else if(line.split(" ")[0].equals("add_employer")) {
				restaurant.add_employer(line);
			}
			else if(line.split(" ")[0].equals("create_table")) {
				restaurant.create_table(line);
			}
			else if(line.split(" ")[0].equals("new_order")) {
				restaurant.new_order(line);
			}
			else if(line.split(" ")[0].equals("add_order")) {
				restaurant.add_order(line);
			}
			else if(line.split(" ")[0].equals("check_out")) {
				restaurant.check_out(line);
			}
			else if(line.split(" ")[0].equals("get_order_status")) {
				restaurant.get_order_status();
			}
			else if(line.split(" ")[0].equals("get_employer_salary")) {
				restaurant.get_employer_salary();
			}
			else if(line.split(" ")[0].equals("get_waiter_salary")) {
				restaurant.get_waiter_salary();
			}
			else if(line.split(" ")[0].equals("stock_status")) {
				restaurant.stock_status();
			}
			else if(line.split(" ")[0].equals("get_table_status")) {
				restaurant.get_table_status();
			}
			else {
				System.out.println("Invalid command");
			}
		}	
	}
	
	public int readLines() {
		String filename = getFile_name();
		ArrayList<String> lines = getLines();
		try {
			RandomAccessFile file = new RandomAccessFile(filename, "r");
			String str;
			while ((str = file.readLine()) != null) {
				lines.add(str);
			}			
			file.close();
		}catch (IOException e) {
			System.out.println("File could not opened!");
			return 0;
		}
		return 0;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public ArrayList<String> getLines() {
		return lines;
	}

	public void setLines(ArrayList<String> lines) {
		this.lines = lines;
	}
	
}

