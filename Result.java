package aitknCEDEC2017LogResult;

public class Result {
	double win;
	double lose;
	double count;
	
	public Result(){
		win = 0.0;
		lose = 0.0;
		count = 0.0;
	}
	
	public void addwin(){
		win++;
		count++;
	}
	
	public void addlose(){
		lose++;
		count++;
	}
	
	public double getCount(){
		return count;
	}
	
	public double getWin(){
		return win;
	}
	
	@Override
	public String toString() {
		String text = "Win = " + this.getWin() + ": Count = " + this.getCount();
		return text;
	}
}
