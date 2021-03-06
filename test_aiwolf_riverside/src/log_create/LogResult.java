package log_create;

public class LogResult {
	double win;
	double lose;
	double count;

	/**
	 * 初期化のみ
	 */
	public LogResult() {
		win = 0.0;
		lose = 0.0;
		count = 0.0;
	}

	/**
	 * 勝利回数を足す
	 */
	public void addWin() {
		win++;
		count++;
	}

	/**
	 * 敗北回数を足す
	 */
	public void addLose() {
		lose++;
		count++;
	}

	/**
	 * 参加回数を返す
	 * @return
	 */
	public double getCount() {
		return count;
	}

	/**
	 * 勝利回数を返す
	 * @return
	 */
	public double getWin() {
		return win;
	}
	/**
	 * 未使用
	 * @return
	 */
	public double getLose() {
		return lose;
	}

	/**
	 * 勝利回数と参加回数をString型で返す
	 */
	public String toString() {
		return "Win = " + this.getWin() + ": Count = " + this.getCount();
	}
}
