
public abstract class Properties extends Square{
	private String name;
	private int cost;
	private int owner_id;
	private boolean sold = false;
	


	public Properties(String name, int id, int cost) {
		super(id);
		this.name = name;
		this.cost = cost;
	}
	
	
	public abstract int calculate_rent(Person player);


	@Override
	public String do_the_square_command(Person[] people, int current_player) {
		String action;
		//Commands for properties	
		if(isSold()) {
			// take rent
			if (((Player)people[current_player]).getBought_properties().contains(getName())) {
				action = " has "+getName();
			}else {
				rent(people, current_player);
				action = " paid rent for "+getName();
			}

		}else if (people[current_player].getBudget() >= getCost()) {
			buy_property(people, current_player);
			action = " bought "+getName();
			
		}else {
			//Game over
			action = " goes bankrupt";
			((Player)people[current_player]).setBankrupt(true);
		}
		return action;
	}
	
	

	public void rent(Person[] player, int current_player) {
		int rent = calculate_rent(player[current_player]);
		((Player) player[current_player]).pay_dept(rent);
		int owner_id = 1 - current_player;
		((Player) player[owner_id]).take_charge(rent);
	}
	
	
	public void buy_property(Person[] people, int current_id) {
		setOwner_id(people[current_id].getName());
		setSold(true);
		//set the property data to player
		((Player) people[current_id]).buy_property(getName(), getCost());
		//Pay to the bank
		people[2].take_charge(getCost());
		
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(int owner) {
		this.owner_id = owner;
	}

	public boolean isSold() {
		return sold;
	}

	public void setSold(boolean sold) {
		this.sold = sold;
	}
	
}
