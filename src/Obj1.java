
public class Obj1 implements Comparable<Object> {

	private int row;
	private int col;
	private double val;
	
	public Obj1() {}
	
	public Obj1(int i, int j, double user_sim){
		this.row = i;
		this.col = j;
		this.val = user_sim;		
	}
	
	
	public int getMainUser() {
		return row;
	}

	public void setMainUser(int row) {
		this.row = row;
	}

	public int getSideUser() {
		return col;
	}

	public void setSideUser(int col) {
		this.col = col;
	}

	public double getVal() {
		return val;
	}

	public void setVal(int val) {
		this.val = val;
	}

	@Override
	  public int compareTo(Object arg0)  throws ClassCastException{
		Obj1 obj=(Obj1) arg0;
	    if (this.val > obj.val) {return 1;}
	    if (this.val < obj.val) {return -1;}
	    else{return 0;}
	  }

	@Override
	public String toString() {
		return "Obj1 [row=" + row + ", col=" + col + ", val=" + val + "]";
	}
	
}

