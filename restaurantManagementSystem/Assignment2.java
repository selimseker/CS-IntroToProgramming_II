public class Assignment2 {

	public static void main(String[] args) {
		Restaurant restaurant = new Restaurant();
		
		InputFiles setup_dat = new InputFiles("setup.dat");
		InputFiles command_dat = new InputFiles("commands.dat");
		
		setup_dat.readLines();
		command_dat.readLines();
		
		setup_dat.execute_commands(restaurant);
		command_dat.execute_commands(restaurant);
	}	
}
