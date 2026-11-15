public class YearCount {

	private int year;
	private int count;

	public YearCount(int year, int count) {
		setYear(year);
		setCount(count);
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		if (year >= 2020 && year <= 2025)
			this.year = year;
		else
			throw new IllegalArgumentException("just years from 2020 to 2025");
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		if (count >= 0)
			this.count = count;
		else
			throw new IllegalArgumentException("Count cannot be negative");
	}
}