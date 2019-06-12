public class Tree extends Sprite{
	
	@Override
	public void setPosition(double x, double y) {
		// TODO Auto-generated method stub
		super.setPosition(x, y);
		positionXShadow = x + 20;
		positionYShadow = y -20;
	}
}
