
public class Land extends Properties {	
	public Land(String name, int id, int cost) {
		super(name, id, cost);
	}

	@Override
	public int calculate_rent(Person player) {
		int rent;
		if (getCost() <= 2000) {
			rent = getCost() * 40/100;
		}else if(getCost() <= 3000) {
			rent = getCost() * 30/100;
		}else {
			rent = getCost() * 35/100;
		}
		return rent;
	}
	
	

}
