import java.util.ArrayList;

public class Player extends Person{

	private int instant_square = 1;
	private int last_dice;
	private int num_of_railroads;
	private ArrayList<String> bought_properties = new ArrayList<String>();
	private boolean in_jail = false;
	private int jail_counter = 0;
	private boolean bankrupt = false;
	
	public Player() {
		super();
		setBudget(15000);
		
	}
	
	@Override
	public void pay_dept(int amount) {
		if (amount <= getBudget()) {
			super.pay_dept(amount);
		}else {
			setBankrupt(true);
		}
		
	}
	
	
	public int move(int step) {
		setLast_dice(step);
		setInstant_square(step+getInstant_square());
		if (getInstant_square() >= 41) {
			setBudget(getBudget()+200);
			setInstant_square(getInstant_square()-40);
		}
		return getInstant_square();
	}
	
	public void buy_property(String name, int cost) {
		this.bought_properties.add(name);
		setBudget(getBudget()-cost);
	}

	
	public int getInstant_square() {
		return instant_square;
	}

	public void setInstant_square(int instant_square) {
		this.instant_square = instant_square;
	}

	public ArrayList<String> getBought_properties() {
		return bought_properties;
	}

	public void setBought_properties(ArrayList<String> bought_properties) {
		this.bought_properties = bought_properties;
	}

	public int getLast_dice() {
		return last_dice;
	}
	
	public void setLast_dice(int last_dice) {
		this.last_dice = last_dice;
	}

	public int getNum_of_railroads() {
		return num_of_railroads;
	}

	public void setNum_of_railroads(int num_of_railroads) {
		this.num_of_railroads = num_of_railroads;
	}

	public boolean isIn_jail() {
		return in_jail;
	}

	public void setIn_jail(boolean in_jail) {
		this.in_jail = in_jail;
	}

	public int getJail_counter() {
		return jail_counter;
	}

	public void setJail_counter(int jail_counter) {
		this.jail_counter = jail_counter;
	}

	public boolean isBankrupt() {
		return bankrupt;
	}

	public void setBankrupt(boolean bankrupt) {
		this.bankrupt = bankrupt;
	}
	
	

}
