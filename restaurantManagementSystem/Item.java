public class Item {
	private String name;
	private float cost;
	private int amount;
	
	
	public Item(String name, float cost, int amount) {
		this.name = name;
		this.cost = cost;
		this.amount = amount;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
