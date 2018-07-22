package log_create;

public class LogResult {
	double win;
	double lose;
	double count;

	/**
	 * �������̂�
	 */
	public LogResult() {
		win = 0.0;
		lose = 0.0;
		count = 0.0;
	}

	/**
	 * �����񐔂𑫂�
	 */
	public void addWin() {
		win++;
		count++;
	}

	/**
	 * �s�k�񐔂𑫂�
	 */
	public void addLose() {
		lose++;
		count++;
	}

	/**
	 * �Q���񐔂�Ԃ�
	 * @return
	 */
	public double getCount() {
		return count;
	}

	/**
	 * �����񐔂�Ԃ�
	 * @return
	 */
	public double getWin() {
		return win;
	}
	/**
	 * ���g�p
	 * @return
	 */
	public double getLose() {
		return lose;
	}

	/**
	 * �����񐔂ƎQ���񐔂�String�^�ŕԂ�
	 */
	public String toString() {
		return "Win = " + this.getWin() + ": Count = " + this.getCount();
	}
}
