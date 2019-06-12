
public class Company extends Properties{
		
	public Company(String name, int id, int cost) {
		super(name, id, cost);
	}
	
	@Override
	public int calculate_rent(Person player) {
		return (((Player)player).getLast_dice()*4);
	}
	
	
}
