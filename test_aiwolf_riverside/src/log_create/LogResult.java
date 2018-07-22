package log_create;

public class LogResult {
	double win;
	double lose;
	double count;

	/**
	 * ‰Šú‰»‚Ì‚İ
	 */
	public LogResult() {
		win = 0.0;
		lose = 0.0;
		count = 0.0;
	}

	/**
	 * Ÿ—˜‰ñ”‚ğ‘«‚·
	 */
	public void addWin() {
		win++;
		count++;
	}

	/**
	 * ”s–k‰ñ”‚ğ‘«‚·
	 */
	public void addLose() {
		lose++;
		count++;
	}

	/**
	 * Q‰Á‰ñ”‚ğ•Ô‚·
	 * @return
	 */
	public double getCount() {
		return count;
	}

	/**
	 * Ÿ—˜‰ñ”‚ğ•Ô‚·
	 * @return
	 */
	public double getWin() {
		return win;
	}
	/**
	 * –¢g—p
	 * @return
	 */
	public double getLose() {
		return lose;
	}

	/**
	 * Ÿ—˜‰ñ”‚ÆQ‰Á‰ñ”‚ğStringŒ^‚Å•Ô‚·
	 */
	public String toString() {
		return "Win = " + this.getWin() + ": Count = " + this.getCount();
	}
}
