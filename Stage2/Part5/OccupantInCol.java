public class OccupantInCol {
	private Object occupant;
	private int col;
	
	public OccupantInCol(Object o, int numCol){
		occupant = o;
		col = numCol;
	}
	
	public Object getOccupant() {
		return occupant;
	}
	
	public void setOccupant(Object o) {
		occupant = o;
	}
	
	public int getColNum() {
		return col;
	}
	
	public void  setColNum(int num) {
		col = num;
	}
}
