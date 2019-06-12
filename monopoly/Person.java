
public class Person {
	private int name;
	private int budget;
	
	public void pay_dept(int amount) {
		setBudget(getBudget()-amount);		
	}
	
	public void take_charge(int amount) {
		setBudget(getBudget()+amount);
	}
	
	public int getName() {
		return name;
	}
	public void setName(int name) {
		this.name = name;
	}
	public int getBudget() {
		return budget;
	}
	public void setBudget(int money) {
		this.budget = money;
	}
	
}
