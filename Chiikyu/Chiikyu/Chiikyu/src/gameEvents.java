import java.util.Random;

public class gameEvents {
	private int randomNumber;
	private String stringNumber;
	private Random random = new Random();
	private int resultReturnValue = 0;
	private StringBuilder strBuilder;
	private int attemptsCount = 0;
	private String resultString;
	private boolean winFlag = false;
	private long score = 10000;
	private long startTime;
	private long nowTime;
	private boolean cheat = true;
	static final long ATTEMPT_FULL_POINT=7000;
	static final long TIME_FULL_POINT=3000;

	public gameEvents() {
		generateRandomNumber();
		winFlag = false;
	}

	public void generateRandomNumber() {
		winFlag = false;
		long t = System.currentTimeMillis();
		random = new Random(t);
		randomNumber = random.nextInt(10000);
		stringNumber = String.valueOf(randomNumber);
		while (stringNumber.length() < 4)
			stringNumber = "0" + stringNumber;
		if (cheat)
			System.out.println("Ran=" + stringNumber);
	}

	public void cleanData() {
		generateRandomNumber();
		winFlag = false;
		resultReturnValue = 0;
		attemptsCount = 0;
		score = ATTEMPT_FULL_POINT+TIME_FULL_POINT;

	}

	public boolean getWinFlag() {
		return winFlag;
	}

	public void calculateRandomNumber(String s) {
		String tmp = new String(stringNumber);
		String tmp2 = new String(s);
		resultReturnValue = 0;
		for (int i = 0; i < 4; ++i)
			if (tmp.charAt(i) == tmp2.charAt(i)) {
				resultReturnValue += 10;
				strBuilder = new StringBuilder(tmp);
				strBuilder.setCharAt(i, '*');
				tmp = strBuilder.toString();

				strBuilder = new StringBuilder(tmp2);
				strBuilder.setCharAt(i, '#');
				tmp2 = strBuilder.toString();
			}
		for (int i = 0; i < 4; ++i)
			for (int j = 0; j < 4; ++j)
				if (tmp.charAt(i) == tmp2.charAt(j)) {
					resultReturnValue += 1;
					strBuilder = new StringBuilder(tmp);
					strBuilder.setCharAt(i, '@');
					tmp = strBuilder.toString();

					strBuilder = new StringBuilder(tmp2);
					strBuilder.setCharAt(j, '!');
					tmp2 = strBuilder.toString();
				} // System.out.println(tmp+"\n"+tmp2+"\n");
		// return resultReturnValue;
	}

	private boolean judge(String s) {
		if (s.length() != 4)
			return false;
		for (int i = 0; i < 4; ++i)
			if (s.charAt(i) > '9' || s.charAt(i) < '0')
				return false;
		return true;
	}

	public int getAttempts() {
		return attemptsCount;
	}

	public long getScore() {
		return score;
	}

	public long getTime() {
		if (attemptsCount == 1)
			startTime = System.currentTimeMillis();
		nowTime = System.currentTimeMillis();
		return (nowTime - startTime) / 1000;
	}

	private void setScore() {
		score = ATTEMPT_FULL_POINT /attemptsCount + TIME_FULL_POINT / (getTime() + 1);
	}

	public String getNumberResult(String s) {
		if (judge(s)) {
			calculateRandomNumber(s);
			if (resultReturnValue < 40) {
				attemptsCount++;
				resultString = attemptsCount + ":" + s + "|"
						+ resultReturnValue / 10 + "A" + resultReturnValue % 10
						+ "B";

			} else {
				attemptsCount++;
				winFlag = true;
				resultString = attemptsCount + ":" + s + "|"
						+ resultReturnValue / 10 + "A" + resultReturnValue % 10
						+ "B";
			}
			setScore();
			return resultString;
		} else {
			return "";
		}
	}
}
