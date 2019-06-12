
public class Railroad extends Properties {

	
	public Railroad(String name, int id, int cost) {
		super(name, id, cost);
	}
	
	@Override
	public void buy_property(Person[] player, int current_player) {
		super.buy_property(player, current_player);
		int num_of_railroads = ((Player) player[current_player]).getNum_of_railroads();
		((Player) player[current_player]).setNum_of_railroads(num_of_railroads+1);
	}

	@Override
	public int calculate_rent(Person player) {
		return 25*((Player)player).getNum_of_railroads();
	}
	
}
